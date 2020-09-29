package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckGroup;

import java.util.List;

/**
 * @author: chencongming
 * @date: 2020-09-20 22:47
 */
public interface CheckGroupService {
    //添加检查组
    void add(CheckGroup checkGroup, Integer[] checkitemIds);
   //检查组分页查询
    PageResult<CheckGroup> fingPage(QueryPageBean queryPageBean);
    //通过id查询
    CheckGroup findById(int id);
    //通过检查组id查询选中的检查项id集合
    List<Integer> findCheckItemIdsByCheckGroupId(int id);
    //修改
    void update(CheckGroup checkGroup, Integer[] checkitemIds);

     //根据id删除检查组
    void deleteById(int id) throws HealthException;
    //查询所有检查组
    List<CheckGroup> findAll();

}
