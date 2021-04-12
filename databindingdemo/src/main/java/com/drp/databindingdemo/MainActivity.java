package com.drp.databindingdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.drp.databindingdemo.databinding.ActivityMainBinding;
import com.drp.databindingdemo.model.People;

public class MainActivity extends AppCompatActivity {

    private People people = new People("张三", 25);
    private ActivityMainBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        dataBinding.setPeople(people);
        dataBinding.setPresenter(new Presenter());
        dataBinding.viewStub.getViewStub().inflate();
    }

    public class Presenter {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            people.setName(s.toString());
//            dataBinding.setPeople(people);
        }

        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "点击了", Toast.LENGTH_SHORT).show();
            people.setFired(true);
            people.observableField.set("我也很调皮");
        }

        public void onClick(People people) {
            Toast.makeText(MainActivity.this, "点击了:" + people.getAge(), Toast.LENGTH_SHORT).show();
            people.setFired(false);
        }
    }
}