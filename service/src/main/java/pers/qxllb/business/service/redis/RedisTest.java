package pers.qxllb.business.service.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * TODO
 *
 * @author liangb
 * @version 1.0
 * @date 2021/7/6 12:39
 */
public class RedisTest {

    private static StringRedisService stringRedisService = RedisHelper.getStringRedis();

    public  void testRedisResourceError(){

        String key="test";
        stringRedisService.set(key,"123",60);

        //并发测试
        CountDownLatch cdl = new CountDownLatch(1);
        //并发1000
        for(int i=0;i<1000;i++){
            new Thread(()->{
                try {
                    cdl.await();  //hand 住线程，等所有线程篡创建OK了，再并发开始业务
                    System.out.println(System.currentTimeMillis()+":"+Thread.currentThread().getName()+"--->"+"redis get() now,value:"+stringRedisService.getErrorTest(key));
                    stringRedisService.redisStatus("redis get() after");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
        }
        cdl.countDown();//可以开始并发
    }

    public void testSetString(){

        /**
         * 1.存文本
         */
        String strKey="str";
        String strValue="value";
        stringRedisService.set(strKey,strValue);
        System.out.println("get:"+stringRedisService.get(strKey));

        /**
         * 2.对象存JSON
         */
        Redis redis = new Redis();
        redis.setName("redis");
        redis.setAge(13);

        String strObjectKey="strObjectKey";
        stringRedisService.setObjectToJson(strObjectKey, redis);
        Redis redis1 = stringRedisService.getJson2Object(strObjectKey,Redis.class);
        System.out.println("getJson2Object:"+(Objects.nonNull(redis1)?redis:null));

        /**
         * 3.数组存JSON
         */
        List<String> stringList = Arrays.asList("abc","efg","okr");
        String strListKey="strListKey";
        stringRedisService.setObjectToJson(strListKey, stringList);
        List<String> listValue = stringRedisService.getJson2List(strListKey,String.class);
        System.out.println("getJson2List:"+(Objects.nonNull(listValue)?listValue: Collections.EMPTY_LIST));

    }

    public void testMsetString(){
        Redis redis = new Redis();
        redis.setAge(11);
        redis.setName("name11");

        Redis redis1 = new Redis();
        redis1.setName("name22");
        redis1.setAge(22);

        /**
         * 1. mset,减少带宽
         */
        Map<String,String> map = Maps.newConcurrentMap();
        map.put("mset1", JSON.toJSONString(redis));
        map.put("mset2", JSON.toJSONString(redis1));
        System.out.println("mset result:"+stringRedisService.mset(map, 600));


        /**
         * 2.mget,减少带宽
         */
        String[] keys = new String[]{"mset1","testKey","mset2"};
        //一次拿多个KEY
        List<String> mget = stringRedisService.mget(keys);

        //封装到MAP
        Map<String,String> countMap = new HashMap<>(keys.length);
        if(CollectionUtils.isNotEmpty(mget)){
            for(int i=0;i<keys.length;i++){
                countMap.put(keys[i],mget.get(i));
            }
        }
        System.out.println("mget mset1:"+(StringUtils.isBlank(countMap.get("mset1"))?"":countMap.get("mset1")));
        System.out.println("mget testKey:"+(StringUtils.isBlank(countMap.get("testKey"))?"":countMap.get("testKey")));
        System.out.println("mget mset2:"+(StringUtils.isBlank(countMap.get("mset2"))?"":countMap.get("mset2")));
    }

    public void testSetBit(){

    }

    public void testStrIncr(){
        String key="incrKey";
        System.out.println("incr.num:"+stringRedisService.incr(key));
        System.out.println("incrBy.num:"+stringRedisService.incrBy(key,5));
    }

    public void testStrDecr(){
        String key="decrKey";
        System.out.println("decr.num:"+stringRedisService.decr(key));
        System.out.println("decrBy.num:"+stringRedisService.decrBy(key,8));
    }

    public  void testStringRedis(){
//        testSetString();
//        testMsetString();
//        testStrIncr();
//        testStrDecr();
//        testStrLen();
//        testLock();
        testLockSet();
        testSetBit();
    }

    public void testLockSet(){

        System.out.println(stringRedisService.lockSet("lockSet", 60));
        System.out.println(stringRedisService.lockSet("lockSet", 80));

        stringRedisService.lockDel("lockSet");

    }

    public void testLock(){
        System.out.println(stringRedisService.lock("lock_123", 60));
        System.out.println(stringRedisService.lock("lock_123", 60));
    }

    public void testStrLen(){
        System.out.println("exist key strlen result:"+stringRedisService.strlen("strObjectKey"));
        System.out.println("not exist key strlen result:"+stringRedisService.strlen("abc"));
    }

    public void testBaseRedis(){
        //-------------删除keys----------------------
        testSetString();
        System.out.println("del key result:"+RedisPoolUtil.del("str"));  //单个key删除

        List<String> keys = Arrays.asList("strObjectKey","strListKey","abc");
        String[] strKeys=keys.toArray(new String[0]);
        System.out.println("del keys result:"+RedisPoolUtil.del(strKeys));   //批量删除keys

        testStrIncr();
        //--------设置key过期时间------------------
        System.out.println("expire key result:"+RedisPoolUtil.expire("incrKey", 600));

        //-----------判断key是否存在----------------
        System.out.println("exists key result:"+RedisPoolUtil.exists("incrKey"));
    }

    public static void main(String[] args){
        RedisTest redisTest = new RedisTest();

        //测试redis资源不释放的异常处理
        //redisTest.testRedisResourceError();

        //base类型处理
        //redisTest.testBaseRedis();

        //String类型处理
        redisTest.testStringRedis();





    }

}

@Data
class Redis{
    private String name;
    private int age;
}
