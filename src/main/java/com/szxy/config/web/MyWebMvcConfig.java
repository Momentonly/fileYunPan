package com.szxy.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *  采用JavaBean的形式来代替传统的xml配置文件形式进行针对mvc框架的个性化定制
 *      可以自定义一些Handler，Interceptor，ViewResolver，MessageConverter
 */
@Configuration
//@EnableWebMvc // 尽量不要使用 @EnableWebMvc 注解，因为它会关闭默认配置。
public class MyWebMvcConfig implements WebMvcConfigurer {

    /**
     * 简单的视图控制器跳转
     *      相同的url路径会被相同的覆盖
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 该路径会被Controller中路径覆盖，并且会被下一个registry注册的相同路径覆盖
        registry.addViewController("/").setViewName("index");
        //registry.addViewController("/").setViewName("ind");
        //registry.addViewController("/index.html").setViewName("index");
    }

    /**
     * 静态资源映射配置(也可以在yml配置，最终被WebMvcAutoConfiguration解析注册)
     *      映射路径会被相同的所覆盖
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /mypic/**路径     映射--->到     (file:前缀，表示文件系统) F:/Picture
        registry.addResourceHandler("/mypic/**").addResourceLocations("file:F:/Picture/");
       /* registry.addResourceHandler("/**").addResourceLocations("file:E:/IDEAWorkSpace/fileYunPan/upfile/")
                    .addResourceLocations("classpath:/static/");*/
    }
}
