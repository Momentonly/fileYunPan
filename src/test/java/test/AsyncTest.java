package test;

import com.szxy.Application;
import com.szxy.config.async.AsyncPrint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 异步注解@Async
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AsyncTest {

    @Autowired
    private AsyncPrint asyncPrint;

    @Test
    public void test1(){
        System.out.println("当前线程执行开始!!");
        // 异步方法
        asyncPrint.testPrint();
        asyncPrint.testPrint();
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("当前线程:" + Thread.currentThread().getName() + ":" + i);
        }
    }

    /**
     * this调用被@Async修饰的方法不生效
     */
    @Test
    public void test2(){
        asyncPrint.nomalPrint();
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("当前线程:" + Thread.currentThread().getName() + ":" + i);
        }
    }
}
