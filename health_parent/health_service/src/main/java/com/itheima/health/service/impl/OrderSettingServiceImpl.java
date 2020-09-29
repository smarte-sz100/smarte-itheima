package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

/**
 * @author: chencongming
 * @date: 2020-09-23 00:10
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

       @Autowired
       private OrderSettingDao orderSettingDao;
    /**
     * 上传Excel文件
     * @param orderSettingsList
     */
    @Override
    @Transactional
    public void add(ArrayList<OrderSetting> orderSettingsList) throws HealthException {
              //遍历
        for (OrderSetting orderSetting : orderSettingsList) {
            //先通过日期查询,再来判断
            OrderSetting osInDB = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
            if (null !=osInDB){
                //已预约数量不能大于可预约数量
                if (osInDB.getReservations()>orderSetting.getNumber()){
                    //报错误信息
                    throw new HealthException(osInDB.getOrderDate()+"中已预约数量不能大于可预约数量");
                }
                orderSettingDao.updateNumber(orderSetting);
            }else{
                // 不存在
                orderSettingDao.add(orderSetting);
            }
        }
    }

    /**
     *  //调用服务查询日期
     * @param month
     * @return
     */
    @Override
    public List<Map<String, Integer>> getOrderSettingByMonth(String month) {
          //组织查询Map,设置dateBegin月份开始日期和dateEnd月份结束日期
        String startDate = month + "-01";
        String endDate = month + "-31";
        List<Map<String, Integer>> monthData = orderSettingDao.getOrderSettingBetween(startDate,endDate);

       //   List<Map<String,Integer>> data = new ArrayList<>();
     /*        //遍历月份数组
        for (OrderSetting orderSetting : list) {
            Map orderSettingMap = new HashMap();
            //获取日期 几号
            orderSettingMap.put("orderDate",orderSetting.getOrderDate().getDate());
             //最大可预约数
            orderSettingMap.put("number",orderSetting.getNumber());
              //当前预约人数
            orderSettingMap.put("reservations",orderSetting.getReservations());
             //把预约设置信息放到list里
            data.add(orderSettingMap);
        }*/
           return monthData ;
    }

    /**
     * 设置预约信息
     * @param orderSetting
     */
    @Override
    @Transactional
    public void editNumberByDate(OrderSetting orderSetting) throws HealthException {
        //先通过日期查询,再来判断
        OrderSetting osInDB = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
        if (null !=osInDB){
            //已预约数量不能大于可预约数量
            if (osInDB.getReservations()>orderSetting.getNumber()){
                //报错误信息
                throw new HealthException(osInDB.getOrderDate()+"中已预约数量不能大于可预约数量");
            }
            orderSettingDao.updateNumber(orderSetting);
        }else{
            // 不存在
            orderSettingDao.add(orderSetting);
        }
    }
}
