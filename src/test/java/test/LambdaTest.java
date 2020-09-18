package test;

import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;
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
        ReflectionUtils.getDeclaredMethods(Object.class);
    }
}
