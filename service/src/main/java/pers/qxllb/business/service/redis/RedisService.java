package pers.qxllb.business.service.redis;

import lombok.extern.slf4j.Slf4j;
import pers.qxllb.business.common.config.RedisPoolConfig;
import redis.clients.jedis.Jedis;

import java.util.concurrent.CountDownLatch;

/**
 * TODO
 *
 * @author liangb
 * @version 1.0
 * @date 2021/2/16 20:36
 */
@Slf4j
public class RedisService {

    private RedisPoolConfig RedisPoolConfig;

    public RedisService(){
        RedisPoolConfig = new RedisPoolConfig();
    }

    private void display(String str){
        System.out.println(System.currentTimeMillis()+":"+Thread.currentThread().getName()+"--->"+
                str+
                ",NumActive:"+RedisPoolConfig.getJedisPool().getNumActive()+
                ",NumIdle:"+RedisPoolConfig.getJedisPool().getNumIdle()+
                ",NumWaiters:"+RedisPoolConfig.getJedisPool().getNumWaiters());
    }

    private Jedis getJedis() {
        Jedis jedis = null;
        try {
            jedis = RedisPoolConfig.getJedisPool().getResource();
            System.out.println();
            System.out.println(System.currentTimeMillis()+":"+Thread.currentThread().getName()+"--->getJedis() now,jedis:"+jedis);
            display("getJedis() after");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }

        return jedis;
    }

    private void release(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    public String set(String key, String value, int expire) {
        Jedis mJedis = getJedis();
        try {
            String result = mJedis.set(key, value);
            if (expire > 0) {
                mJedis.expire(key, expire);
            }
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            release(mJedis);
        }

        return null;
    }

    public String get2(String key) {
        Jedis mJedis = getJedis();
        try {
            String result = mJedis.get(key);
            if (result != null) {
                return result;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            release(mJedis);
        }
        return null;
    }

    public String get(String key) {
        Jedis mJedis = getJedis();
        try {
            String result = mJedis.get(key);
            if (result != null) {
                return result;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            release(mJedis);
        } finally {
            release(mJedis);
        }
        return null;
    }

    public Boolean exists(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.exists(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    public long del(String key) {
        Jedis mJedis = getJedis();
        try {
            return mJedis.del(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            release(mJedis);
        }
        return -1;
    }

    public static  void main(String args[]){

        RedisService redisService = new RedisService();

        String key="test_set_20210216";
        String value="1232";

        redisService.set(key, value,1800);
        redisService.display("redis set() after");

        //
        CountDownLatch cdl = new CountDownLatch(1);
        //并发1000
        for(int i=0;i<1000;i++){
            new Thread(()->{
                try {
                    cdl.await();  //hand 住线程，等所有线程篡创建OK了，再并发开始业务
                    System.out.println(System.currentTimeMillis()+":"+Thread.currentThread().getName()+"--->"+"redis get() now,value:"+redisService.get(key));
                    redisService.display("redis get() after");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
        }
        cdl.countDown();//可以开始并发

        //String key="test_set_20210216";
        //String value="1232";

        //redisService.set(key, value,180);
        //String str=redisService.get(key);
        //System.out.println(str);

        //redisService.del(key);
    }
}
