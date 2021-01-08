package com.xizi.dao;

import com.xizi.pojo.Perms;
import com.xizi.pojo.Role;
import com.xizi.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface UserDao {
    void save(User user);

    User findByUserName(String username);

    //通过用户名查询角色信息
    User findRoleByUserName(String username);

    //通过角色id查询权限信息
    List<Perms> findPermsByRole(String id);
}
