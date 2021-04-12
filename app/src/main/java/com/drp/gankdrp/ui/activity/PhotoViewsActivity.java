package com.drp.gankdrp.ui.activity;

import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.drp.gankdrp.base.BaseViewBindingActivity;
import com.drp.gankdrp.databinding.ActivityPhotoviewsBinding;
import com.drp.gankdrp.ui.adapter.PhotoViewPagerAdapter;
import com.drp.gankdrp.utils.AppUtil;
import com.drp.gankdrp.utils.Constants;
import com.drp.gankdrp.utils.ImageUtils;

import java.util.List;

/**
 * @author durui
 * @date 2021/3/9
 * @description
 */
public class PhotoViewsActivity extends BaseViewBindingActivity<ActivityPhotoviewsBinding> {

    private List<String> mGirls;
    private int curPosition = 0;
    private ImageUtils imageHelper;

    @Override
    protected void init() {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initData();
        initView();
        initListener();
    }

    @Override
    protected Toolbar getToolBar() {
        return null;
    }

    private void initData() {
        mGirls = (List<String>) getIntent().getSerializableExtra(Constants.IMAGE_URL_LIST_KEY);
        if (AppUtil.isEmpty(mGirls)) {
            throw new RuntimeException("The girls of gank is empty!");
        }
        curPosition = mGirls.indexOf(getIntent().getStringExtra(Constants.IMAGE));
    }

    private void initView() {
        viewBinding.tvCurPosition.setText(String.valueOf(curPosition + 1));
        viewBinding.tvSumCount.setText(String.valueOf(mGirls.size()));
        viewBinding.viewPager.setAdapter(new PhotoViewPagerAdapter(this, mGirls));
    }

    private void initListener() {
        viewBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                curPosition = position;
                viewBinding.tvCurPosition.setText(String.valueOf(position + 1));
                viewBinding.viewPager.setTag(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewBinding.viewPager.setCurrentItem(curPosition);

        AppUtil.singleClick( viewBinding.ivSave, o -> {
            imageHelper = new ImageUtils(PhotoViewsActivity.this);
            imageHelper.saveImage(mGirls.get(curPosition));
        });
    }

    @Override
    protected void onDestroy() {
        if (imageHelper != null) {
            imageHelper.unInit();
        }
        super.onDestroy();
    }
}
