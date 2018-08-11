package com.shu.edu.tac.toutiao.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.shu.edu.tac.toutiao.controller.*Controller.*(..))")
    public void before(JoinPoint joinPoint){
        logger.info("before method");
        StringBuilder sb = new StringBuilder();
        for(Object arg :joinPoint.getArgs()){
            sb.append("arg:"+arg.toString()+"|");
        }
        logger.info(sb.toString());

    }

    @After("execution(* com.shu.edu.tac.toutiao.controller.*.*(..))")
    public void after(){
        logger.info("after method");

    }
}
