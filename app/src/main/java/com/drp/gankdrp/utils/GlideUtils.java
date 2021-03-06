package com.drp.gankdrp.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.drp.gankdrp.App;
import com.drp.gankdrp.R;

/**
 * @author KG on 2017/8/2.
 */
public class GlideUtils {

    private RequestOptions mOptions;

    //在装载该内部类时才会去创建单例对象
    private static class Singleton {
        private static GlideUtils ourInstance = new GlideUtils();
    }

    public static GlideUtils newInstance() {
        return Singleton.ourInstance;
    }

    public GlideUtils() {
        mOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.icon_white_back)
                .error(R.drawable.icon_white_back)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    // 加载网络图片
    public void loadNetImage(String url, ImageView iv) {
        Glide.with(App.application)
                .load(url)
                .apply(mOptions)
                .transition(new DrawableTransitionOptions().crossFade(500))
                .into(iv);
    }

    // 加载自适应高度的ImageView
    public void loadAutoHeightNetImage(String url, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.icon_white_back)
                .error(R.drawable.icon_white_back)
                .priority(Priority.HIGH)
                .override(DensityUtil.getScreenWidth(App.application), Target.SIZE_ORIGINAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(App.application)
                .load(url)
                .apply(options)
                .transition(new DrawableTransitionOptions().crossFade(500))
                .into(iv);
    }

    public void loadBlackDefaultImage(String url, ImageView iv) {
        RequestOptions mOptions = new RequestOptions()
                .fitCenter()
                .placeholder(R.color.md_black)
                .error(R.color.md_black)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(App.application)
                .load(url)
                .apply(mOptions)
                .into(iv);
    }

}
