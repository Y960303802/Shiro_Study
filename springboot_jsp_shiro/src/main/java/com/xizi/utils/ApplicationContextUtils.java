package com.xizi.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

//工具类 获取bean 对象
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

//    @Autowired
    private static ApplicationContext context;

    //设置容器
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context=applicationContext;
    }

    //根据bean 名字获取工厂中指定bean对象
    public static Object getBean(String beanname){
        return context.getBean(beanname);
    }

}
