package com.drp.gankdrp.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.drp.gankdrp.utils.GlideUtils;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;


/**
 * @author KG on 2017/8/1.
 */

public class PhotoViewPagerAdapter extends PagerAdapter {
    private Activity mActivity;
    private List<String> mList;

    public PhotoViewPagerAdapter(Activity activity, List<String> list) {
        this.mActivity = activity;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                mActivity.finish();
            }
        });

        GlideUtils.newInstance().loadBlackDefaultImage(mList.get(position), photoView);
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
