package pers.qxllb.business.service.redis;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    private final static int OK_RESULT=1;

    private final static Integer SPLIT_LENGTH_LIMIT = 2;

    private final static Long EXPIRE_LIMIT = 1000L;

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
            String result = mJedis.setex(key, expire, value);
//            String result = mJedis.set(key, value);
//            if (expire > 0) {
//                mJedis.expire(key, expire);
//            }
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
     * 设置一批key和value
     * 用于同时设置一个或多个 key-value 对
     * @param keyValues
     * @param expire
     * @return
     */
    public boolean mset(Map<String, String> keyValues, int expire){
        try(Jedis jedis = getJedis()) {
            List<String> keyDataList = Lists.newArrayList();
            for (Map.Entry<String, String> entry : keyValues.entrySet()) {
                keyDataList.add(entry.getKey());
                keyDataList.add(entry.getValue());
            }

            String[] keyDatas = keyDataList.toArray(new String[0]);

            String result = jedis.mset(keyDatas);
            if (expire > 0) {
                for (Map.Entry<String, String> entry : keyValues.entrySet()) {
                    jedis.expire(entry.getKey(), expire);
                }
            }

            return SUCCESS_RESULT.equals(result);
        } catch (Exception ex) {
            log.error("StringRedisService.mset() error,keys:"+keyValues,ex);
        }

        return false;
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
     * 分布式锁处理
     * Redis Setnx（SET if Not eXists） 命令在指定的 key 不存在时，为 key 设置指定的值
     * 设置成功，返回 1 。 设置失败，返回 0
     * 锁成功值命名规范： 线程名_年-月-日- 时:分:秒_[锁成功的时间戳毫秒]
     *                main_2021-07-11 15:29:42_1625988582162
     * @param key
     * @param expire
     * @return
     */
    public boolean lockSet(String key, int expire){
        try (Jedis jedis = getJedis()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String t = Thread.currentThread().getName();

            /**
             * 原子性设置键值和过期时间
             * 一、设置键值
             *  NX ：只在键不存在时，才对键进行设置操作。 SET key value NX 效果等同于 SETNX key value 。
             *  XX ：只在键已经存在时，才对键进行设置操作。
             * 二、设置键值过期时间的单位
             *  EX second ：设置键的过期时间为 second 秒。
             *  PX millisecond ：设置键的过期时间为 millisecond 毫秒。
             */
            String str = jedis.set(key, t + "_" + sdf.format(new Date()) + "_" + System.currentTimeMillis(),"NX","EX",expire);
            return StringUtils.isNotBlank(str)?true:false;
        }catch (Exception ex){
            log.error("StringRedisService.lockSet() error,key:"+key,ex);
        }
        return false;
    }

    /**
     * 删除分布式锁,通过判断是否当前线程名称的分布式锁
     * 1. 存在分布式锁提前过期的情况，线程没干活完，线程锁过期失效了；（我们评估操作共享资源的时间不准确导致的）
     * 2. 防止释放别线程的锁；（一个客户端释放了其它客户端持有的锁）
     * @param key
     */
    public void lockDel(String key){
        try (Jedis jedis = getJedis()) {
            String t = Thread.currentThread().getName(); //当前线程名称
            String string = jedis.get(key);
            if (StringUtils.isNotBlank(string)) {
                String[] split = string.split("_");  //长度为3；main_2021-07-11 15:29:42_1625988582162
                if (split.length >= SPLIT_LENGTH_LIMIT) {
                    String tTmp = split[0]; //线程名称
                    if(t.equals(tTmp)){
                        //仅删除自己上锁的key,防止之前设置的key过期时间已到，这个时候是其他线程争抢到锁了，所以不能前面的线程锁删除后面的线程锁
                        jedis.del(key);
                    }
                }
            }
        }catch (Exception ex){
            log.error("StringRedisService.lockDel() error,key:"+key,ex);
        }
    }

    /**
     * 分布式锁处理(setnx和expire是两个操作)
     * Redis Setnx（SET if Not eXists） 命令在指定的 key 不存在时，为 key 设置指定的值
     * 设置成功，返回 1 。 设置失败，返回 0
     * 锁成功值命名规范： 线程名-年-月-日- 时:分:秒_[锁成功的时间戳毫秒]
     *              main-2021-07-11 15:29:42_1625988582162
     * @param key key
     * @param expire  单位:秒
     * @return
     */
    public boolean lock(String key, int expire) {
        boolean result = false;
        try (Jedis jedis = getJedis()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String t = Thread.currentThread().getName();

            long setnx = jedis.setnx(key, t + "-" + sdf.format(new Date()) + "_" + System.currentTimeMillis());
            result = setnx == OK_RESULT ? true : false;
            if (result) {
                //锁成功
                jedis.expire(key, expire == 0 ? 60 : expire);
            } else {
                // 防止分布式锁第二部锁失效异常的兜底处理；setnx和expire是两步操作，可能第二没设置成功，所以锁失败的需要判断是否死锁
                String string = jedis.get(key);
                if (StringUtils.isNotBlank(string)) {
                    String[] split = string.split("_");
                    if (split.length >= SPLIT_LENGTH_LIMIT) {
                        long lastLockTimeMillis = Long.valueOf(split[split.length - 1]); //上次锁的时间
                        long now = System.currentTimeMillis();   //当前时间
                        long v = now - lastLockTimeMillis;       //锁的时长
                        if (v > (expire + 1) * EXPIRE_LIMIT) {   //如果当前时间-上次锁的时间 大于锁过期时间，则兜底删除
                            //防止之前锁的过期没释放
                            jedis.del(key);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            log.error("StringRedisService.lock() error,key:"+key,ex);
        }
        return result;
    }

    /**
     * 返回 key 所储存的字符串值的长度
     * @param key
     * @return
     */
    public Long strlen(String key){
        Jedis mJedis = getJedis();
        try{
            return mJedis.strlen(key);
        }catch (Exception ex){
            log.error("StringRedisService.strlen() error,key:"+key,ex);
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
