package test.spring;

import com.szxy.Application;
import com.szxy.config.helloservlet.RegHelloServlet;
import com.szxy.config.helloservlet.RegServletConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Configuration
 * @Componenet
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ConfigurationOrComponentTest {

    @Autowired
    private RegServletConfig regServletConfig;

    @Autowired
    private RegHelloServlet regHelloServlet;

    @Test
    public void test1(){
        System.out.println(regServletConfig.getClass().getSimpleName());
        System.out.println(regHelloServlet.getClass().getSimpleName());
    }
}
