package test.spring;

import com.szxy.Application;
import com.szxy.config.cycle.CPerson1;
import com.szxy.config.cycle.CPerson2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CycleTest {

    @Autowired
    private CPerson1 cPerson1;

    @Autowired
    private CPerson2 cPerson2;

    /**
     * 循环依赖测试
     *      Error:The dependencies of some of the beans in the application context form a
     *        应用程序上下文中的一些bean的依赖关系形成了一个循环
     */
    @Test
    public void test1(){
        System.out.println(cPerson1 + ":" + cPerson1.getcPerson2());
        System.out.println(cPerson2 + ":" + cPerson2.getcPerson1());
    }

}
