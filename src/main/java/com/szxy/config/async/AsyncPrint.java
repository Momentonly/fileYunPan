package com.szxy.config.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 在bean中不要使用this来调用被@Async、@Transactional、@Cacheable等注解标注的方法，this下注解是不生效的。
 *      解析:Spring在创建一个Bean之后，对其包装并生成动态代理对象都是后置的举动，故会先生成真实类的实例bean，
 *          再动态创建动态代理bean，在动态代理bean中，会持有真实的bean的实例。
 *      总结:因为AOP动态代理的方法真实调用，会使用真实被代理对象实例进行方法调用，
 *          故在实例方法中通过this获取的都是被代理的真实对象的实例，而不是代理对象自身。
 */
@Component
public class AsyncPrint {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * this调用被@Async修饰的方法
     */
    public void nomalPrint(){
        System.out.println("namalPrint():" + Thread.currentThread().getName());
        // 异步不生效
        this.testPrint();
        // 异步生效
        //applicationContext.getBean(AsyncPrint.class).testPrint();
    }

    @Async
    public void testPrint(){
        System.out.println("异步打印开始!!");
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("testPrint():" + Thread.currentThread().getName() + ":" + i);
        }
    }

}
