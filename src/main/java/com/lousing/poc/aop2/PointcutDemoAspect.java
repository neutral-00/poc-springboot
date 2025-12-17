package com.lousing.poc.aop2;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Component
public class PointcutDemoAspect {

    // 1️⃣ Match all methods in ProductService
    @Pointcut("execution(* com.lousing.poc.aop2.ProductService.*(..))")
    public void allProductMethods() {}

    // 2️⃣ Match only methods starting with 'add'
    @Pointcut("execution(* com.lousing.poc.aop2.ProductService.add*(..))")
    public void addMethods() {}

    // 3️⃣ Match any method with an int parameter
    @Pointcut("args(int,..)")
    public void intArgumentMethods() {}

    // 4️⃣ Combine pointcuts
    @Pointcut("allProductMethods() && intArgumentMethods()")
    public void productMethodsWithIntArgs() {}

    // Advice using pointcut #1
    @Before("allProductMethods()")
    public void beforeAll(JoinPoint jp) {
        System.out.println("🔍 @Before (allProductMethods): " + jp.getSignature().getName());
    }

    // Advice using pointcut #2
    @Before("addMethods()")
    public void beforeAdd(JoinPoint jp) {
        System.out.println("➕ @Before (addMethods): " + jp.getSignature().getName());
    }

    // Advice using pointcut #4
    @Before("productMethodsWithIntArgs()")
    public void beforeIntMethods(JoinPoint jp) {
        System.out.println("🔢 @Before (intArgumentMethods): " + jp.getSignature().getName());
    }
}
