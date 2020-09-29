package com.itheima.health.dao;

import com.itheima.health.pojo.User;

/**
 * @author: chencongming
 * @date: 2020-09-27 22:50
 */
public interface UserDao {
    //实现用户信息的查询与授权
    User findByUsername(String username);
}
