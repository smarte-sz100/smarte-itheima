package com.itheima.health.controller;

import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.plugin.liveconnect.SecurityContextHelper;

/**
 * @author: chencongming
 * @date: 2020-09-28 19:56
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 获取登录用户名
     * @return
     */
  @GetMapping("/getUsername")
    public Result getUsername(){

      //获取登录用户的信息
      User loginUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       //获取用户名
      String username = loginUser.getUsername();
      //返回
       return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,username);
  }

}
