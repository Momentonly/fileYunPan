package com.szxy.config.helloservlet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注册一个servlet配置类
 */
@Configuration
public class RegServletConfig {

    @Bean
    public RegHelloServlet regHelloServlet(){
        RegHelloServlet regHelloServlet = new RegHelloServlet();
        return regHelloServlet;
    }

}
