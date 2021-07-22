package com.example.myapplication.basemvvm;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author yuechou.zhang
 * @since 2021/7/16
 */
public abstract class BaseActivity<VM extends BaseViewModel, DB extends ViewDataBinding> extends AppCompatActivity {

    protected VM viewModel;

    protected DB dataBinding;

    protected View rootView;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = getViewModel();
        viewModel.onAttachToActivity();
        getLifecycle().addObserver(viewModel);

        dataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        dataBinding.setLifecycleOwner(this);
        rootView = dataBinding.getRoot();
        initData();
        initView();
    }

    /**
     * 通常是用viewModel fetch data
     */
    protected void initData() {
    }

    protected abstract void initView();

    protected abstract int getLayoutId();



    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private VM getViewModel() {
        Class<VM> vmClass = getVmClass();
        if (vmClass == null) {
            throw new IllegalArgumentException("vm created fail.");
        }
        return ((VM) new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(vmClass));
    }

    private Class<VM> getVmClass() {
        Type type = getClass().getGenericSuperclass();
        if (type == null) {
            return null;
        }
        if (ParameterizedType.class.isAssignableFrom(type.getClass())) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            return ((Class) types[0]);
        }
        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
