package com.example.myapplication.bean;

import androidx.databinding.BaseObservable;

import kotlin.jvm.Transient;

/**
 * @author yuechou.zhang
 * @since 2021/7/13
 */
public class User extends BaseObservable {

    public String name;

    public int age;

    @Transient
    public boolean check;

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
