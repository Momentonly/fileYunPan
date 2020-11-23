package com.szxy.config.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


/**
 * 声明切面类
 * aop-starter自动配置开启了@EnableAspectJAutoProxy
 * @EnableAspectJAutoProxy 该注解会确保Aspect切面被合理地处理并且被代理被类会被自动创建代理
 *          (创建了AnnotationAwareAspectJAutoProxyCreator类)
 *      关于@Aspect注解如何被扫描：(会扫描所有spring容器中的bean判断是否有@Aspect注解)
 *          AnnotationAwareAspectJAutoProxyCreator中的findCandidateAdvisors()方法中的
 *          this.aspectJAdvisorsBuilder.buildAspectJAdvisors()
 *          BeanFactoryAspectJAdvisorsBuilder.buildAspectJAdvisors()方法中if (this.advisorFactory.isAspect(beanType))
 *          advisorFactory是AbstractAspectJAdvisorFactory类型
 *          public boolean isAspect(Class<?> clazz) {
 * 		        return (hasAspectAnnotation(clazz) && !compiledByAjc(clazz));
 *          }
 *          private boolean hasAspectAnnotation(Class<?> clazz) {
 * 		        return (AnnotationUtils.findAnnotation(clazz, Aspect.class) != null);
 *          }
 *      参考: https://blog.csdn.net/m0_46125280/article/details/103854896
 *      关于切面织入：
 *      1）AnnotationAwareAspectJAutoProxyCreator进行自动代理的创建工作。
 *      1）查看AnnotationAwareAspectJAutoProxyCreator继承结构，基于BeanPostProcessor的自动代理创建器的实现类，
 *         将根据一些规则在容器实例化Bean时为匹配的Bean生成代理实例。
 *      3）AbstractAutoProxyCreator是对自动代理创建器的一个抽象实现。
 *          最重要的是，它实现了SmartInstantiationAwareBeanPostProcessor接口，因此会介入到Spring IoC容器Bean实例化的过程。
 *      4）SmartInstantiationAwareBeanPostProcessor继承InstantiationAwareBeanPostProcessor，所以它最主要的职责
 *          是在bean的初始化前，先会执行所有的InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation，
 *          谁第一个返回了不为null的Bean，后面就都不会执行了。然后会再执行BeanPostProcessor#postProcessAfterInitialization。
 */
@Aspect
@Component
//@EnableAspectJAutoProxy
public class WebLogAspect {

    private final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    /**
     * execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?   name-pattern(param-pattern) throws-pattern?)
     * ?表示可选
     * execution(方法修饰符(可选)  返回类型  类路径(可选) 方法名(参数)  异常模式(可选))
     */
    @Pointcut("execution(public * com.szxy.controller..*.*(..))")//切入点描述 这个是controller包的切入点
    public void controllerLog(){} // 签名，可以理解成这个切入点的一个名称

    @Pointcut("execution(public * com.szxy.service..*.*(..))")//切入点描述，这个是uiController包的切入点
    public void serviceLog(){}

    //在切入点的方法之前
    @Before("controllerLog() || serviceLog()") // 可以写切入点表达式或者签名
    public void logBeforeController(JoinPoint joinPoint) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();//这个RequestContextHolder是Springmvc提供来获得请求的东西
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();

        // 记录下请求内容
        logger.info("################URL : " + request.getRequestURL().toString());
        logger.info("################HTTP_METHOD : " + request.getMethod());
        logger.info("################IP : " + request.getRemoteAddr());
        logger.info("################THE ARGS OF THE CONTROLLER : " + Arrays.toString(joinPoint.getArgs()));

        //下面这个getSignature().getDeclaringTypeName()是获取包+类名的   然后后面的joinPoint.getSignature.getName()获取了方法名
        logger.info("################CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //logger.info("################TARGET: " + joinPoint.getTarget());//返回的是需要加强的目标类的对象
        //logger.info("################THIS: " + joinPoint.getThis());//返回的是经过加强后的代理类的对象
    }

    //定义增强，pointcut连接点使用@annotation（xxx）进行定义
    @Around(value = "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object processAuthority(ProceedingJoinPoint point) throws Throwable{
        System.out.println("Around Aop Start!!!");
        System.out.println("ANNOTATION 调用类：" + point.getSignature().getDeclaringTypeName());
        //System.out.println("ANNOTATION 调用类名" + point.getSignature().getDeclaringType().getSimpleName());
        Object proceed = point.proceed();//调用目标方法
        System.out.println("Around Aop End!!!");
        return proceed;
    }
}

