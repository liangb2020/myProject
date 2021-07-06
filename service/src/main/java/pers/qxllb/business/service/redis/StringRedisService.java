package pers.qxllb.business.service.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * String类型数据操作
 *
 * @author liangb
 * @version 1.0
 * @date 2021/7/6 9:18
 */
@Slf4j
public class StringRedisService extends RedisBaseService {

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

    private Jedis get(){
        return super.getJedis();
    }

    /**
     * 永不过期的set
     * @param key
     * @param value
     * @return
     */
    public String set(String key,String value){
        return set(key,value,-1);
    }

    /**
     * 有效期的set
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public String set(String key,String value, int expire){
        Jedis mJedis = getJedis();
        try{
            String result = mJedis.set(key, value);
            if (expire > 0) {
                mJedis.expire(key, expire);
            }
            return result;
        }catch (Exception ex){
            log.error("StringRedisService.set() error.",ex);
        }finally {
            release(mJedis);
        }
        return null;
    }

    /**
     * 获取key数据
     * @param key
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


    public String aaa(String key){
        Jedis mJedis = getJedis();
        try{

        }catch (Exception ex){

        }finally {

        }
        return null;
    }

}
