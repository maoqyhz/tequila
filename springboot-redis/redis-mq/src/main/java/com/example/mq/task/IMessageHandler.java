package com.example.mq.task;

import java.util.List;

/**
 * 消息处理接口
 * @author Fururur
 * @create 2020-02-24-13:36
 */
public interface IMessageHandler {
    void doHandle();
    List<Integer> getSupportEventTypes();
}
