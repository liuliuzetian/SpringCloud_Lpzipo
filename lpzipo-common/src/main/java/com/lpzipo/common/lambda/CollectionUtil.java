package com.lpzipo.common.lambda;

import com.lpzipo.common.util.ClassUtil;
import com.lpzipo.common.util.User;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author wb.liuzetian
 * @Date 2018/4/11 11:22
 * @Dcription TODO
 */
public class CollectionUtil<T extends Object> {

    /**
     * 去除实体类中重复数据
     * @param dupliList
     * @param filed
     * @return
     */
    public List<Object> removeDuplicates(List<T> dupliList, String filed){
        HashSet<T> set = new HashSet<>();
        List resList = dupliList.stream().filter(obj ->{
            try {
                Object filedRes =ClassUtil.reflexFieldVal(obj,filed);
            return  set.add((T) filedRes);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IntrospectionException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return true;
        }).collect(Collectors.toList());
        return  resList;
    }

    public static void main(String[] args) {
       List<User> users = new ArrayList<>();
        for(int  i = 0;i<20;i++){
            User user  = new User();
            if(i > 10){
                user.setName("aa");
            }else{
                user.setName("aa"+i);
            }
            users.add(user);
        }
        CollectionUtil collectionUtil = new CollectionUtil();
        List<User> newUses = collectionUtil.removeDuplicates(users,"age");
        System.out.println(newUses);
    }
}
