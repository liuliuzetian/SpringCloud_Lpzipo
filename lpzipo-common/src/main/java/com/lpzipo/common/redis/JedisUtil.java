package com.lpzipo.common.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 * @version 1.0
 * @Author wb.liuzetian
 * @Date 2018/4/12 11:28
 * @Dcription TODO
 */
public class JedisUtil {

    public static void main(String[] args) {
//        Jedis jedis = new Jedis("10.240.155.100",7002);
//        jedis.set("name","liuzetian");
//        String name = jedis.get("name");
//        System.out.println(name);
//        jedis.close();
        getJedisPool();
    }

    public static JedisPool getJedisPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(10);
        config.setMaxWaitMillis(100000);
        JedisPool jedisPool = new JedisPool(config, "10.240.155.100", 7001, 180,null);
        RedisClient client = new RedisClient();
        client.setJedisPool(jedisPool);
        String res = RedisClient.ping();
        String res1 = RedisClient.info();
        Set<String> keys = RedisClient.keys("*");
        System.out.println(res+keys);
        return null;
    }

}
