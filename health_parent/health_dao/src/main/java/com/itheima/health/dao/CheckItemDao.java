package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 * @author: chencongming
 * @date: 2020-09-18 20:25
 */
public interface CheckItemDao {
    //查询所有
    List<CheckItem> findAll();
    //添加检查项
    void add(CheckItem checkItem);
     //检查项分页
    Page<CheckItem> findByCondition(String queryString);

    //检查项是否被检查组使用了
    int findCountByCheckItemId(int id);

    //删除检查项
    void deleteById(int id);


    //通过id查询
    CheckItem findById(int id);
   //编辑检查项
    void update(CheckItem checkitem);


}
