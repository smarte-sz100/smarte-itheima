package com.itheima.health.service;

import com.itheima.health.pojo.User;

/**
 * @author: chencongming
 * @date: 2020-09-27 16:37
 */
public interface UserService {
    //实现用户信息的查询与授权
    User findByUsername(String username);
}
