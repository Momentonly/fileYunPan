package com.szxy.config.schedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 测试定时任务
 */
@Configuration
@EnableScheduling
public class TestSchedule {

    @Scheduled(cron = "* * * * * *")
    public void print1(){
        System.out.println(Thread.currentThread().getName() + "||||||666666");
    }

    @Scheduled(cron = "* * * * * *")
    public void print2(){
        System.out.println(Thread.currentThread().getName() + "||||||777777");
    }

}
