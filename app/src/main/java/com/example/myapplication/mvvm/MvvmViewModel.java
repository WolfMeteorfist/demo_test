package com.example.myapplication.mvvm;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.basemvvm.BaseViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author yuechou.zhang
 * @since 2021/7/16
 */
public class MvvmViewModel extends BaseViewModel<MvvmDataSource> {

    private final MutableLiveData<List<Student>> studentsLiveData = new MutableLiveData<>();
    //private final ObservableArrayList<Student> studentList = new ObservableArrayList<>();
    private final MutableLiveData<Student> studentLiveData = new MutableLiveData<>();
    Handler handler = new Handler(Looper.getMainLooper());

    public MvvmViewModel(@NonNull @NotNull Application application) {
        super(application);
        Log.i("zyc", "view model被初始化");
    }

    public void getAllStudents() {
        Log.i("zyc", "view model 调用dataSource读取数据");
        List<Student> students = dataSource.getAllStudents();
        studentsLiveData.setValue(students);
        handler.postDelayed(() -> students.get(0).setName("我曹."), 3000);
    }

    public void getOneStudent() {
        Log.i("zyc", "随机抽取一名煞笔.");
        Student student = dataSource.getRandomStudent();
        studentLiveData.setValue(student);
        handler.postDelayed(() -> changeStudentName(), 3000);
    }

    public void changeStudentName() {
        studentLiveData.getValue().setName("我被改名啦！");
    }

    public MutableLiveData<List<Student>> getStudentsLiveData() {
        return studentsLiveData;
    }

    public MutableLiveData<Student> getStudentLiveData() {
        return studentLiveData;
    }
}
