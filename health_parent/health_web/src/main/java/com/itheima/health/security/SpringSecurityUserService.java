package com.itheima.health.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;

/**
 * @author: chencongming
 * @date: 2020-09-27 16:34
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {
    @Reference
    private UserService userService;

    /**
     * 实现用户信息的查询与授权
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //找出用户所拥有的角色，及角色下所拥有的权限集合
        //User.roles(角色集合).permissions(权限集合)
       com.itheima.health.pojo.User  user=userService.findByUsername(username);
        if(null != user) {
            // 用户名存在
            // 用户的权限集合
            ArrayList<GrantedAuthority> authorityList = new ArrayList<>();
            GrantedAuthority authority=null;
             //用户所拥有的角色
             Set<Role> roles = user.getRoles();
             if (null!=roles){
                 for (Role role : roles) {
                     //授予角色
                   authority = new SimpleGrantedAuthority(role.getKeyword());
                     authorityList.add(authority);
                     //角色下的权限集合
                     Set<Permission> permissions = role.getPermissions();
                       if (null !=permissions){
                           for (Permission permission : permissions) {
                                  //授予权限
                    authority = new SimpleGrantedAuthority(permission.getKeyword());
                               authorityList.add(authority);
                           }
                       }

                 }
             }
             //认证用户信息
             return  new User(username,user.getPassword(),authorityList);
        }
        //报错
        return null;
    }
}
