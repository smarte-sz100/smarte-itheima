package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.pojo.Member;
import com.itheima.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: chencongming
 * @date: 2020-09-27 12:41
 */
@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {
    @Autowired
     private MemberDao memberDao;


    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }
}
