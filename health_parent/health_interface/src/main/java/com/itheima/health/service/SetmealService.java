package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author: chencongming
 * @date: 2020-09-22 01:06
 */
public interface SetmealService {
    //新增套餐
    Integer add(Setmeal setmeal, Integer[] checkgroupIds);
     //套餐分页查询
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);
    //通过id查询套餐
    Setmeal findById(int id);
  //选中检查组id
    List<Integer> findCheckgroupIdsBySetmealId(int id);
     //更新套餐
    void update(Setmeal setmeal, Integer[] checkgroupIds);
     //删除套餐
    void deleteById(int id)throws HealthException;
     //查询数据库里的所有图片
    List<String> findSetmealImg();

    //查询所有
    List<Setmeal> findAll();
    //通过id查询套餐显示页面
    Setmeal findDetailById(Integer id);
    //套餐预约占比- 套餐数量
    List<Map<String, Object>> getSetmealReport();

}
