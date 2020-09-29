package com.itheima.health.dao;

import com.itheima.health.pojo.Member;

/**
 * @author: chencongming
 * @date: 2020-09-26 18:19
 */
public interface MemberDao {
    //通过手机查询会员信息
    Member findByTelephone(String telephone);
     //添加会员预约信息
    void add(Member member);
}
