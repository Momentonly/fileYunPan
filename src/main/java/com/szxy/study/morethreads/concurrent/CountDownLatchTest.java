package com.szxy.study.morethreads.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * (01) CountDownLatch的作用是允许1或N个线程等待其他线程完成执行；
 *          而CyclicBarrier则是允许N个线程相互等待
 * (02) CountDownLatch的计数器无法被重置；CyclicBarrier的计数器可以被重置后使用，
 *          因此它被称为是循环的barrier
 * @Author: cwx
 * @Date: 2020/11/19
 */
public class CountDownLatchTest {
    public static void main(String[] args) {
        // 创建3个线程的线程池
        Executor executor = Executors.newFixedThreadPool(3);
        CountDownLatch latch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            executor.execute(()-> {
                // 业务
                try {
                    System.out.println(Thread.currentThread().getName() + "start");
                    Thread.sleep((long) (Math.random() * 5000));
                    System.out.println(Thread.currentThread().getName() + "end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });
        }
        // 等待操作结束
        try {
            System.out.println("等待业务线程结束！！");
            latch.await();
            System.out.println("业务线程结束!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
