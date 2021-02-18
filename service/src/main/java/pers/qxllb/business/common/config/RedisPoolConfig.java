package pers.qxllb.business.common.config;

import lombok.Data;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * TODO
 *
 * @author liangb
 * @version 1.0
 * @date 2021/2/16 20:30
 */
@Data
public class RedisPoolConfig {

    private RedisConfig redisConfig;

    private JedisPool jedisPool;

    public RedisPoolConfig(){

        redisConfig = new RedisConfig();
        JedisPoolConfig config = new JedisPoolConfig();

        //最大空闲连接数, 应用自己评估，不要超过ApsaraDB for Redis每个实例最大的连接数
        config.setMaxIdle(redisConfig.getRedisPoolMaxIdle());
        //最大连接数, 应用自己评估，不要超过ApsaraDB for Redis每个实例最大的连接数
        config.setMaxTotal(redisConfig.getRedisPoolMaxTotal());
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);
        config.setMaxWaitMillis(redisConfig.getMaxWaitMillis());

        jedisPool = new JedisPool(config, redisConfig.getRedisHost(), redisConfig.getRedisPort(),
                redisConfig.getRedisTimeout(), redisConfig.getRedisPassword());
    }
}
