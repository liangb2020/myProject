package pers.qxllb.business.service.redis;

/**
 * TODO
 *
 * @author liangb
 * @version 1.0
 * @date 2021/7/6 12:29
 */
public class RedisUtil {

    /**
     * 获取String redis处理对象
     * @return
     */
    public static StringRedisService getStringRedis(){
        return StringRedisService.getInstance();
    }

}
