package com.lpzipo.common.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = JedisProperties.JEDIS_PREFIX)
@Data
public class JedisProperties {

    public static final String JEDIS_PREFIX = "jedis";

    private String host;

    private int port;

    private String password;

    private int timeout;

    private int maxTotal;

    private int maxIdle;

    private int maxWaitMillis;

}
