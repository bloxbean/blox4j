package com.bloxbean.bloxql.aspect;

import com.bloxbean.bloxql.exception.DataFetchingException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class ExceptionAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @AfterThrowing(pointcut = "execution(* com.bloxbean.bloxql.resolvers.*.*(..))",
            throwing = "error")
    public void afterThrowingAdvice(JoinPoint jp, Throwable error){
        logger.error("Method Signature: "  + jp.getSignature());
        logger.error("Exception: "+error);
        logger.error("Error", error);
        throw new DataFetchingException(error.getMessage());
    }
}
