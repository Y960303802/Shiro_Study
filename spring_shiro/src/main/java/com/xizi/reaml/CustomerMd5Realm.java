package com.xizi.reaml;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class CustomerMd5Realm extends AuthorizingRealm {


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("=======================");
        String primaryPrincipal = (String) principals.getPrimaryPrincipal();
        System.out.println("身份信息："+primaryPrincipal);

        //根据身份信息 用户名 获取当前用户的角色信息 以及权限信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        //将数据库中查询角色信息赋值给权限对象
        simpleAuthorizationInfo.addRole("admin");
        simpleAuthorizationInfo.addRole("user");

        //将数据库中查询权限信息赋值给权限对象
        simpleAuthorizationInfo.addStringPermission("user:*:01");
        simpleAuthorizationInfo.addStringPermission("product:*");
        return simpleAuthorizationInfo;
    }

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

            //1. md5 加密
//            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal,"202cb962ac59075b964b07152d234b70",this.getName());
            //2. md5 +salt  参数2： 数据库md5+salt之后的密码 参数3: 注册时的随机盐
//            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal,"8af432092927b6c2d785ad810e7305f1", ByteSource.Util.bytes("com"),this.getName());
            //2. md5 +salt  +hash散列次数
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal,"6c88656f61eafa1004f6f9574f823b3b", ByteSource.Util.bytes("com"),this.getName());
            return simpleAuthenticationInfo;
        }
        return null;
    }
}
