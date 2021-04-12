package com.drp.fuli.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.widget.Toolbar;

import com.drp.fuli.base.BaseViewBindingActivity;
import com.drp.fuli.databinding.ActivityAboutBinding;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

/**
 * @author durui
 * @date 2021/3/12
 * @description
 */
public class AboutActivity extends BaseViewBindingActivity<ActivityAboutBinding> {
    @SuppressLint("CheckResult")
    @Override
    protected void init() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewBinding.toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        RxView.clicks(viewBinding.cardAddress)
                .throttleFirst(1, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(o -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("https://github.com/Assassinss/pretty-girl"));
                    startActivity(intent);
                });
        RxView.clicks(viewBinding.cardThank)
                .throttleFirst(1, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(o -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("http://gank.io"));
                    startActivity(intent);
                });
    }

    @Override
    protected Toolbar getToolBar() {
        return viewBinding.toolbar;
    }
}
