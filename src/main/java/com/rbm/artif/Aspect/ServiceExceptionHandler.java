package com.rbm.artif.Aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class ServiceExceptionHandler {

    private static final Logger logger = LogManager.getLogger(ServiceExceptionHandler.class);

    @AfterThrowing(pointcut = "execution(* com.rbm.artif.service.*.*(..))",throwing = "exception")
    public void handleService(JoinPoint joinPoint,Exception exception){
        logger.error("Exception in method: {}", joinPoint.getSignature().getName());
        //this will tell method which it is connected to
        logger.error("Error At:"+exception.getMessage());
    }
}
