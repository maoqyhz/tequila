package xyz.fur.skeleton.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/**
 * 定时任务demo
 * @author Fururur
 * @create 2019-07-22-18:55
 */
@Component
@Slf4j
public class ScheduledTask {
    // @Scheduled(cron = "0/5 * * * * ?")
    public void TestTask(){
        log.info("time: " + LocalTime.now() + "     print: ----testing----");
    }
}
