package com.example.asm_ht_nguyendinhtrung_pk02294.home_list;

import java.io.Serializable;

public class Student implements Serializable {
    private Integer id, clazz_id;
    private String username,password, name;

    public Student() {
    }

    public Student(Integer id, Integer clazz_id, String username, String password, String name) {
        this.id = id;
        this.clazz_id = clazz_id;
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClazz_id() {
        return clazz_id;
    }

    public void setClazz_id(Integer clazz_id) {
        this.clazz_id = clazz_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
