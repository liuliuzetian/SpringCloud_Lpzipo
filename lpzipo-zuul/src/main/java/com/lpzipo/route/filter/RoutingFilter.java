package com.lpzipo.route.filter;

import com.netflix.zuul.ZuulFilter;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @Author wb.liuzetian
 * @Date 2018/4/19 14:11
 * @Dcription TODO
 */
public class RoutingFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return null;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        System.out.println("routing filter ");
        return null;
    }
}
