package com.lousing.poc.aop3;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdviceDemoAspect {
    // 1️⃣ BEFORE ADVICE
    @Before("execution(* com.lousing.poc.aop3.TaskService.startTask(..))")
    public void beforeAdvice(JoinPoint jp) {
        System.out.println("🔍 @Before: Preparing to run " + jp.getSignature().getName());
    }

    // 2️⃣ AFTER ADVICE (runs on success OR failure)
    @After("execution(* com.lousing.poc.aop3.TaskService.startTask(..))")
    public void afterAdvice(JoinPoint jp) {
        System.out.println("📌 @After: Finished running " + jp.getSignature().getName());
    }

    // 3️⃣ AFTER RETURNING ADVICE
    @AfterReturning(
            value = "execution(* com.lousing.poc.aop3.TaskService.completeTask(..))",
            returning = "result")
    public void afterReturningAdvice(Object result) {
        System.out.println("🎉 @AfterReturning: Method returned → " + result);
    }

    // 4️⃣ AFTER THROWING ADVICE
    @AfterThrowing(
            value = "execution(* com.lousing.poc.aop3.TaskService.failTask(..))",
            throwing = "ex")
    public void afterThrowingAdvice(Exception ex) {
        System.out.println("💣 @AfterThrowing: Exception caught → " + ex.getMessage());
    }

    // 5️⃣ AROUND ADVICE
    @Around("execution(* com.lousing.poc.aop3.TaskService.completeTask(..))")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("⏱️ @Around: Before execution");
        Object result = pjp.proceed();
        System.out.println("⏱️ @Around: After execution");
        return result;
    }
}
