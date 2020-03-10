package com.example.mq.task;

import com.example.mq.RedisConstants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 消息产生者
 * @author Fururur
 * @create 2020-02-24-13:32
 */
@Component
public class MessageProducer {

    private final RedisTemplate<String, Object> redisTemplate;

    public MessageProducer(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void produce(MessageModel event) {
        redisTemplate.opsForList().leftPush(RedisConstants.QUEUE_PENDING_NAME, event);
    }
}
