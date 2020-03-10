package com.example.mq.task;

import com.example.mq.entity.CarCaseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 具体消息处理类
 * @author Fururur
 * @create 2020-02-24-13:39
 */
@Slf4j
@Component
public class CarCaseHandlerImpl implements IMessageHandler {

    private CarCaseDto dto;

    public CarCaseHandlerImpl(CarCaseDto dto) {
        this.dto = dto;
    }

    @Override
    public void doHandle() {
        log.info("handling...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> getSupportEventTypes() {
        return null;
    }
}
