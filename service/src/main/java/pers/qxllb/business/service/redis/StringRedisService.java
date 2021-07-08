package pers.qxllb.business.service.redis;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * String类型数据操作
 *
 * @author liangb
 * @version 1.0
 * @date 2021/7/6 9:18
 */
@Slf4j
public class StringRedisService {

    private final static String SUCCESS_RESULT = "OK";

    private StringRedisService(){}

    /**
     * 获取单例对象实例
     * @return
     */
    public static StringRedisService getInstance(){
        return InnerStringRedis.SINGLETON.stringRedisService;
    }

    /**
     * 枚举类单例模式
     */
    public enum InnerStringRedis{
        /**
         * 仅定义一个实例枚举，不能多个,否则会实例化多个
         */
        SINGLETON;

        /**
         * 外部类对象引用
         */
        private StringRedisService stringRedisService;

        /**
         * JVM保证这个方法只调用一次
         */
        InnerStringRedis(){
            stringRedisService = new StringRedisService();
        }
    }

    /**
     * 获取连接资源
     * @return
     */
    private Jedis getJedis(){
        return RedisPoolUtil.getJedis();
    }

    /**
     * 释放连接资源
     * @param jedis
     */
    private void release(Jedis jedis){
        RedisPoolUtil.release(jedis);
    }

    /**
     * 获取Jedis连接状态
     * @param str
     */
    public void redisStatus(String str) {
        RedisPoolUtil.redisStatus(str);
    }

    /**
     * 设置永不过期的对象或数组存json
     * @param key key
     * @param objectValue 对象或者数组泛型
     * @param <T> 对象或者数组泛型
     * @return true or false
     */
    public <T> boolean setObjectToJson(String key,T objectValue){
        return setObjectToJson(key,objectValue,-1);

    }

    /**
     * 设置有效期的对象或数组存json
     * @param key key
     * @param objectValue 对象或者数组泛型
     * @param expire 失效秒数
     * @param <T> 对象或者数组泛型
     * @return  true or false
     */
    public <T> boolean setObjectToJson(String key,T objectValue,int expire){
        String strJson = JSON.toJSONString(objectValue);
        return set(key,strJson,expire);
    }

    /**
     * 返回Object对象
     * @param key
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T getJson2Object(String key,Class<T> tClass){
        String jsonStr = get(key);
        return JSON.parseObject(jsonStr,tClass);
    }

    /**
     * 返回List对象
     * @param key
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> List<T> getJson2List(String key, Class<T> tClass){
        String jsonStr = get(key);
        return JSON.parseArray(jsonStr,tClass);
    }

    /**
     * 永不过期的set
     * @param key key
     * @param value 字符串
     * @return  true or false
     */
    public boolean set(String key,String value){
        return set(key,value,-1);
    }

    /**
     * 有效期的set
     * @param key key
     * @param value 字符串
     * @param expire 失效秒数
     * @return  true or false
     */
    public boolean set(String key,String value, int expire){
        Jedis mJedis = getJedis();
        try{
            String result = mJedis.set(key, value);
            if (expire > 0) {
                mJedis.expire(key, expire);
            }
            return SUCCESS_RESULT.equals(result);
        }catch (Exception ex){
            log.error("StringRedisService.set() error,key:"+key,ex);
        }finally {
            release(mJedis);
        }
        return false;
    }

    /**
     * 获取key数据
     * @param key key
     * @return
     */
    public String get(String key){
        Jedis mJedis = getJedis();
        try{
            return mJedis.get(key);
        }catch (Exception ex){
            log.error("StringRedisService.get() error,key:"+key,ex);
        }finally {
            release(mJedis);
        }
        return null;
    }

    /**
     * 返回所有(一个或多个)给定 key 的值。
     * 如果给定的 key 里面，有某个 key 不存在，那么这个 key 返回特殊值 null
     * 减少网络带宽
     * @param key
     * @return
     */
    public List<String> mget(String[] key){
        Jedis mJedis = getJedis();
        try{
            return mJedis.mget(key);
        }catch (Exception ex){
            log.error("StringRedisService.mget() error,key:"+key,ex);
        }finally {
            release(mJedis);
        }
        return null;
    }

    /**
     * 将 key 中储存的数字值增一，原子性操作
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作
     * 本操作的值限制在 64 位(bit)有符号数字表示之内
     * @param key
     * @return
     */
    public Long incr(String key){
        Jedis mJedis = getJedis();
        try{
            return mJedis.incr(key);
        }catch (Exception ex){
            log.error("StringRedisService.incr() error,key:"+key,ex);
        }finally {
            release(mJedis);
        }
        return null;
    }

    /**
     * 将 key 中储存的数字值增一，原子性操作
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
     * 本操作的值限制在 64 位(bit)有符号数字表示之内。
     * @param key
     * @return
     */
    public Long incrBy(String key,long value){
        Jedis mJedis = getJedis();
        try{
            return mJedis.incrBy(key,value);
        }catch (Exception ex){
            log.error("StringRedisService.incrBy() error,key:"+key,ex);
        }finally {
            release(mJedis);
        }
        return null;
    }

    /**
     * 将 key 中储存的数字值减一，原子性操作
     * @param key
     * @return
     */
    public Long decr(String key){
        Jedis mJedis = getJedis();
        try{
            return mJedis.decr(key);
        }catch (Exception ex){
            log.error("StringRedisService.decr() error,key:"+key,ex);
        }finally {
            release(mJedis);
        }
        return null;
    }

    /**
     * 将 key 中储存的数字值减一，原子性操作
     * @param key
     * @return
     */
    public Long decrBy(String key,long value){
        Jedis mJedis = getJedis();
        try{
            return mJedis.decrBy(key,value);
        }catch (Exception ex){
            log.error("StringRedisService.decrBy() error,key:"+key,ex);
        }finally {
            release(mJedis);
        }
        return null;
    }


    /**
     * 获取key数据，不释放连接资源验证
     * @param key
     * @return
     */
    public String getErrorTest(String key){
        Jedis mJedis = getJedis();
        try{
            return mJedis.get(key);
        }catch (Exception ex){
            log.error("StringRedisService.get2() error,key:"+key,ex);
        }finally {
            //release(mJedis);
        }
        return null;
    }
}
