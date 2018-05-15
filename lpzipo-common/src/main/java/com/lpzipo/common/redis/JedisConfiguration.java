package com.lpzipo.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableConfigurationProperties(JedisProperties.class)
@ConditionalOnClass(RedisClient.class)//当RedisClient存在时才实例化bean
public class JedisConfiguration {

    @Autowired
    private JedisProperties jedisProperties;

    @Bean(name = "jedisPool")
    public JedisPool jedisPool (){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(jedisProperties.getMaxTotal());
        config.setMaxIdle(jedisProperties.getMaxIdle());
        config.setMaxWaitMillis(jedisProperties.getMaxWaitMillis());
        return new JedisPool(config, jedisProperties.getHost(), jedisProperties.getPort(), jedisProperties.getTimeout(), jedisProperties.getPassword());
    }

    @Bean()
    @ConditionalOnMissingBean(RedisClient.class)
    public RedisClient redisClient(@Qualifier("jedisPool")JedisPool pool){
        RedisClient redisClient = new RedisClient();
        redisClient.setJedisPool(pool);
        return redisClient;
    }

}
