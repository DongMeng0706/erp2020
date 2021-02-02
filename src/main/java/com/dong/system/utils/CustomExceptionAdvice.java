package com.dong.system.utils;

import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 */
@RestControllerAdvice
public class CustomExceptionAdvice {

    @ExceptionHandler(UnknownAccountException.class)
    public ResultObj xxx(UnknownAccountException e) {
        return new ResultObj(-1000, "账号不存在，请重新输入");
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResultObj password(IncorrectCredentialsException e) {
        return new ResultObj(-1001, "密码错误，请重新输入");
    }

    @ExceptionHandler(ClassCastException.class)
    public ResultObj password(ClassCastException e) {
        return new ResultObj(-1024, e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResultObj aa(UnauthorizedException e) {
        return new ResultObj(-1000, "权限不足，亲联系管理员");
    }

    @ExceptionHandler(LockedAccountException.class)
    public ResultObj zzz(LockedAccountException e) {
        System.out.println("===============================================");
        return new ResultObj(-1002, e.getMessage());
    }

    @ExceptionHandler(ExcessiveAttemptsException.class)
    public ResultObj eee(ExcessiveAttemptsException e) {
        return new ResultObj(-1003, e.getMessage());
    }
}