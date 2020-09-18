package com.szxy.config.anno;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(MyAnnoIp.class)
public @interface MyAnnoScan {

    String[] value() default {};

}
