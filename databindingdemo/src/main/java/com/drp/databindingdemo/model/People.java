package com.drp.databindingdemo.model;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.drp.databindingdemo.BR;

/**
 * @author durui
 * @date 2021/3/23
 * @description
 */
public class People extends BaseObservable {
    private String name;
    private int age;
    private boolean fired;

    public ObservableField<String> observableField;

    public People(String name, int age) {
        this.name = name;
        this.age = age;
        observableField = new ObservableField<>();
        observableField.set("你真调皮");
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }

    public boolean getFired() {
        return fired;
    }

    public void setFired(boolean fired) {
        this.fired = fired;
        notifyChange();
    }
}