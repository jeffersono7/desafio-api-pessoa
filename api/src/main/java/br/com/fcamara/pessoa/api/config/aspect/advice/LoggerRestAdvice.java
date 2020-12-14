package br.com.fcamara.pessoa.api.config.aspect.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@Aspect
public class LoggerRestAdvice {
    public static final long WARNING_TIME = 60_000;

    @Around("@annotation(br.com.fcamara.pessoa.api.config.aspect.LoggerRest)")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        var method = (MethodSignature) joinPoint.getSignature();

        var methodName = method.getMethod().getName();

        log.debug("[REST] method: {} called", methodName);

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;

        if (executionTime > WARNING_TIME) {
            log.warn("[REST] method: {} executed in {} ms", methodName, executionTime);
        }

        return result;
    }
}
