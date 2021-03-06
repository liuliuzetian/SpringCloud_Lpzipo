package com.lpzipo.client.feign.pojo;

public class User {

    /**
     * 必须要有无参构造 否则json转换会出错
     */
   public User(){}

   public User(String name, Integer age){
        this.name = name;
        this.age = age;
    }

    private String name;

    private  Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
