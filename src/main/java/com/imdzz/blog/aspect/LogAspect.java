package com.imdzz.blog.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 用来打印出入Controller的日志
 * @author imdzz
 * @version 1.0
 * @date 2019/11/21 17:05
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    /**
     * 第一个*：任意返回值
     * 第二个*：任意类
     * 第三个*：任意方法
     * (..)：任意参数
     */
    @Pointcut("execution(public * com.imdzz.blog.controller.*.*(..))")
    public void log(){};

    @Before(value = "log()")
    public void doBefore(JoinPoint joinPoint){
        // joinpoint: Signature getSignature();获取封装了署名信息的对象,在该对象中可以获取到目标方法名,所属类的Class等信息
        // Object[] getArgs(); 获取传入目标方法的参数对象
        // Object getTarget(); 获取被代理的对象
        // Object getThis(); 获取代理对象
        Signature signature = joinPoint.getSignature();
        String classAndMethod = signature.getDeclaringTypeName() + "." + signature.getName();

        // RequestContextHolder 保持了当前请求上下文中的request和response对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String method = request.getMethod();

        log.info("------------{} Begin---------", signature.getName());
        log.info("| 1 [url]:{}, [method]:{}", url, method);
        log.info("| 2 [method]: {}", classAndMethod);
        log.info("| 3 [args]: {}", Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 有些接口需要返回大量数据，如果打印出来会导致日志量很大
     * @param joinPoint
     */
//    @AfterReturning(returning = "returnValue", pointcut = "log()")
//    public void doAfterReturning(JoinPoint joinPoint, Object returnValue){
//        String name = joinPoint.getSignature().getName();
//        //String classAndMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + name;
//        log.info("| 4 [return]: {}", String.valueOf(returnValue));
//        log.info("------------{} End---------", name);
//    }

    @AfterThrowing(pointcut = "log()")
    public void doAfterThrowing(JoinPoint joinPoint){
        log.info("------------{} exception---------", joinPoint.getSignature().getName());
    }
}
