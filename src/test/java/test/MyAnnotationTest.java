package test;

import com.szxy.Application;
import com.szxy.config.anno.MyAnnoIp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MyAnnotationTest {

    @Autowired
    private MyAnnoIp annoIp;

    /**
     * 测试自定义组合注解
     */
    @Test
    public void test(){
        System.out.println(annoIp);
    }
}
