package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: chencongming
 * @date: 2020-09-23 00:38
 */
public interface OrderSettingDao {
    //通过日期来查询预约设置
    OrderSetting findByOrderDate(Date orderDate);
    //更新可预约数量
    void updateNumber(OrderSetting orderSetting);
    //添加预约设置
    void add(OrderSetting orderSetting);
    //调用服务查询日期
   // List<Map<String, Integer>> getOrderSettingByMonth(dateBegin,dateEnd);
    //调用服务查询日期
    List<Map<String, Integer>> getOrderSettingBetween(@Param("startDate")
                              String startDate,@Param("endDate") String endDate);
    //更新已预约人数
    int editReservationsByOrderDate(OrderSetting orderSetting);
}
