package test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * stream测试
 */
public class StreamTest {

    @Test
    public void test1(){
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        strings.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
    }

    @Test
    public void test2(){
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        strings.forEach(s -> {
            System.out.println(s);
        });
    }

    @Test
    public void test3(){
        List<Integer> nums = Arrays.asList(new Integer[]{0, 1, 2, 3, 4, 5});
        // map
        nums.stream().map(i->++i).forEach(System.out::print);
        System.out.println();
        // peek
        nums.stream().peek(i->++i).forEach(System.out::print);
        System.out.println();
        // filter
        nums.stream().filter(i -> {return i > 3;}).forEach(i -> System.out.print(i));
    }


}
