package test.spring;

import com.szxy.controller.UserController;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 获取组合注解
 */
public class GetAnnotationsTest {

    @Test
    public void getAnnotation1(){
        Annotation[] annotations = UserController.class.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
            // 获取
            Class<? extends Annotation> aClass = annotation.getClass();
            for (Method declaredMethod : aClass.getDeclaredMethods()) {
                System.out.println(declaredMethod);
            }
        }

    }

    @Test
    public void getAnnotations2(){
        Annotation[] annotations = UserController.class.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
            // 获取当前注解的Class描述
            Annotation[] supers = annotation.annotationType().getAnnotations();
            for (Annotation aSuper : supers) {
                System.out.println(aSuper);
            }
        }
    }
}
