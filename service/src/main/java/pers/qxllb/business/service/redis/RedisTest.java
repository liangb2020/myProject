package pers.qxllb.business.service.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
                    System.out.println(System.currentTimeMillis()+":"+Thread.currentThread().getName()+"--->"+"redis get() now,value:"+stringRedisService.get2(key));
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
        System.out.println(stringRedisService.get(strKey));

        /**
         * 2.对象存JSON
         */
        Redis redis = new Redis();
        redis.setName("redis");
        redis.setAge(13);

        String strObjectKey="strObjectKey";
        stringRedisService.setObjectToJson(strObjectKey, redis);
        Redis redis1 = stringRedisService.getJson2Object(strObjectKey,Redis.class);
        System.out.println(Objects.nonNull(redis1)?redis:null);

        /**
         * 3.数组存JSON
         */
        List<String> stringList = Arrays.asList("abc","efg","okr");
        String strListKey="strListKey";
        stringRedisService.setObjectToJson(strListKey, stringList);
        List<String> listValue = stringRedisService.getJson2List(strListKey,String.class);
        System.out.println(Objects.nonNull(listValue)?listValue: Collections.EMPTY_LIST);
    }

    public void testSetBit(){

    }

    public  void testStringRedis(){
        testSetString();
        testSetBit();
    }

    public static void main(String[] args){

        RedisTest redisTest = new RedisTest();

        //测试redis资源不释放的异常处理
        //redisTest.testRedisResourceError();

        //String类型处理
        redisTest.testStringRedis();


    }

}

@Data
class Redis{
    private String name;
    private int age;
}
