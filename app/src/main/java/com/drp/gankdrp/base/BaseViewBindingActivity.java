package com.drp.gankdrp.base;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * @author durui
 * @date 2021/3/9
 * @description
 */
public abstract class BaseViewBindingActivity<T extends ViewBinding> extends RxAppCompatActivity {

    protected T viewBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        Class cls = (Class) type.getActualTypeArguments()[0];
        try {
            Method inflate = cls.getDeclaredMethod("inflate", LayoutInflater.class);
            viewBinding = (T) inflate.invoke(null, getLayoutInflater());
            setContentView(viewBinding.getRoot());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        Toolbar toolBar = getToolBar();
        if (toolBar != null) {
            toolBar.setTitle("");
            setSupportActionBar(toolBar);
        }
        init();
    }

    protected abstract void init();

    protected abstract Toolbar getToolBar();
}
