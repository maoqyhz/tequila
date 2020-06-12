package xyz.fur.skeleton.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池Bean配置文件
 * @author Fururur
 * @create 2019-07-26-15:46
 */
@Configuration
public class ThreadPoolConfiguration {

    // JUC中的线程池的注入
    @Bean("uploadThreadPool")
    public ExecutorService downloadThreadPool() {
        return Executors.newFixedThreadPool(10);
    }

    // Spring 线程池注入
    // 默认@Async线程池，通过实现AsyncConfigurer注入
    // 如果还需要别的池子，通过@Bean注入，在@Async配置value
    @Bean("taskExecutor2")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(200);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("taskExecutor2-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }

}


