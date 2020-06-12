package xyz.fur.skeleton.config;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 初始化操作的一种方式
 * 影响的是Servlet生命周期
 * @author Fururur
 * @create 2019-08-01-17:15
 */
@Component
public class InitTest {

    @PostConstruct
    public void init() {
        // to do some init things.
    }
}
