package pers.qxllb.business.service.redis;

import lombok.Data;
import pers.qxllb.business.common.config.RedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Objects;

/**
 * 懒汉模式 内部类实现
 *
 * @author liangb
 * @version 1.0
 * @date 2021/2/16 20:30
 */
public class RedisPoolUtil {

    private RedisConfig redisConfig;

    private JedisPool jedisPool;

    /**
     * 优先实例化,只实例化1个
     */
    private RedisPoolUtil(){

        redisConfig = new RedisConfig();
        JedisPoolConfig config = new JedisPoolConfig();

        //最大空闲连接数, 应用自己评估，不要超过ApsaraDB for Redis每个实例最大的连接数
        config.setMaxIdle(redisConfig.getRedisPoolMaxIdle());
        //最大连接数, 应用自己评估，不要超过ApsaraDB for Redis每个实例最大的连接数
        config.setMaxTotal(redisConfig.getRedisPoolMaxTotal());
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);
        config.setMaxWaitMillis(redisConfig.getMaxWaitMillis());

        jedisPool = new JedisPool(config, redisConfig.getRedisHost(), redisConfig.getRedisPort(),
                redisConfig.getRedisTimeout(), redisConfig.getRedisPassword());
    }

    /**
     * 内部类实现，JVM线程安全
     */
    private static class InnerRedisPool{
        private static RedisPoolUtil instance = new RedisPoolUtil();
    }

    /**
     * 获取单例
     * @return
     */
    private static RedisPoolUtil getInstance(){
        return InnerRedisPool.instance;
    }

    /**
     * 获取jedis连接资源
     * @return
     */
    public static Jedis getJedis(){
        redisStatus("redis get() before");
        return getInstance().jedisPool.getResource();
    }

    /**
     * 释放连接资源
     * @param jedis
     */
    public static void release(Jedis jedis) {
        if(Objects.nonNull(jedis)){
            jedis.close();
        }
    }

    /**
     * 获取Jedis连接状态
     * @param str str
     *  NumActive:激活连接数
     *  NumIdle： 空闲连接数=redisPoolMaxIdle - NumActive
     *  NumWaiters：等待数量
     */
    public static void redisStatus(String str){
        System.out.println(System.currentTimeMillis()+":"+Thread.currentThread().getName()+"--->"+
                str+
                ",NumActive:"+getInstance().jedisPool.getNumActive()+
                ",NumIdle:"+getInstance().jedisPool.getNumIdle()+
                ",NumWaiters:"+getInstance().jedisPool.getNumWaiters());
    }

}
