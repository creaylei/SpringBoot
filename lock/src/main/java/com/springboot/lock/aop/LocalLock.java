package com.springboot.lock.aop;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface LocalLock {

    //默认key
    String key() default "";

    //过期时间
    int expire() default 5;
}
