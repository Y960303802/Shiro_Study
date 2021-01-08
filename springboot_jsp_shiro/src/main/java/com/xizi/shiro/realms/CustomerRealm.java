package com.xizi.shiro.realms;

import com.xizi.pojo.Perms;
import com.xizi.pojo.Role;
import com.xizi.pojo.User;
import com.xizi.service.UserService;
import com.xizi.shiro.salt.MyBateSource;
import com.xizi.utils.ApplicationContextUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.List;

public class CustomerRealm extends AuthorizingRealm {




    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取身份信息
        String primaryPrincipal = (String) principals.getPrimaryPrincipal();
        System.out.println("调用授权验证:"+primaryPrincipal);
        //根据主身份信息获取角色和权限信息
        UserService userService = (UserService) ApplicationContextUtils.getBean("userService");
        User user = userService.findRoleByUserName(primaryPrincipal);
        List<Role> roles = user.getRoles();
        if(!CollectionUtils.isEmpty(roles)){
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            //JAVA8新特性
            roles.forEach(role -> {
                //角色信息
                simpleAuthorizationInfo.addRole(role.getName());
                //权限信息
                List<Perms> permsByRole = userService.findPermsByRole(role.getId());
                if(!CollectionUtils.isEmpty(permsByRole)){
                   permsByRole.forEach(perm->{
                       simpleAuthorizationInfo.addStringPermission(perm.getName());
                   });
                }
            });
            return simpleAuthorizationInfo;
        }
        return null;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        System.out.println("=======================");
        System.out.println("调用认证");
        //获取用户名
        String principal = (String) token.getPrincipal();
        //使用工具类 在工厂中获取指定bean对象
        UserService userService = (UserService) ApplicationContextUtils.getBean("userService");
        User user = userService.findByUserName(principal);
       if(!ObjectUtils.isEmpty(user)){
         return    new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),new MyBateSource(user.getSalt()),this.getName());
       }
        return null;
    }
}
