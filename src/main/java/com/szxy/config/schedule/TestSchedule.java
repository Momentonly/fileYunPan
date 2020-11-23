package com.szxy.config.schedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 测试定时任务
 */
@Configuration
//@EnableScheduling
public class TestSchedule {

    /**
     * 秒  分  时   DayofMonth(1-31)  月   DayofWeek(1-7)
     */
    @Scheduled(cron = "* * * * * ?")
    public void print1(){
        System.out.println(Thread.currentThread().getName() + "||||||666666");
    }

    @Scheduled(cron = "* * * * * ?")
    public void print2(){
        System.out.println(Thread.currentThread().getName() + "||||||777777");
    }

}
