package com.xizi.shiro.cache;

import com.xizi.utils.ApplicationContextUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

//自定义redis缓存实现
public class RedisCache<k,v>  implements Cache<k,v> {


    private String cacheName;

    public RedisCache(){

    }

    public RedisCache(String cacheName){
        this.cacheName=cacheName;
    }


    @Override
    public v get(k k) throws CacheException {
        System.out.println("get key"+k);
        v o = (v) getRedisTemplate().opsForHash().get(this.cacheName,k.toString());
        return o ;
    }

    @Override
    public v put(k k, v v) throws CacheException {
        System.out.println("put key:"+k);
        System.out.println("put value:"+v);
        //该类不是工厂管理的类
        //通过工厂工具类获取
        // springboot启动时 会自动构造 redisTemplate这个bean对象

        getRedisTemplate().opsForHash().put(this.cacheName,k.toString(),v);
        return null;
    }

    @Override
    public v remove(k k) throws CacheException {
        System.out.println("====remove======");
        return (v) getRedisTemplate().opsForHash().delete(this.cacheName,k.toString());
    }

    @Override
    public void clear() throws CacheException {
        System.out.println("====clear======");
        getRedisTemplate().delete(this.cacheName);
    }

    @Override
    public int size() {
        return getRedisTemplate().opsForHash().size(this.cacheName).intValue();
    }

    @Override
    public Set<k> keys() {
        return getRedisTemplate().opsForHash().keys(this.cacheName);
    }

    @Override
    public Collection<v> values() {
        return getRedisTemplate().opsForHash().values(this.cacheName);
    }

    private RedisTemplate getRedisTemplate(){
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
