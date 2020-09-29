package com.itheima.health.service;

import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Order;

import java.util.Map;

/**
 * @author: chencongming
 * @date: 2020-09-26 01:10
 */
public interface OrderService {
    //预约提交
    Order submit(Map<String, String> orderInfo) throws HealthException;
    //订单详情
    Map<String, String> findById(int id);


}
