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
            log.error("StringRedisService.set() error.",ex);
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
            log.error("StringRedisService.get() error.",ex);
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
    public String get2(String key){
        Jedis mJedis = getJedis();
        try{
            return mJedis.get(key);
        }catch (Exception ex){
            log.error("StringRedisService.get() error.",ex);
        }finally {
            //release(mJedis);
        }
        return null;
    }


    public String aaa(String key){
        Jedis mJedis = getJedis();
        try{

        }catch (Exception ex){

        }finally {

        }
        return null;
    }

}
