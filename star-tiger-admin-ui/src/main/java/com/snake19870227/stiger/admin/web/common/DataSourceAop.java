package com.snake19870227.stiger.admin.web.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/04/04
 */
@Aspect
@Component
public class DataSourceAop {

    @Pointcut(
            "execution(* com.snake19870227.stiger.admin.dao.mapper.*.select*(..))" +
            " || execution(* com.snake19870227.stiger.admin.dao.mapper.*.get*(..))" +
            " || execution(* com.snake19870227.stiger.admin.dao.mapper.*.query*(..))"
    )
    public void readPointcut() {

    }

    @Pointcut(
            "execution(* com.snake19870227.stiger.admin.dao.mapper.*.*(..))" +
            " && !execution(* com.snake19870227.stiger.admin.dao.mapper.*.select*(..))" +
            " && !execution(* com.snake19870227.stiger.admin.dao.mapper.*.get*(..))" +
            " && !execution(* com.snake19870227.stiger.admin.dao.mapper.*.query*(..))"
    )
    public void writePointcut() {

    }

    @Before("readPointcut()")
    public void read(JoinPoint jp) {
        DBContextHolder.slave();
    }

    @Before("writePointcut()")
    public void write(JoinPoint jp) {
        DBContextHolder.master();
    }
}
