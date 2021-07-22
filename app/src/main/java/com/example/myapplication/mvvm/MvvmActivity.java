package com.example.myapplication.mvvm;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

import com.example.myapplication.BR;
import com.example.myapplication.R;
import com.example.myapplication.basemvvm.BaseActivity;
import com.example.myapplication.databinding.ActivityMvvmBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuechou.zhang
 * @since 2021/7/16
 */
public class MvvmActivity extends BaseActivity<MvvmViewModel, ActivityMvvmBinding> {

    @Override
    protected void initView() {
        dataBinding.setHandler(new HandlerClick());
        viewModel.getStudentLiveData().observe(this, student -> {
            Log.i("zyc", "回调:" + student);
            dataBinding.setVariable(BR.student, student);
        });
        viewModel.getStudentsLiveData().observe(this, students -> {
            Log.i("zyc", "获取所有学生回调:" + students);
            List<Map<String, Object>> list = new ArrayList<>();
            for (Student student : students) {
                Map<String, Object> map = new HashMap<>(2);
                map.put("name", student.getName());
                map.put("age", student.getAge());
                list.add(map);
            }
            dataBinding.lvMvvm.setAdapter(new SimpleAdapter(this, list, R.layout.lv_mvvm_item,
                    new String[]{"name", "age"}, new int[]{R.id.tv_name_item_lv, R.id.tv_age_item_lv}));
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvvm;
    }

    public class HandlerClick {
        public void getOneStudentClick(View view) {
            Log.i("zyc", "点击事件回调");
            viewModel.getOneStudent();
            viewModel.getAllStudents();
        }
    }


}
