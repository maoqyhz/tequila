package com.example.mq;

import lombok.extern.slf4j.Slf4j;

/**
 * PUB/SUB 机制中消息的接受类
 * <p>
 * PUB/SUB 机制是 1:m 消费的模型
 * <p>
 * 可直接实现 MessageListener 的 onMessage方法，
 * 也可直接实现一个方法，再通过 MessageListenerAdapter 进行包装。
 *
 * @author Fururur
 * @create 2020-01-20-16:05
        */
@Slf4j
public class Receiver {
    private Long id;

    public Receiver(Long id) {
        this.id = id;
    }

    public void receiveMessage(String message) {
        log.info("Receiver [{}] Received <{}>", this.id, message);
        // todo
    }
}
