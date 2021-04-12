package com.drp.test;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.drp.test.ui.main.MainFragment;
import com.drp.test.ui.main.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getName().setValue("更新后的数据:" + i++);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (i < 10) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    viewModel.getName().postValue("更新后的数据:" + i++);
                }
            }
        }).start();
    }
}