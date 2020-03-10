package com.example.mq.task;

import com.alibaba.fastjson.JSON;
import com.example.mq.RedisConstants;
import com.example.mq.entity.CarCaseDto;
import com.example.mq.utils.CodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * task scan类
 * <p>
 * 定期扫描doing queue，判断是否有任务执行失败
 *
 * @author Fururur
 * @create 2020-02-26-15:11
 */
@Slf4j
@Component
public class MessageScan implements ApplicationRunner {
    // 定义任务执行的最长耗时为 8s
    private  final int TASK_TIMEOUT = 8000;

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private MessageProducer producer;

    @Autowired
    private MessageConsumer consumer;

    public MessageScan(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 每2s扫描hash，剔除失败任务，重新插入队列
     */
    @Scheduled(cron = "0/2 * * * * ?")
    public void scan() {
        try {
            ScanOptions scanOptions = ScanOptions.scanOptions().count(1000).build();
            Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisConstants.QUEUE_DOING_NAME, scanOptions);
            long now = System.currentTimeMillis();
            while (cursor.hasNext()) {
                Map.Entry<Object, Object> entry = cursor.next();
                long s1 = Long.valueOf(String.valueOf(entry.getValue()));
                long timesSub = (now - s1);
                // 假设超过 TASK_TIMEOUT 判定任务失败
                // 重新插入 pending 列表中去
                if (timesSub > TASK_TIMEOUT) {
                    redisTemplate.opsForHash().delete(RedisConstants.QUEUE_DOING_NAME, entry.getKey());
                    MessageModel model = JSON.parseObject(String.valueOf(entry.getKey()), MessageModel.class);
                    redisTemplate.opsForList().rightPush(RedisConstants.QUEUE_PENDING_NAME, model);
                }
            }
            cursor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下面是 testing
     */

    private MessageModel generateModel(int eventType, String caseNum) {
        MessageModel model = new MessageModel();
        model.setTaskId(CodeUtils.generateShortUuid());
        model.setEventType(eventType);
        CarCaseDto car = new CarCaseDto() {{
            setCaseNum(caseNum);
        }};
        model.setSubmitTime(System.currentTimeMillis());
        model.setSubject(JSON.toJSONString(car));
        return model;
    }

    /**
     * 每 2s 随机创建任务
     */
    @Scheduled(cron = "0/2 * * * * ?")
    public void produce() {
        int n = new Random().nextInt(10);
        log.info("produce:{}", n);
        while (n-- > 0) {
            producer.produce(generateModel(1, CodeUtils.generateShortUuid()));
        }
    }

    ExecutorService pool = Executors.newFixedThreadPool(10);

    /**
     * 四个消费者同时消费任务
     * @param args
     */
    @Override
    public void run(ApplicationArguments args) {
        pool.execute(() -> consumer.consume());
        pool.execute(() -> consumer.consume());
        pool.execute(() -> consumer.consume());
        pool.execute(() -> consumer.consume());
    }
}