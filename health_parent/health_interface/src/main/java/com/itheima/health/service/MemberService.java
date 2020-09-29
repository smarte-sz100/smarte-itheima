package com.itheima.health.service;

import com.itheima.health.pojo.Member;

/**
 * @author: chencongming
 * @date: 2020-09-27 12:22
 */
public interface MemberService {
    //通过号码查询会员
    Member findByTelephone(String telephone);
   //添加会员
    void add(Member member);
}
