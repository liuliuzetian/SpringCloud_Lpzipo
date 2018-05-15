package com.lpzipo.common.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @version 1.0
 * @Author wb.liuzetian
 * @Date 2018/4/11 13:47
 * @Dcription TODO
 */
public class  ClassUtil {


    /**
     * 根据类型反射出Class对象
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    public static Class reflexClass(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

    /**
     *
     * @param object
     * @return
     */
    public static  Class reflexClass(Object object){
        if(object == null){
            return  null;
        }
        return object.getClass();
    }

    /**
     * 获取私有构造
     * @param object
     * @param clazz
     * @return
     * @throws NoSuchMethodException
     */
    public static Constructor reflexConstructor(Object object,Class clazz) throws NoSuchMethodException {
        return  reflexClass(object).getDeclaredConstructor(clazz);
    }


    /**
     * 获取类某一字段属性值 有get方法
     * @param object
     * @param fieldName
     * @return
     * @throws NoSuchFieldException
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object reflexFieldVal(Object object,String fieldName) throws NoSuchFieldException, IntrospectionException, InvocationTargetException, IllegalAccessException {
        Class clazz = reflexClass(object);
        if(clazz == null){
            return  null;
        }
        Field field = clazz.getDeclaredField(fieldName);
        PropertyDescriptor pd = new PropertyDescriptor(field.getName(),clazz);
        Method getMethod = pd.getReadMethod();
        return getMethod.invoke(object);
    }

    /**
     * 获取方法返回值
     * @param obj
     * @param mthodName
     * @param paramter
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object reflexMthodVal(Object obj, String mthodName,Object paramter) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class clazz = reflexClass(obj);
        Method method = clazz.getMethod(mthodName);
        Object resObj = null;
        if(paramter == null){
            resObj =  method.invoke(obj);
        }else{
            resObj = method.invoke(obj,paramter);
        }
        return  resObj;
    }

    /**
     * 获取属性
     * @param object
     * @param fieldName
     * @return
     * @throws NoSuchFieldException
     */
    public static Field reflexField(Object object,String fieldName) throws NoSuchFieldException {
        Class clazz = reflexClass(object);
        if(clazz == null){
            return  null;
        }
        Field field = clazz.getDeclaredField(fieldName);
        return  field;
    }

    /**
     * 获取指定类指定方法上指定注解
     * @param object
     * @param methodName
     * @param annotion
     * @return
     * @throws NoSuchMethodException
     */
    public Annotation reflexAnnotationMthod(Object object,String  methodName,Class annotion) throws NoSuchMethodException {
        Method method = object.getClass().getMethod(methodName);
        Annotation annotation = method.getAnnotation(annotion);
        return annotation;
    }

    /**
     * 获取方法上面所有注解
     * @param object
     * @param methodName
     * @return
     * @throws NoSuchMethodException
     */
    public Annotation[] reflexAnnotationsMthod(Object object,String  methodName) throws NoSuchMethodException {
        Method method = object.getClass().getMethod(methodName);
        Annotation[] annotations = method.getAnnotations();
        return annotations;
    }

    /**
     * 获取方法指定参数上指定注解
     * @param annotionClass 要获取的指定注解类
     * @param anno 指定参数上的注解数组
     * @param <T>
     * @return
     */
    public static  <T extends Annotation>T getAnnotation(Class<T> annotionClass, Annotation[] anno) {
        if(anno != null && anno.length>0){
            for(Annotation annotation : anno){
                if(annotionClass.equals(annotation.annotationType())){
                    return (T) annotation;
                }
            }
        }
        return  null;
    }


    public static void main(String[] args) {
        User user = new User();
        user.setAge(14);
        user.setHeight(188.0f);
        user.setName("王华");
        try {
            System.out.println(reflexFieldVal(user,"name"));
            System.out.println(reflexMthodVal(user,"getAge",null));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}
