package com.xizi.service;

import com.xizi.dao.UserDao;
import com.xizi.pojo.Perms;
import com.xizi.pojo.Role;
import com.xizi.pojo.User;
import com.xizi.utils.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void register(User user) {

        //1.生成随机盐
        String salt = SaltUtils.getSalt(8);
        //2.将随机盐保存到数据中
        user.setSalt(salt);
        //3. 对明文密码进行md5+salt+hash散列
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
        //4.将生成的密文传给用户
        user.setPassword(md5Hash.toHex());
        userDao.save(user);
    }

    @Override
    public User findByUserName(String username) {
        return userDao.findByUserName(username);
    }

    @Override
    public User findRoleByUserName(String username) {
        return userDao.findRoleByUserName(username);
    }

    @Override
    public List<Perms> findPermsByRole(String id) {
        return userDao.findPermsByRole(id);
    }
}
