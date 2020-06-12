package xyz.fur.skeleton.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 初始化操作的一种方式，spring boot提供
 * CommandLineRunner、ApplicationRunner 接口是在容器启动成功后的最后一步回调，可以用于做一些初始化操作
 * 可以实现多个，通过@Order(2) 注解来控制顺序
 * @author Fururur
 * @create 2019-07-31-14:11
 */
@Component
@Slf4j
public class InitRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // do some init things.
    }
}
