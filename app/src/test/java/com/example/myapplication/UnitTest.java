package com.example.myapplication;

import android.annotation.SuppressLint;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.bean.testjson.ApplianceBean;
import com.example.myapplication.bean.testjson.ApplianceShowBean;
import com.example.myapplication.slidingup.SlideUpView;
import com.google.android.material.shape.CornerFamily;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yuechou.zhang
 * @since 2021/7/16
 */
public class UnitTest {

    //@Deprecated
    @SuppressLint("hello")
    @CornerFamily
    public class PrivateClass<T> extends SuperClass<Integer> implements IInterface<String> {
        T t;

        private String privateValue;
        public String publicValue;
        @SlideUpView.STATUS
        protected String protectedValue;
        private final String privateFinalValue = "final string.";

        public PrivateClass(String privateValue) {
            this.privateValue = privateValue;
        }

        private void privateMethod(String name, int age) {

        }

        public void publicMethod(String name, int age) {

        }

        @Override
        public void interfaceMethod(String str) {

        }

        class InnerClass {

            public InnerClass(String innerClzValue) {
                this.innerClzValue = innerClzValue;
            }

            private String innerClzValue;

            void methodInInnerClass() {

            }
        }
    }

    public interface IInterface<IT> {
        void interfaceMethod(IT str);
    }

    public class SuperClass<SC> {
        SC t;
    }

    @Test
    public void showClazz() throws ClassNotFoundException {
        //Class<?> clazz = Class.forName("com.example.myapplication.UnitTest#PrivateClass");
        Class<?> clazz = PrivateClass.class;
        PrivateClass heloo = new PrivateClass<String>("heloo");
        clazz = heloo.getClass();
        System.out.println(Arrays.toString(clazz.getDeclaredAnnotations()));
        System.out.println(clazz.getCanonicalName());
        System.out.println(new Integer[]{}.getClass().getComponentType());
        System.out.println(Arrays.toString(clazz.getGenericInterfaces()));
        System.out.println(Arrays.toString(clazz.getConstructors()));
        System.out.println(clazz.getEnclosingClass());
        System.out.println("泛型");
        System.out.println(clazz.getGenericSuperclass());
        for (Type type : ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()) {
            System.out.println("==================getActualTypeArgument start=================");
            System.out.println(((Class) type));
            System.out.println(type.getClass());
            System.out.println("==================getActualTypeArgument end=================");
        }
        System.out.println(((ParameterizedType) clazz.getGenericSuperclass()).getOwnerType());
        System.out.println(((ParameterizedType) clazz.getGenericSuperclass()).getRawType());
        System.out.println(Arrays.toString(clazz.getTypeParameters()));
        for (TypeVariable<? extends Class<?>> typeParameter : clazz.getTypeParameters()) {
            System.out.println(typeParameter.getGenericDeclaration());
            System.out.println(typeParameter.getBounds());
            System.out.println(typeParameter.getTypeName());
            System.out.println(typeParameter.getName());
        }
        System.out.println(clazz.getTypeName());
        System.out.println(clazz.getEnclosingConstructor());
        System.out.println(clazz.getProtectionDomain().getCodeSource().getLocation());
    }


}
