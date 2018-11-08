package com.mdh.cache.cacheOfAnno;

/**
 * 账号类，具备基本的 id 和 name 属性，且具备 getter 和 setter 方法
 * @author madonghao
 * @date 2018/11/6
 */
public class Account {

    private int id;
    private String name;

    public Account(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
