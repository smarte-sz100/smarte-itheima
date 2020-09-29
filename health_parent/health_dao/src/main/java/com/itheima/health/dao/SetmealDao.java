package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: chencongming
 * @date: 2020-09-22 02:04
 */
public interface SetmealDao {
    //新增套餐
    void add(Setmeal setmeal);
      //新增套餐与检查组关系
    void addSetmealCheckGroup(@Param("setmealId") Integer setmealId, @Param("checkgroupId") Integer checkgroupId);
       //套餐分页查询
    Page<Setmeal> findByCondition(String queryString);
     //通过id查询套餐
    Setmeal findById(int id);
     //选中检查组id
    List<Integer> findCheckgroupIdsBySetmealId(int id);

    //更新套餐
    void update(Setmeal setmeal);
    //删除关系
    void deleteSetmealCheckGroup(Integer id);

     //查询订单是否存在
    int findOrderBySetmeal(int id);
    //删除套餐组与检查组关系
    void deleteSetmealCheckGroup(int id);
    //没有被使用  则执行删除
    void deleteById(int id);
    //查询数据库里的所有图片
    List<String> findSetmealImg();

   //查询所有
    List<Setmeal> findAll();

    //通过id查询套餐显示页面
    Setmeal findDetailById(Integer id);

   //套餐预约占比- 套餐数量
    List<Map<String, Object>> getSetmealReport();

}
