package com.example.mq.task;

import com.alibaba.fastjson.JSONObject;
import com.example.mq.RedisConstants;
import com.example.mq.entity.CarCaseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 消费者
 * <p>
 * 从队列中变 bpop 消息并进行处理
 *
 * @author Fururur
 * @create 2020-02-24-13:34
 */
@Slf4j
@Component
public class MessageConsumer {

    private final RedisTemplate<String, Object> redisTemplate;

    public MessageConsumer(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * redis 消息队列利用的是 redis 最基本的 list 双端队列来是实现的
     * 缺少生产级别 mq 的一些特性，例如 ack 机制等
     * 这里通过 pending 和 doing 两个简单的 list 来实现基本 ack 机制
     */
    public void consume() {
        IMessageHandler handler = null;
        while (true) {
            System.out.println("consume...");
            MessageModel model = (MessageModel) redisTemplate.opsForList().rightPop(RedisConstants.QUEUE_PENDING_NAME, 1000L, TimeUnit.MILLISECONDS);
            if (model != null) {
                // 队列可能包含多任务， 可根据标识分发业务
                switch (model.getEventType()) {
                    case 1:
                        CarCaseDto dto = JSONObject.parseObject(model.getSubject(), CarCaseDto.class);
                        handler = new CarCaseHandlerImpl(dto);
                        break;
                }
                model.setStartTime(System.currentTimeMillis());
                // 处理任务前，写入doing 列表
                redisTemplate.opsForHash().put(RedisConstants.QUEUE_DOING_NAME, JSONObject.toJSONString(model), model.getStartTime().toString());
                try {
                    handler.doHandle();
                } catch (Exception e) {
                    // 处理失败，从doing list 写回 pending list
                    redisTemplate.opsForHash().delete(RedisConstants.QUEUE_DOING_NAME, JSONObject.toJSONString(model));
                    redisTemplate.opsForList().rightPush(RedisConstants.QUEUE_PENDING_NAME, model);
                }
                // 处理成功，从doing list删除
                long count = redisTemplate.opsForHash().delete(RedisConstants.QUEUE_DOING_NAME, JSONObject.toJSONString(model));
                // 处理超时但成功，可能会被扫描任务移除doing队列，防止多次消费，需要从pending队列中删除
                if (count < 1)
                    redisTemplate.opsForList().remove(RedisConstants.QUEUE_PENDING_NAME, 1, model);
            }
        }
    }
}
