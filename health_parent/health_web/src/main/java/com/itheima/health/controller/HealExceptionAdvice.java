package com.itheima.health.controller;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.itheima.health.entity.Result;
import com.itheima.health.exception.HealthException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

/**
 * @author: chencongming
 * @date: 2020-09-20 11:29
 */
//全局异常处理
@RestControllerAdvice
public class HealExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(HealExceptionAdvice.class);

    /**
     * 自定义异常
     */
    @ExceptionHandler(HealthException.class)
    public Result handleHealthException(HealthException e){
        // 我们自己抛出的异常，把异常信息包装下返回即可
        return new Result(false, e.getMessage());
    }

    /**
     *  所有未知的异常
     * @param e
     * @return
     */
  @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        log.error("发生异常",e);
        return new Result(false, "发生未知错误，操作失败，请联系管理员");
    }


    /**
     * 密码错误
     * @param he
     * @return
     */
    @ExceptionHandler(BadCredentialsException.class)
    public Result handBadCredentialsException(BadCredentialsException he){
        return handleUserPassword();
    }

    /**
     * 用户名不存在
     * @param he
     * @return
     */
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public Result handInternalAuthenticationServiceException(InternalAuthenticationServiceException he){
        return handleUserPassword();
    }

    private Result handleUserPassword(){
        return new Result(false, "用户名或密码错误");
    }

    /**
     * 用户名不存在
     * @param he
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result handAccessDeniedException(AccessDeniedException he){
        return new Result(false, "没有权限");
    }
}
