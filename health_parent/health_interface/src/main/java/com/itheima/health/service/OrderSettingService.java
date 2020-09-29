package com.itheima.health.service;

import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.OrderSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: chencongming
 * @date: 2020-09-23 00:10
 */
public interface OrderSettingService {

    //上传Excel文件
    void add(ArrayList<OrderSetting> orderSettingsList)throws HealthException;
    //调用服务查询日期
    List<Map<String, Integer>> getOrderSettingByMonth(String month);
    //设置预约信息
    void editNumberByDate(OrderSetting orderSetting) throws HealthException;
}
