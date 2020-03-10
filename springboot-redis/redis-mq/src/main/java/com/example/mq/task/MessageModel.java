package com.example.mq.task;

import lombok.Data;

/**
 *  消息事件模型
 * @author Fururur
 * @create 2020-02-21-16:14
 */
@Data
public class MessageModel {
    // 任务 id
    private String taskId;

    // 任务类别
    private int eventType;

    // 业务模型
    private String subject;

    // 任务执行的时间戳
    private Long submitTime;

    private Long startTime;
}
