package com.lpzipo.route.common;

/**
 * @version 1.0
 * @Author wb.liuzetian
 * @Date 2018/4/19 13:47
 * @Dcription TODO
 */
public class Constance {

    /**可以在请求被路由之前被调用*/
    public static String ZUUL_TYPE_PRE= "pre";
    /**可以在请求被路由时被调用*/
    public static String ZUUL_TYPE_ROUTING= "routing";
    /**在routing和eroor过滤器之后被调用*/
    public static String ZUUL_TYPE_POST="post";
    /**处理请求发生错误时调用*/
    public static  String ZUUL_TYPE_ERROR="error";

}
