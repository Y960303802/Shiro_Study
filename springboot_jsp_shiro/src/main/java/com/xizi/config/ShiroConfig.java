package com.xizi.config;

import com.xizi.shiro.cache.RedisCacheManager;
import com.xizi.shiro.realms.CustomerRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sun.security.krb5.internal.CredentialsUtil;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * 用来整合shiro框架相关的配置类
 */
@Configuration
public class ShiroConfig {
    //1.创建ShiroFilter //负责拦截所有请求
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //配置系统受限资源
        //配置系统公共资源
        HashMap<String, String> map = new HashMap<>();
        map.put("/user/login","anon"); //anon 设置为公共资源
        map.put("/user/register","anon"); //anon 设置为公共资源
        map.put("/user/getImage","anon"); //anon 设置为公共资源
        map.put("/register.jsp","anon"); //anon 设置为公共资源

//        map.put("/index.jsp","authc");   //authc 请求这个资源需要认证和授权
        map.put("/**","authc");


        //默认认证界面路径 login.jsp
        shiroFilterFactoryBean.setLoginUrl("/login.jsp");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);


        return  shiroFilterFactoryBean;
    }


    //2.创建安全管理器

    @Bean
    public DefaultWebSecurityManager getSecurityManager(@Qualifier("getRealm") Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }
    //3.创建自定义realm
    //创建自定义realm
    @Bean
    public Realm getRealm(){
        CustomerRealm customerRealm = new CustomerRealm();
        //修改凭证效验匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置加密算法为MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //设置散列次数
        credentialsMatcher.setHashIterations(1024);
        customerRealm.setCredentialsMatcher(credentialsMatcher);


        //开启缓存管理
//        customerRealm.setCacheManager(new EhCacheManager());

        //使用自定义redis缓存管理器
        customerRealm.setCacheManager(new RedisCacheManager());
        //全局开启缓存
        customerRealm.setCachingEnabled(true);
        //开启 认证和授权缓存
        customerRealm.setAuthenticationCacheName("authenticationCache");
        customerRealm.setAuthenticationCachingEnabled(true);
        customerRealm.setAuthorizationCacheName("authorizationCache");
        customerRealm.setAuthorizationCachingEnabled(true);
        return customerRealm ;
    }
}
