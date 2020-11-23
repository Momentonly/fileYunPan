package test.spring;

import com.szxy.Application;
import com.szxy.common.ClasspathResolver;
import com.szxy.spring.boot.autoconfigure.yml.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.URL;
import java.util.Enumeration;

/**
 * 资源加载
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ResourceTest {

    @Autowired
    private Person person;

    @Test
    public void test1(){
        // 不加/    表示当前类的路径
        //InputStream resourceAsStream = this.getClass().getResourceAsStream("templates/add.html");
        // 加/      表示类路径根目录
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/templates/add.html");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
        try {
            String str;
            while( (str = bufferedReader.readLine()) != null){
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试加载Resource
     */
    @Test
    public void test2(){
        Person person = this.person;
        Resource[] htmlLocations = person.getHtmlLocations();
        for (Resource htmlLocation : htmlLocations) {
            if(htmlLocation instanceof FileSystemResource){
                File file = ((FileSystemResource) htmlLocation).getFile();
                System.out.println(file);
            }
            if(htmlLocation instanceof ClassPathResource){
                String path = ((ClassPathResource) htmlLocation).getPath();
                System.out.println(path);
                InputStream ras = this.getClass().getClassLoader().getResourceAsStream(path);
                BufferedReader br = new BufferedReader(new InputStreamReader(ras));
                try {
                    String str;
                    while ((str = br.readLine()) != null){
                        System.out.println(str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 资源解析
     */
    @Test
    public void test3(){
        //String[] strs = new String[]{"classpath:mappers/user/*.xml"};
        //String[] strs = new String[]{"classpath:templates/**/*.html"};
        String[] strs = new String[]{"classpath:META-INF/mappers/user/*Mapper.xml"};
        Resource[] resources = ClasspathResolver.resolveLocations(strs);
        System.out.println("资源数：" + resources.length);
        for (Resource resource : resources) {
            if(resource instanceof FileSystemResource){
                File file = ((FileSystemResource) resource).getFile();
                System.out.println(file);
            }
            if(resource instanceof ClassPathResource){
                String path = ((ClassPathResource) resource).getPath();
                System.out.println(path);
                InputStream ras = this.getClass().getClassLoader().getResourceAsStream(path);
                BufferedReader br = new BufferedReader(new InputStreamReader(ras));
                try {
                    String str;
                    while ((str = br.readLine()) != null){
                        System.out.println(str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(resource instanceof UrlResource){
                URL url = ((UrlResource) resource).getURL();
                System.out.println(url);
            }
        }
    }

    /**
     * 匹配全部的资源路径URL
     */
    @Test
    public void test4(){
        try {
            Enumeration<URL> resources = this.getClass().getClassLoader().getResources("META-INF/");
            while(resources.hasMoreElements()){
                URL url = resources.nextElement();
                System.out.println(url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 匹配单一资源路径URL
     */
    @Test
    public void test5(){
        URL mapper = this.getClass().getClassLoader().getResource("META-INF/mappers/");
        System.out.println(mapper.getPath());
        URL metainf = this.getClass().getClassLoader().getResource("META-INF/");
        System.out.println(metainf.getPath());
    }


    @Test
    public void test6(){
        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream ras = classLoader.getResourceAsStream("META-INF/mappers/user/UserMapper.xml");
        BufferedReader br = new BufferedReader(new InputStreamReader(ras));
        try {
            String str;
            while ((str = br.readLine()) != null){
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
