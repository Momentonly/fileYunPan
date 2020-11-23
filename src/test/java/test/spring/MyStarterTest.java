package test.spring;

import com.szxy.Application;
import com.szxy.spring.boot.autoconfigure.helloworld.HelloWorld;
import com.szxy.spring.boot.autoconfigure.yml.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * 自定义starter测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MyStarterTest {

    @Autowired
    private HelloWorld helloWorld;

    @Autowired
    private Person person;

    /**
     * 测试自定义的stater
     */
    @Test
    public void test1(){
        helloWorld.hello();
    }

    @Test
    public void test2(){
        List<Object> lists = person.getLists();
        for (Object list : lists) {
            System.out.println(list);
        }
    }

    /**
     * map
     */
    @Test
    public void test3(){
        Map<String, String> strmaps = person.getStrmaps();
        System.out.println(strmaps);
    }
}
