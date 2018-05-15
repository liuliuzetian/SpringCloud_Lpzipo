package com.lpzipo.route.filter;

import com.netflix.zuul.ZuulFilter;

/**
 * @version 1.0
 * @Author wb.liuzetian
 * @Date 2018/4/19 13:46
 * @Dcription TODO
 */
public class ErrorFilter extends ZuulFilter {
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
        return false;
    }

    @Override
    public Object run() {
        return null;
    }
}
