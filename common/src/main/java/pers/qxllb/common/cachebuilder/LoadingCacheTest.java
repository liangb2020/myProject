package pers.qxllb.common.cachebuilder;

import com.google.common.cache.*;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import lombok.Data;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 类描述:
 *
 * @author liangb
 * @date 2021/6/8  0:46
 */
public class LoadingCacheTest {

    //缓存接口这里是LoadingCache，LoadingCache在缓存项不存在时可以自动加载缓存
    private static LoadingCache<Integer, Student> studentCache
            //CacheBuilder的构造函数是私有的，只能通过其静态方法newBuilder()来获得CacheBuilder的实例
            = CacheBuilder.newBuilder()
            //设置并发级别为8，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(8)
            //设置写缓存后8秒钟过期
            .expireAfterWrite(8, TimeUnit.SECONDS)
            //設置讀緩存后5秒過期
            //.refreshAfterWrite(5, TimeUnit.SECONDS)
            //设置缓存容器的初始容量为10
            .initialCapacity(10)
            //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
            .maximumSize(100)
            //设置要统计缓存的命中率
            .recordStats()
            //设置缓存的移除通知
            .removalListener(new RemovalListener<Object, Object>() {
                @Override
                public void onRemoval(RemovalNotification<Object, Object> notification) {
                    System.out.println(notification.getKey() + " was removed, cause is " + notification.getCause());
                }
            })
            //build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
            .build(
                    new CacheLoader<Integer, Student>() {
                        @Override
                        public Student load(Integer key) throws Exception {
                            System.out.println("load student:" + key);
                            return load1(key);
                        }

                        @Override
                        public ListenableFuture<Student> reload(Integer key, Student oldValue) throws Exception {
                            System.out.println("reload student:" + key);

                            //asynchronous,异步刷新
                            ListenableFutureTask<Student> task = ListenableFutureTask.create(new Callable<Student>() {
                                @Override
                                public Student call() throws Exception {
                                    //添加到异步更新队列
                                    //asyncReloadHandle(key);
                                    reload1(key);
                                    //返回旧值
                                    return oldValue;
                                }
                            });

                            return task;
                        }
                    }
            );

    public static Student load1(Integer key){
        Student student = new Student();
        student.setId(key);
        student.setName("name " + key);
        
        System.out.println("load1():"+key);

        return student;
    }

    public static void reload1(Integer key){
        Student student = new Student();
        student.setId(key);
        student.setName("name===" + key);

        System.out.println("==reload():"+key);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        for (int i = 0; i < 20; i++) {
            //从缓存中得到数据，由于我们没有设置过缓存，所以需要通过CacheLoader加载缓存数据
            Student student = studentCache.get(1);
            System.out.println(student);
            //休眠1秒
            TimeUnit.SECONDS.sleep(1);
        }

        System.out.println("cache stats:");
        //最后打印缓存的命中率等 情况
        System.out.println(studentCache.stats().toString());
    }
}

@Data
class Student {

    private Integer id;
    private String name;

}
