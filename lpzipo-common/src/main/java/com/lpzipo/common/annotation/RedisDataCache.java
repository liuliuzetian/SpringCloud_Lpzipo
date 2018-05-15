package com.lpzipo.common.annotation;

import java.lang.annotation.*;

/**
 * @version 1.0
 * @Author wb.liuzetian
 * @Date 2018/4/12 16:10
 * @Dcription TODO
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RedisDataCache {

    String key();

    int expire() default 2*60;

}
