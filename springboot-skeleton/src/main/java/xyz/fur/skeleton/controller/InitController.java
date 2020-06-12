package xyz.fur.skeleton.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.bind.annotation.RestController;

/**
 * 初始化操作的其中一种方式
 * 基于Spring事件机制
 *
 * @author Fururur
 * @create 2019-07-31-10:05
 */
@Slf4j
@RestController
public class InitController implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // to do some init things.
    }
}
