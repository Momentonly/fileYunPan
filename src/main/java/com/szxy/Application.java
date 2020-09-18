package com.szxy;

import com.szxy.config.anno.MyAnnoScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.szxy.dao")
/**
 * 开启缓存
 */
@EnableCaching
/**
 * 自定义组合注解
 */
@MyAnnoScan("自定义的一个组合注解，使用@Import向spring容器注入一个bean")
public class Application{
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        System.out.println("ConfigurableApplicationContext:" + context.getClass().getSimpleName());
        System.out.println("id:" + context.getId());
    }
}
