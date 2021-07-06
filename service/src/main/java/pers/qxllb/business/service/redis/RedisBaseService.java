package pers.qxllb.business.service.redis;

import lombok.extern.slf4j.Slf4j;
import pers.qxllb.business.common.config.RedisPoolConfig;
import redis.clients.jedis.Jedis;

/**
 * TODO
 *
 * @author liangb
 * @version 1.0
 * @date 2021/7/6 9:15
 */
@Slf4j
public class RedisBaseService {

    /**
     * 当前连接资源的状态信息
     * @param str
     */
    public void display(String str){
        System.out.println(System.currentTimeMillis()+":"+Thread.currentThread().getName()+"--->"+
                str+
                ",NumActive:"+RedisPoolConfig.getInstance().getJedisPool().getNumActive()+
                ",NumIdle:"+RedisPoolConfig.getInstance().getJedisPool().getNumIdle()+
                ",NumWaiters:"+RedisPoolConfig.getInstance().getJedisPool().getNumWaiters());
    }

    /**
     * 获取连接资源
     * @return
     */
    Jedis getJedis(){
        return innerGetJedis();
    }

    /**
     * 创建连接资源
     * @return
     */
    private Jedis innerGetJedis() {
        Jedis jedis = null;
        try {
            jedis = RedisPoolConfig.getInstance().getJedisPool().getResource();
            System.out.println();
            System.out.println(System.currentTimeMillis()+":"+Thread.currentThread().getName()+"--->getJedis() now,jedis:"+jedis);
            display("getJedis() after");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }

        return jedis;
    }

    /**
     * 释放连接资源
     * @param release
     */
    public void release(Jedis release){
        innerelease(release);
    }

    /**
     * 释放连接资源
     * @param jedis
     */
    private void innerelease(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

}
