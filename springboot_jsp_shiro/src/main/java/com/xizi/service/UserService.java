package com.xizi.service;

import com.xizi.pojo.Perms;
import com.xizi.pojo.Role;
import com.xizi.pojo.User;

import java.util.List;

public interface UserService {

    void register(User user);

    User findByUserName(String username);
    //通过用户名查询角色信息
    User findRoleByUserName(String username);
    //通过角色id查询权限信息
    List<Perms> findPermsByRole(String id);

}
