package xyz.fur.skeleton.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fur.skeleton.jobs.AsyncTasks;

import java.util.concurrent.Future;

/**
 * 异步任务调用接口测试
 * @author Fururur
 * @create 2019-08-23-9:55
 */
@RestController
@RequestMapping("/async")
public class AsyncController {

    private final AsyncTasks task;

    public AsyncController(AsyncTasks task) {
        this.task = task;
    }

    @GetMapping("/testAsync")
    public void testAsync() throws InterruptedException {

        long start = System.currentTimeMillis();
        Future<String> task1 = task.foo();
        Future<String> task2 = task.bar();
        Future<String> task3 = task.baz();

        while (true) {
            if (task1.isDone() && task2.isDone() && task3.isDone()) {
                // 三个任务都调用完成，退出循环等待
                break;
            }
            Thread.sleep(1000);
        }
        long end = System.currentTimeMillis();

        System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");
    }
}
