package com.example.springboottest.config;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: SpringbooTest
 * @BelongsPackage: com.example.springboottest.config
 * @Author: lujie
 * @Date: 2022/6/24 3:49
 * @Description:
 */
@Aspect
@Component
public class QRAop {
    //切入点配置  和  切入点表达式
    @Pointcut(value = "execution(* com.example.springboottest.service.QRScanner.*.*(..))")
    public void pointCut() {

    };

    //前置通知
    @Before(value = "pointCut()")
    public void before(){
        System.out.println("前置通知");
    }

    //后置通知
    @After("pointCut()")
    public void after(){
        System.out.println("后置通知");
    }
}
