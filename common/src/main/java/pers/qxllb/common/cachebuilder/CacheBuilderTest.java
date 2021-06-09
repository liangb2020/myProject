package pers.qxllb.common.cachebuilder;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 类描述:
 *
 * @author liangb
 * @date 2021/6/7  23:36
 */
public class CacheBuilderTest {

    private static Cache<Long, Stu> studentCache
            = CacheBuilder.newBuilder()
            .concurrencyLevel(10)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .refreshAfterWrite(2, TimeUnit.SECONDS)
            .maximumSize(1000)
            .recordStats()
            .build(
                    new CacheLoader<Long, Stu>() {
                        @Override
                        public Stu load(Long key) throws Exception {
                            System.out.println("CacheLoader.load(),key:" + key);
                            return play(key, "load()");
                        }

                        @Override
                        public ListenableFuture<Stu> reload(Long key, Stu oldValue) throws Exception {

                            System.out.println("CacheLoader.reload(),key:"+key);

                            //asynchronous,异步刷新
                            ListenableFutureTask<Stu> task = ListenableFutureTask.create(new Callable<Stu>() {
                                @Override
                                public Stu call() throws Exception {
                                    //添加到异步更新队列
                                    //asyncReloadHandle(key);
                                    //返回旧值
                                    return oldValue;
                                }
                            });

                            return task;
                        }
                    }
            );

    public static Stu  play(Long key, String source) {
        Stu stu = new Stu();
        stu.setId(key);
        stu.setName("liangbo:" + key);

        System.out.println(source + ": deal result:" + JSON.toJSONString(stu));
        return stu;
    }


    @SneakyThrows
    public static void main(String[] args) {

        for (int i = 0; i < 20; i++) {

            System.out.println("main(),key:"+i);

            Stu stu = studentCache.getIfPresent("key");

            //休眠1秒
            Thread.sleep(1000L);//让当前线程sleep 1秒，是为了测试load和reload时候的并发特性
        }
    }

};

@Data
class Stu {

    private Long id;
    private String name;

}

