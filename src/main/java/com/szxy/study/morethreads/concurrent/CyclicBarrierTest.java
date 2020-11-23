package com.szxy.study.morethreads.concurrent;

import java.util.concurrent.CyclicBarrier;

/**
 * 大概的意思就是一个可循环利用的屏障。
 *      它的作用就是会让所有线程都等待完成后才会继续下一步行动。
 * 可以用于多线程计算数据，最后合并计算结果的场景。
 * @Author: cwx
 * @Date: 2020/11/19
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        int threadNum = 4;
        //
        CyclicBarrier barrier = new CyclicBarrier(threadNum, new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 完成最后任务");
            }
        });
  
        for(int i = 0; i < threadNum; i++) {
            new TaskThread(barrier).start();
            /*new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " 到达栅栏 A");
                    barrier.await();
                    System.out.println(Thread.currentThread().getName() + " 冲破栅栏 A");

                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + " 到达栅栏 B");
                    barrier.await();
                    System.out.println(Thread.currentThread().getName() + " 冲破栅栏 B");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();*/
        }
    }

    static class TaskThread extends Thread {

        CyclicBarrier barrier;

        public TaskThread(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(getName() + " 到达栅栏 A");
                barrier.await();
                System.out.println(getName() + " 冲破栅栏 A");

                Thread.sleep(2000);
                System.out.println(getName() + " 到达栅栏 B");
                barrier.await();
                System.out.println(getName() + " 冲破栅栏 B");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
