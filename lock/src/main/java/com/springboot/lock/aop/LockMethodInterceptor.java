package com.springboot.lock.aop;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;


@Aspect
@Configuration
public class LockMethodInterceptor {

    private static final Cache<String, Object> CACHES = CacheBuilder.newBuilder()
            //最大缓存100个
            .maximumSize(100)
            //写缓存5秒后过期
            .expireAfterAccess(5, TimeUnit.SECONDS)
            .build();

    @Around("execution(public * *(..)) && @annotation(com.springboot.lock.aop.LocalLock)")
    public Object interceptor(ProceedingJoinPoint pip) {
        MethodSignature signature = (MethodSignature)pip.getSignature();
        Method method = signature.getMethod();
        LocalLock localLock = method.getAnnotation(LocalLock.class);
        String key = getKey(localLock.key(), pip.getArgs());

        if(!StringUtils.isEmpty(key)) {
            if(CACHES.getIfPresent(key) != null) {
                throw new RuntimeException("请勿重复请求");
            }
            CACHES.put(key, key);
        }

        try {
            return pip.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException("服务器异常");
        }finally {

        }
    }

    private String getKey(String key, Object[] args) {
        for (int i = 0; i < args.length ; i++) {
            key = key.replace("arg["+i+"]", args[i].toString());
        }
        return key;
    }
}
