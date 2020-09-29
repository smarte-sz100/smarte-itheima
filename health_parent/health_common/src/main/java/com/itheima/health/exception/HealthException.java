package com.itheima.health.exception;

/**
 * @author: chencongming
 * @date: 2020-09-20 11:25
 */

//自定义异常
public class HealthException extends RuntimeException {

    public HealthException(String message){
        super(message);
    }

}
