package test.basic;

import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class LambdaTest {

    /**
     * 方法引用
     */
    @Test
    public void test1(){
        List<String> list = new ArrayList<String>();
        list.add("test");
        list.add("123");
        //list.stream().forEach(System.out::println);

        // 方法引用
        Consumer<String> consumer = System.out::println;

        // 匿名类
        new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };

        // 调用
        list.stream().forEach(consumer);
    }

    @Test
    public void test2(){
        Method[] declaredMethods = ReflectionUtils.getDeclaredMethods(Object.class);
    }

    @Test
    public void test3(){
        /**
         * 匿名内部类{}
         * 内层{}代码块初始化
         */
        ArrayList<Integer> integers = new ArrayList(){
            {
                add(0);
                add(1);
                add(2);
            }
            @Override
            public String toString() {
                return super.toString();
            }
        };

        integers.stream().filter(i -> i == 1).forEach(System.out::println);

        Optional<Integer> first = integers.stream().filter(i -> i == 1).findFirst();
        System.out.println(first);
        System.out.println(first.get());
    }

    /**
     * 匿名对象访问外部变量，外部变量是final
     *      目的：匿名类反编译是做了变量的构造器复制拷贝，修改会造成数据不同步问题
     */
    @Test
    public void test4(){
        int x = 0;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //x = 5;
                System.out.println(x);
            }
        };
        runnable.run();

        Runnable r = () -> System.out.println(x);
        r.run();
    }

}
