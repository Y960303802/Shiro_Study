package com.xizi;

import com.xizi.reaml.CustomerMd5Realm;
import com.xizi.reaml.CustomerReaml;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class SpringShiroApplicationTests {


    //第一个shiro 测试案列 shiro.ini 固定配置文件
    @Test
    void TestAuthenticator() {

        //1.创建安全管理器对象
        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        //2.给安全管理器设置realm
        securityManager.setRealm(new IniRealm("classpath:shiro.ini"));

        //3.SecurityUtils 全局安全工具类
        SecurityUtils.setSecurityManager(securityManager);

        //4.关键对象subject  主体
        Subject subject = SecurityUtils.getSubject();

        //5.创建令牌
        UsernamePasswordToken token = new UsernamePasswordToken("com", "123");
        try {
            //测试认证状态
            System.out.println("认证状态前:" + subject.isAuthenticated());
            subject.login(token); //用户认证
            System.out.println("认证状态后:" + subject.isAuthenticated());
        } catch (UnknownAccountException e) {
            System.out.println(":认证失败用户名不存在");
        } catch (IncorrectCredentialsException e) {
            System.out.println(":认证失败密码错误");

        }

    }


    //使用自定义reaml
        @Test
        void TestCustomerRealmAuthenticator() {

            //1.创建安全管理器对象
            DefaultSecurityManager securityManager = new DefaultSecurityManager();

            //2.给安全管理器设置自定义realm
            securityManager.setRealm(new CustomerReaml());

            //3.SecurityUtils 全局安全工具类
            SecurityUtils.setSecurityManager(securityManager);

            //4.关键对象subject  主体
            Subject subject = SecurityUtils.getSubject();

            //5.创建令牌
            UsernamePasswordToken token = new UsernamePasswordToken("com", "123");
            try {
                //测试认证状态
                System.out.println("认证状态:" + subject.isAuthenticated());
                subject.login(token); //用户认证
                System.out.println("认证状态:" + subject.isAuthenticated());
            } catch (UnknownAccountException e) {
                System.out.println(":认证失败用户名不存在");
            } catch (IncorrectCredentialsException e) {
                System.out.println(":认证失败密码错误");
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        //测试shiroMD5 加密算法
    @Test
    void TestShiroMD5() {

        //这种方式不推荐
//        Md5Hash md5Hash=new Md5Hash();
//        md5Hash.setBytes("123".getBytes());
//        System.out.println(md5Hash.toHex()); //313233

//          使用正确方式MD5
        Md5Hash md5Hash = new Md5Hash("123");
        System.out.println(md5Hash.toHex()); //202cb962ac59075b964b07152d234b70

        //使用MD5+salt处理
        Md5Hash md5Hash1 = new Md5Hash("123", "com");
        System.out.println(md5Hash1.toHex());  //8af432092927b6c2d785ad810e7305f1

        //使用MD5+salt+hash散列
        Md5Hash md5Hash2 = new Md5Hash("123", "com", 1024);
        System.out.println(md5Hash2.toHex()); //6c88656f61eafa1004f6f9574f823b3b
    }

    @Test
    void TestCustomerMD5RealmAuthenicator() {
        //创建安全管理器对象
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

        //设置realm使用hash凭证匹配器
        CustomerMd5Realm realm = new CustomerMd5Realm();
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");//设置MD5加密
        credentialsMatcher.setHashIterations(1024); //设置散列次数
        realm.setCredentialsMatcher(credentialsMatcher);
        defaultSecurityManager.setRealm(realm);

        //3.SecurityUtils 全局安全工具类
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        //4.关键对象subject  主体
        Subject subject = SecurityUtils.getSubject();


        //5.创建令牌
        UsernamePasswordToken token = new UsernamePasswordToken("com", "123");
        try {
            //测试认证状态
            System.out.println("认证状态:" + subject.isAuthenticated());
            subject.login(token); //用户认证
            System.out.println("认证状态:" + subject.isAuthenticated());
        } catch (UnknownAccountException e) {
            System.out.println(":认证失败用户名不存在");
        } catch (IncorrectCredentialsException e) {
            System.out.println(":认证失败密码错误");
        }catch (Exception e){
            e.printStackTrace();
        }

        //认证用户进行授权
        if(subject.isAuthenticated()){
            //1. 基于角色权限控制
//            System.out.println(subject.hasRole("admin"));
//            System.out.println(subject.hasRole("user"));
            //1. 基于多角色权限控制
            System.out.println(subject.hasAllRoles(Arrays.asList("admin", "user")));

            //是否具有其中一个角色
//            boolean[] booleans = subject.hasRoles(Arrays.asList("admin", "super"));
//            for(boolean b:booleans){
//                System.out.println(b);
//            }

            System.out.println("==========================");
            //基于权限字符串的访问控制 资源标识符：操作：资源类型
            System.out.println("权限:"+subject.isPermitted("user:upadte:01"));
            System.out.println("权限:"+subject.isPermitted("product:create"));
            System.out.println("权限:"+subject.isPermitted("product:create:02"));

            //分别具有哪些权限
            boolean[] permitted = subject.isPermitted("user:*:01", "order:*:02");
            for (boolean b : permitted) {
                System.out.println(b);
            }

            System.out.println("==============================");

            //同时具有哪些权限
            boolean permitted1 = subject.isPermittedAll("user:*:01", "product:*");
            System.out.println(permitted1);
        }
    }




}
