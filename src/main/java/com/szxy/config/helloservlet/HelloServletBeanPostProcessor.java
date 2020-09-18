package com.szxy.config.helloservlet;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Spring中的BPP接口
 *      在一个Bean实例生成后，会交给BPP的postProcessBeforeInitialization方法进行加工，
 *          此时可以返回与此Bean相兼容的其他Bean实例，例如最常见的就是在这里返回原对象的动态代理对象。
 *      在这个方法执行后，会调用Bean实例的init相关方法。调用的方法是InitializingBean接口
 *          的afterPropertiesSet方法，以及@Bean声明中initMethod指定的初始化方法。
 *      在调用init方法之后，会调用BPP的postProcessAfterInitialization方法进行后置处理。
 *          此时处理同postProcessBeforeInitialization，也可以替换原bean的实例。
 */
@Component
public class HelloServletBeanPostProcessor implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // System.out.println("--------" + bean.getClass().getSimpleName());
        if (bean instanceof RegHelloServlet) {
            System.out.println("RegHelloServlet BeanPostProcessor postProcessBeforeInitialization called");
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof RegHelloServlet) {
            System.out.println("RegHelloServlet BeanPostProcessor postProcessAfterInitialization called");
        }
        return bean;
    }
}
