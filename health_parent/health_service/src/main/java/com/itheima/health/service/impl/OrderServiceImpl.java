package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.dao.OrderDao;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Member;
import com.itheima.health.pojo.Order;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: chencongming
 * @date: 2020-09-26 01:11
 */
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;

    /**
     * 体检预约
     *
     * @param orderInfo
     * @return
     */
    @Override
    @Transactional
    public Order submit(Map<String, String> orderInfo) throws HealthException {
        //1.通过日期查询预约设置信息
        //1.1 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //1.2预约日期前端来
        Date orderDate = null;
        try {
            orderDate = sdf.parse(orderInfo.get("orderDate"));
        } catch (ParseException e) {
            throw new HealthException("日期格式不正确，请选择正确的日期");
        }
        //先在数据库查询预约信息
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderDate);
        //2.判断是否有提前预约信息
        //不存在,报错
        if (null == orderSetting) {
            throw new HealthException("所选日期不能预约，请选择其它日期");
        }
        //存在 ,预约已满 ,报错
        if (orderSetting.getReservations() >= orderSetting.getNumber()) {
            throw new HealthException("选日期预约已满，请选择其它日期");
        }
        //存在,预约未满,判断是否重复预约
        String telephone = orderInfo.get("telephone");
        //通过手机号码查询会员预约信息
        Member member = memberDao.findByTelephone(telephone);
        //创建order对象
        Order order = new Order();
        order.setOrderDate(orderDate);
        order.setSetmealId(Integer.valueOf(orderInfo.get("setmealId")));
        if (null != member) {
            //查询t_order, 条件orderDate=? and setmeal_id=?,member=?
            order.setSetmealId(member.getId());
            //判断是否重复预约
            List<Order> orderList = orderDao.findByCondition(order);
            if (null != orderList && orderList.size() > 0) {
                throw new HealthException("该套餐已经预约过了，请不要重复预约");
            }
        } else {
            //不存在,新增预约信息
            member = new Member();
            //name 从前端来
            member.setName(orderInfo.get("name"));
            //sex 从前端来
            member.setSex(orderInfo.get("sex"));
            // idCard 从前端来
            member.setIdCard(orderInfo.get("idCard"));
            // phoneNumber 从前端来
            member.setPhoneNumber(telephone);
            // regTime 系统时间
            member.setRegTime(new Date());
            // password 可以不填，也可生成一个初始密码
            member.setPassword("12345678");
            // remark 自动注册
            member.setRemark("由预约而注册上来的");
            //   添加会员
            memberDao.add(member);
            order.setMemberId(member.getId());
        }
        //3.可预约
        //预约类型
        order.setOrderType(orderInfo.get("orderType"));
        // 预约状态
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        //添加t_order 预约信息
        orderDao.add(order);
        //4.更新已预约人数
        int affectedCount = orderSettingDao.editReservationsByOrderDate(orderSetting);
        if (affectedCount == 0) {
            throw new HealthException(MessageConstant.ORDER_FULL);
        }
        //5.返回新添加的订单对象
        return order;
    }

    /**
     * 订单详情
     * @param id
     * @return
     */
    @Override
    public Map<String, String> findById(int id) {


        return  orderDao.findById4Detail(id);

    }
}
