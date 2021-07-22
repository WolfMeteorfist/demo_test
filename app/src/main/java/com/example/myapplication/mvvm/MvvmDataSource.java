package com.example.myapplication.mvvm;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.basemvvm.BaseDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author yuechou.zhang
 * @since 2021/7/16
 */
public class MvvmDataSource extends BaseDataSource {

    final List<Student> studentList = new ArrayList<>();

    {
        studentList.add(new Student("张飞", 26));
        studentList.add(new Student("刘备", 30));
        studentList.add(new Student("关羽", 28));
        studentList.add(new Student("黄忠", 30));
        studentList.add(new Student("马超", 18));
        studentList.add(new Student("徐晃", 22));
        studentList.add(new Student("司马懿", 30));
        studentList.add(new Student("董卓", 34));
        studentList.add(new Student("孙权", 29));
        studentList.add(new Student("袁绍", 28));
        studentList.add(new Student("曹操", 30));
    }

    public MvvmDataSource(Context context) {
        super(context);
    }

    @Override
    public void init() {
        Log.i("zyc", "mvvm data source 被初始化.");
    }

    @Override
    public void release() {
        Log.i("zyc", "mvvm data source 被釋放.");
    }

    public List<Student> getAllStudents() {
        Log.i("zyc", "開始讀取数据");
        return studentList;
    }

    public Student getStudent(int index) {
        Log.i("zyc", "開始讀取数据");
        return studentList.get(index);

    }

    public Student getRandomStudent() {
        Log.i("zyc", "開始讀取数据");
        Random random = new Random();
        int whichOne = random.nextInt(studentList.size() - 1);
        return studentList.get(whichOne);
    }
}
