package pers.qxllb.business.service.redis;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        //redisStatus("redis get() before");
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
     * 命令用于删除已存在的键。不存在的 key 会被忽略
     * @param key
     * @return
     */
    public static Long del(String key){
        Jedis mJedis = getJedis();
        try{
            return mJedis.del(key);
        }catch (Exception ex){
            log.error("RedisPoolUtil.del key() error,key:"+key,ex);
        }finally {
            release(mJedis);
        }
        return null;
    }

    /**
     * 命令用于删除已存在批量键。不存在的 keys 会被忽略
     * @param keys
     * @return
     */
    public static Long del(String[] keys){
        Jedis mJedis = getJedis();
        try{
            return mJedis.del(keys);
        }catch (Exception ex){
            log.error("RedisPoolUtil.del keys() error,key:"+keys,ex);
        }finally {
            release(mJedis);
        }
        return null;
    }

    /**
     * 设置key的失效时间
     * 1.设置成功返回 1 。
     * 2.当 key 不存在或者不能为 key 设置过期时间时(比如在低于 2.1.3 版本的 Redis 中你尝试更新 key 的过期时间)返回 0
     * @param key
     * @param value
     */
    public static Long expire(String key, int value) {
        Jedis jedis = getJedis();
        try {
            return jedis.expire(key, value);
        } catch (Exception ex) {
            log.error("RedisPoolUtil.expire() error,key:"+key,ex);
        } finally {
            release(jedis);
        }
        return null;
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public static boolean exists(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.exists(key);
        } catch (Exception ex) {
            log.error("RedisPoolUtil.exists() error,key:"+key,ex);
        }
        return false;
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
