package com.lousing.poc.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.lousing.poc.aop.OrderService.placeOrder(..))")
    public void beforeAdvice(JoinPoint jp) {
        System.out.println("🔍 @Before: Calling method " + jp.getSignature().getName());
    }

    @After("execution(* com.lousing.poc.aop.OrderService.placeOrder(..))")
    public void afterAdvice(JoinPoint jp) {
        System.out.println("✅ @After: Completed method " + jp.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.lousing.poc.aop.OrderService.placeOrder(..))", returning = "result")
    public void afterReturningAdvice(Object result) {
        System.out.println("🎉 @AfterReturning: Method returned → " + result);
    }

    @AfterThrowing(pointcut = "execution(* com.lousing.poc.aop.OrderService.failOrder(..))", throwing = "ex")
    public void afterThrowingAdvice(Exception ex){
        System.out.println("💥 @AfterThrowing: Exception caught → " + ex.getMessage());
    }

    @Around("execution(* com.lousing.poc.aop.OrderService.placeOrder(..))")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("⏱️ @Around: Before execution");
        Object result = proceedingJoinPoint.proceed();
        System.out.println("⏱️ @Around: After execution");
        return result;
    }
}
