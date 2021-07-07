package pers.qxllb.business.common.config;

import lombok.Data;

/**
 * redis配置管理
 *
 * @author liangb
 * @version 1.0
 * @date 2021/2/16 20:07
 */
@Data
public class RedisConfig {

    /**
     * redis服务IP
     */
    private String redisHost="127.0.0.1";

    /**
     * redis连接端口
     */
    private int redisPort=6379;

    /**
     * redis接入密码
     */
    private String redisPassword="123456";

    /**
     * 最大空闲连接数, 应用自己评估，不要超过ApsaraDB for Redis每个实例最大的连接数
     */
    private int redisPoolMaxIdle=7;

    /**
     * 最大连接数, 应用自己评估，不要超过ApsaraDB for Redis每个实例最大的连接数
     */
    private int redisPoolMaxTotal=10;

    /**
     * redis连接超时时长
     */
    private int redisTimeout=3000;

    /**
     * 获取连接时的最大等待毫秒数
     * 1.不设置默认-1（锁等待）
     * 2.设置超时，则异常抛出
     */
    //private int maxWaitMillis=500;
    private int maxWaitMillis=-1;

}
