package test.basic;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * SimpleDateFormat 线程不安全测试
 *
 */
public class LocalTimeTest {

    // 线程不安全
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 线程共享变量，安全
    private static final ThreadLocal<DateFormat> df = ThreadLocal.withInitial(
            () -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    );

    public static void main(String[] args) throws InterruptedException {

        Date d1 = new Date(2020, 1, 1);
        Date d2 = new Date();

        System.out.println("d1:" + sdf.format(d1));
        System.out.println("d1:" + sdf.format(d2));

        Thread.sleep(3000);

        for (int i = 0; i < 10; i++){
            Thread thread1 = new Thread(() -> {
                //System.out.println(Thread.currentThread().getName() + ":" + sdf.format(d1));
                System.out.println(Thread.currentThread().getName() + ":" + df.get().format(d1));
            });
            thread1.setName("thread the ONE" + i);
            thread1.start();

            Thread thread2 = new Thread(() -> {
                //System.out.println(Thread.currentThread().getName() + ":" + sdf.format(d2));
                System.out.println(Thread.currentThread().getName() + ":" + df.get().format(d2));
            });
            thread2.setName("thread the TWO" + i);
            thread2.start();
        }
    }

    /**
     * 格式化日期
     *      yyyy-MM-dd HH:mm:ss.SSS
     *      年月日 时分秒 毫秒
     */
    @Test
    public void test(){
        String time = "2020-10-20 10:20:20.202";
        LocalDateTime parseExpireTime2 = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        System.out.println(parseExpireTime2);
    }
}
