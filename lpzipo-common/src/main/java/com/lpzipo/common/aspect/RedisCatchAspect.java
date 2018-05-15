package com.lpzipo.common.aspect;

import com.lpzipo.common.annotation.ExtendKey;
import com.lpzipo.common.annotation.RedisDataCache;
import com.lpzipo.common.redis.RedisClient;
import com.lpzipo.common.util.ClassUtil;
import com.lpzipo.common.util.LoggerUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @Author wb.liuzetian
 * @Date 2018/4/12 19:31
 * @Dcription TODO
 */
@Aspect
@Component
@Order(2)
public class RedisCatchAspect {

    @Around("@annotation(com.lpzipo.common.annotation.RedisDataCache)")
    public Object dataCatch(ProceedingJoinPoint joinPoint) throws Throwable {
        RedisDataCache dataCache = getRedisCatch(joinPoint);
        String extend = getExtendKey(joinPoint);
        Object object = RedisClient.getSerialized(dataCache.key()+extend,Object.class);
        //如果redis中不存在
        if(object != null){
            object = joinPoint.proceed();
            if(object!=null){
                //序列化后 存入redis
                RedisClient.setSerialized(dataCache.key()+extend, object);
                RedisClient.expireSerialized(dataCache.key()+extend,dataCache.expire());
            }
        }
        if(dataCache != null){

        }
        return object;
    }

    /**
     * 获取方法参数作为key
     * @param joinPoint
     * @return
     */
    private String getExtendKey(ProceedingJoinPoint joinPoint) {
        String extKey = "";
        Object[] args = joinPoint.getArgs();
        if(args != null && args.length > 0){
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            //获取方法参数上的所有注解 二维数组 第一个参数是参数下标 第二参数是注解下标 因为一个参数可能会有多个注解
            Annotation[][] annotations = methodSignature.getMethod().getParameterAnnotations();
            Map<String, String> keys = new HashMap<>(5);
            for (int i = 0; i<annotations.length;i++){
                ExtendKey extendKey = ClassUtil.getAnnotation(ExtendKey.class,annotations[i]);
                if(extendKey != null){
                    Object arg = args[i];
                    if(arg != null){
                        try {
                            keys.put((String) ClassUtil.reflexMthodVal(arg,extendKey.method(),null), arg.toString());
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (keys != null && keys.size() > 0) {
                for (String key : keys.values()) {
                    extKey = extKey + key;
                }
            }
        }
        return extKey;
    }

    /**
     * 获取ReisDataCache注解
     * @param joinPoint
     * @return
     */
    private RedisDataCache getRedisCatch(ProceedingJoinPoint joinPoint) {
        RedisDataCache dataCache = null;
        try {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            dataCache = method.getAnnotation(RedisDataCache.class);
        }catch (Exception e){
            LoggerUtil.error("Get redis data catch annotation error :",e);
        }
        return  dataCache;
    }
}
