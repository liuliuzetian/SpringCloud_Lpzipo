package com.lpzipo.route.filter;

import com.lpzipo.route.common.Constance;
import com.netflix.zuul.ZuulFilter;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @Author wb.liuzetian
 * @Date 2018/4/17 16:18
 * @Dcription TODO
 */
public class AccessFilter extends ZuulFilter {
    /**
     * 过滤器类型 决定过滤器在请求的哪个生命周期执行
     * pre：可以在请求被路由之前调用
     * route：在路由请求时候被调用
     * post：在route和error过滤器之后被调用
     * error：处理请求时发生错误时被调用
     * @return
     */
    @Override
    public String filterType() {
        return Constance.ZUUL_TYPE_PRE;
    }

    /**
     * 过滤器执行顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 判断改过滤器是否需要被执行
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        System.out.println("pre Filter");
        return null;
    }
}
