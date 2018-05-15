package com.lpzipo.route.filter;

import com.lpzipo.route.common.Constance;
import com.netflix.zuul.ZuulFilter;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @Author wb.liuzetian
 * @Date 2018/4/19 14:12
 * @Dcription TODO
 */
public class PostFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return Constance.ZUUL_TYPE_POST;
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
        System.out.println("post Filter");
        return null;
    }
}
