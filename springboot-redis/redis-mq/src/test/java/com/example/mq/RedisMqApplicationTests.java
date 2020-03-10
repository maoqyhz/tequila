package com.example.mq;

import com.alibaba.fastjson.JSON;
import com.example.mq.entity.CarCaseDto;
import com.example.mq.task.MessageConsumer;
import com.example.mq.task.MessageModel;
import com.example.mq.task.MessageProducer;
import com.example.mq.utils.CodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisMqApplication.class)
class RedisMqApplicationTests {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private MessageProducer producer;

    @Autowired
    private MessageConsumer consumer;


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


    @Test
    public void produce() {
        producer.produce(generateModel(1, "arx001"));
        producer.produce(generateModel(1, "arx002"));
    }

    @Test
    public void consume() {
        consumer.consume();
    }


    @Test
    public void test() {
        ScanOptions scanOptions = ScanOptions.scanOptions().count(5).build();
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan("set", scanOptions);
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            log.info("id:{},pos:{}---{}:{}", cursor.getCursorId(), cursor.getPosition(), entry.getKey(), entry.getValue());
        }
    }
}
