package test.spring;

import com.szxy.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DataSourceTest {

    @Autowired
    private DataSource dataSource;

    /**
     * druid数据源
     */
    @Test
    public void test1(){
        //DruidDataSource dataSource = ((DruidDataSource) this.dataSource);
        DataSource dataSource = this.dataSource;
        System.out.println(dataSource.getClass().getSimpleName());
    }

}
