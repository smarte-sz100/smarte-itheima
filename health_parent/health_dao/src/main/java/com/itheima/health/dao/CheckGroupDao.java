package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;

import java.util.List;

/**
 * @author: chencongming
 * @date: 2020-09-20 22:57
 */
public interface CheckGroupDao {
    //添加检查组
    void add(CheckGroup checkGroup);

    //添加检查组与检查项的关系
    void addCheckGroupCheckItem(Integer checkGroupId, Integer checkitemId);

    // 条件查询
    Page<CheckGroup> findPage(String queryString);

    //通过id查询
    CheckGroup findById(int id);
    //通过检查组id查询选中的检查项id集合
    List<Integer> findCheckItemIdsByCheckGroupId(int id);
    //更新检查组
    void update(CheckGroup checkGroup);
    //删除旧关系
    void deleteCheckGroupCheckItem(Integer id);
   //通过检查组id统计个数
    int findSetmealCountByCheckGroupId(int id);
   //删除检查组
    void deleteById(int id);
  //查询检查组
    List<CheckGroup> findAll();
}
