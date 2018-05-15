package com.lpzipo.common.annotation;

import java.lang.annotation.*;

/**
 * @version 1.0
 * @Author wb.liuzetian
 * @Date 2018/4/13 10:26
 * @Dcription TODO
 */
@Target(ElementType.PARAMETER)//方法参数注解
@Retention(RetentionPolicy.RUNTIME)//运行期一直存在
@Inherited
public @interface ExtendKey {

    /**
     * 通过方法获取参数值
     * @return
     */
    String method();

}
