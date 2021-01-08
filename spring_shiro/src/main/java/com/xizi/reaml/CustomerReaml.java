package com.xizi.reaml;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义realm实现 将认证/授权数据来源转为数据库的实现
 */
public class CustomerReaml extends AuthorizingRealm {


    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //根据token 中获取用户名
        String principal = (String)token.getPrincipal();
        System.out.println(principal);
        //根据身份信息使用jdbc mybatis 查询相关数据库
        if ("com".equals(principal)){
            //参数1: 返回数据库中正确的用户名
            //参数2： 返回数据库中正确密码
            //参数3: 提供当前reaml的名字
            //还是不够安全 密码得加密加盐
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal,"123",this.getName());
            return simpleAuthenticationInfo;
        }


        return null;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }
}
