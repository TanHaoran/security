package com.jerry.web.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/23
 * Time: 20:36
 * Description:
 */
@Aspect
@Component
@Slf4j
public class TimeAspect {

    // 时间：@Around以包围的方式起作用
    // 地点：只针对UserController中的所有方法起作用
    @Around("execution(* com.jerry.web.controller.UserController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("【切片】start");

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.info("【切片】arg is " + arg);
        }

        long start = new Date().getTime();

        Object proceed = joinPoint.proceed();

        log.info("【切片】耗时: " + (new Date().getTime() - start));

        log.info("【切片】end");

        return proceed;
    }

}
