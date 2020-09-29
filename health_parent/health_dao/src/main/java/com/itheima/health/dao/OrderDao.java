package com.itheima.health.dao;

import com.itheima.health.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * @author: chencongming
 * @date: 2020-09-26 18:03
 */
public interface OrderDao {
    //查询预约信息
    List<Order> findByCondition(Order order);
     //添加预约信息
    void add(Order order);
     //查询订单详情
    Map<String, String> findById4Detail(int id);
}
