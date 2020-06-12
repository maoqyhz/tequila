package xyz.fur.skeleton.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.Future;

/**
 * 异步任务实例 @Async(value="")
 * 可在后面添加具体的value，指定处理的taskExecutor
 * @author Fururur
 * @create 2019-08-05-16:07
 */
@Slf4j
@Component
public class AsyncTasks {

    private static Random random = new Random();

    @Async("taskExecutor3")
    public Future<String> foo() throws InterruptedException {
        log.info("开始做任务foo");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("完成任务foo，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<>("任务foo完成");
    }


    @Async
    public Future<String> bar() throws InterruptedException {
        log.info("开始做任务bar");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("完成任务bar，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<>("任务bar完成");
    }

    @Async
    public Future<String> baz() throws InterruptedException {
        log.info("开始做任务baz");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
                log.info("完成任务baz，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<>("任务baz完成");
    }
}
