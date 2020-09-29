package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 * @author: chencongming
 * @date: 2020-09-18 20:06
 */
public interface CheckItemService {
    //查询所有
    List<CheckItem> findAll();
   //添加检查项
    void add(CheckItem checkItem);
     //检查项分页
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);
     //根据id 删除检查项
    void deleteById(int id) throws HealthException;


   //通过id查询
   CheckItem findById(int id);
    //编辑检查项
    void update(CheckItem checkitem);
}
