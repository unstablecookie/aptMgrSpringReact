package org.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class LoggingAspect {

    Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("@annotation(LogBeforeExecution)")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("method:" + joinPoint.getSignature().getName() + " with args:" + Arrays.toString(joinPoint.getArgs()));
    }

    @Before("@annotation(LogBeforeExecutionNoArgs)")
    public void logBeforeNoArgs(JoinPoint joinPoint) {
        logger.info("method:" + joinPoint.getSignature().getName());
    }

    @Before("@annotation(LogBeforeExecutionNoPasswd)")
    public void logBeforeNoPasswd(JoinPoint joinPoint) {
        String[] args = Arrays.toString(joinPoint.getArgs()).split(" ");
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].startsWith("password")) {
                args[i] = "";
            }
        }
        logger.info("method:" + joinPoint.getSignature().getName() + " with args:" + Arrays.toString(args));
    }

    @Before("@annotation(LogBeforeExecutionNoToken)")
    public void logBeforeNoToken(JoinPoint joinPoint) {
        String regex = "Bearer.+";
        String[] args = Arrays.toString(joinPoint.getArgs()).split(regex);
        logger.info("method:" + joinPoint.getSignature().getName() + " with args:" + Arrays.toString(args));
    }

    @AfterReturning(pointcut = "@annotation(LogAfterReturningSingleObject)", returning = "result")
    public void logAfterReturnSingleObject(JoinPoint joinPoint, Object result) {
        logger.info("method:" + joinPoint.getSignature().getName() + " returned:" + result.toString());
    }

    @AfterReturning(pointcut = "@annotation(LogAfterReturningSingleObjectNoData)", returning = "result")
    public void logAfterReturnSingleObjectNoData(JoinPoint joinPoint, Object result) {
        String regex = "\\[[^\\[]*\\]";
        String[] args = (result.toString()).split(regex);
        logger.info("method:" + joinPoint.getSignature().getName() + " with args:" + Arrays.toString(args));
    }

    @AfterReturning(pointcut = "@annotation(LogAfterReturningList)", returning = "result")
    public void logAfterReturnList(JoinPoint joinPoint, Object result) {
        logger.info("method:" + joinPoint.getSignature().getName() + " returned list of size:" + ((List<?>) result).size());
    }

    @AfterThrowing(pointcut = "@annotation(LogAfterThrow)", throwing = "e")
    public void logAfterThrowing(Exception e) {
        logger.error("Exception error:" + e.getMessage());
    }

    @Around("@annotation(LogTimeTracker)")
    public Object aroundTimeTracker(ProceedingJoinPoint proceedingJoinPoint) {
        long startTime = System.currentTimeMillis();
        Object result;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable t) {
            throw new RuntimeException("exception caught.", t);
        }
        long endTime = System.currentTimeMillis() - startTime;
        logger.info("time tracker: method {} took {} ms to proceed", proceedingJoinPoint.getSignature().getName(), endTime);
        return result;
    }
}
