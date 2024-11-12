package org.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.After;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("@annotation(LogBeforeExecution)")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("LOG:" + joinPoint.getSignature().getName());
    }

    @After("@annotation(LogAfterExecution)")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("LOG:" + joinPoint.getSignature().getName());
    }

    @Around("@annotation(TimeTracker)")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis() - startTime;
        logger.info("LOG: method {} took {} ms to proceed", proceedingJoinPoint.getSignature().getName(), endTime);
    }
}
