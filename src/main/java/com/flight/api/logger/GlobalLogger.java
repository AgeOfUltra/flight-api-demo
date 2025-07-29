package com.flight.api.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class GlobalLogger {

    private static final Logger logger = LoggerFactory.getLogger(GlobalLogger.class);

    // Combined pointcut for all layers
    @Pointcut("execution(* com.flight.api.controller..*(..))")
    public void controllerLayer() {}

    @Pointcut("execution(* com.flight.api.service..*(..))")
    public void serviceLayer() {}

    @Pointcut("execution(* com.flight.api.repository..*(..))")
    public void repositoryLayer() {}

    // Single around advice handling all layers
    @Around("controllerLayer() || serviceLayer() || repositoryLayer()")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String layer = determineLayer(className);

        long startTime = System.currentTimeMillis();

        logger.info("Starting {} method: {}.{}", layer, className, methodName);

        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            logger.info("Completed {} method: {}.{} in {}ms", layer, className, methodName, executionTime);
            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.error("Failed {} method: {}.{} after {}ms - Error: {}",
                    layer, className, methodName, executionTime, e.getMessage());
            throw e;
        }
    }

    private String determineLayer(String className) {
        if (className.contains("Controller")) {
            return "CONTROLLER";
        } else if (className.contains("Service")) {
            return "SERVICE";
        } else if (className.contains("Repository")) {
            return "REPOSITORY";
        } else if (className.contains("Exception")) {
            return "EXCEPTION";
        }else if (className.contains("Aspect")) {
            return "ASPECT";
        }else if (className.contains("Logger")) {
            return "LOGGER";
        }
        return "UNKNOWN";
    }
}