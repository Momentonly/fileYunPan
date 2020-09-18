package com.szxy.config.cycle;

import org.springframework.context.annotation.Bean;

/**
 * 循环依赖测试
 *      Error:The dependencies of some of the beans in the application context form a
 *      应用程序上下文中的一些bean的依赖关系形成了一个循环
 */
//@Configuration
public class CycleConf {

    @Bean
    public CPerson1 t1(CPerson2 tPerson2){
        CPerson1 tPerson1 = new CPerson1();
        tPerson1.setcPerson2(tPerson2);
        return tPerson1;
    }

    @Bean
    public CPerson2 t2(CPerson1 tPerson1){
        CPerson2 tPerson2 = new CPerson2();
        tPerson2.setcPerson1(tPerson1);
        return tPerson2;
    }
}