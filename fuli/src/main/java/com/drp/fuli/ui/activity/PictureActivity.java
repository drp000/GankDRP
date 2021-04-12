package com.drp.fuli.ui.activity;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.drp.fuli.base.BaseViewBindingActivity;
import com.drp.fuli.databinding.ActivityPictureBinding;
import com.drp.fuli.ui.view.PullBackLayout;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.orhanobut.logger.Logger;

public class PictureActivity extends BaseViewBindingActivity<ActivityPictureBinding> implements PullBackLayout.PullCallBack {

    private PhotoViewAttacher mViewAttacher;
    private boolean systemUiIsShow;
    private ColorDrawable background;

    @Override
    protected void init() {
        ViewCompat.setTransitionName(viewBinding.ivPhoto, "girl");
        String url = getIntent().getStringExtra("url");
        Glide.with(this).load(url).into(viewBinding.ivPhoto);

        background = new ColorDrawable(Color.BLACK);
        viewBinding.pullBackLayout.setBackground(background);
        mViewAttacher = new PhotoViewAttacher(viewBinding.ivPhoto);
        viewBinding.pullBackLayout.setPullCallBack(this);
        mViewAttacher.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                if (systemUiIsShow) {
                    hideSystemUi();
                    systemUiIsShow = false;
                } else {
                    showSystemUi();
                    systemUiIsShow = true;
                }
            }
        });
    }

    private void showSystemUi() {
        getWindow().getDecorView().setSystemUiVisibility(FLAG_SHOW_SYSTEM_UI);
    }

    private void hideSystemUi() {
        getWindow().getDecorView().setSystemUiVisibility(FLAG_HIDE_SYSTEM_UI);
    }

    @Override
    protected Toolbar getToolBar() {
        return null;
    }

    private static final int FLAG_HIDE_SYSTEM_UI = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE;

    private static final int FLAG_SHOW_SYSTEM_UI = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

    @Override
    public void onPullStart() {
        showSystemUi();
    }

    @Override
    public void onPull(float progress) {
        showSystemUi();
        int alpha = (int) (0xff * (1 - progress));
        background.setAlpha(alpha);
        viewBinding.pullBackLayout.setBackground(background);
    }

    @Override
    public void onPullCompleted() {
        showSystemUi();
        onBackPressed();
    }
}