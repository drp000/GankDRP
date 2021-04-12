package com.drp.gankdrp.ui.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.drp.gankdrp.R;
import com.drp.gankdrp.base.BaseViewBindingActivity;
import com.drp.gankdrp.databinding.ActivityMainBinding;
import com.drp.gankdrp.ui.adapter.GankPagerAdapter;
import com.drp.gankdrp.ui.fragment.CategoryFragment;
import com.drp.gankdrp.ui.fragment.ContentFragment;
import com.drp.gankdrp.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseViewBindingActivity<ActivityMainBinding> implements View.OnClickListener {


    private GankPagerAdapter gankPagerAdapter;

    @Override
    protected void init() {
        setMenuSelected(true);
        initTabLayoutViewPager();
        initListener();
    }

    @Override
    protected Toolbar getToolBar() {
        return viewBinding.toolbar;
    }

    private void setMenuSelected(boolean isCategory) {
        viewBinding.ivCategory.setSelected(isCategory);
        viewBinding.ivContent.setSelected(!isCategory);
    }

    private void initTabLayoutViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(CategoryFragment.getInstance());
        fragmentList.add(ContentFragment.getInstance("all"));
        gankPagerAdapter = new GankPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewBinding.viewPager.setAdapter(gankPagerAdapter);
    }

    private void initListener() {
        viewBinding.ivCategory.setOnClickListener(this);
        viewBinding.ivContent.setOnClickListener(this);
        viewBinding.viewPager.addOnPageChangeListener(new GankPagerChangeListener());
        viewBinding.toolbar.setOnMenuItemClickListener(new OnGankMenuItemClickListener());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_category) {
            viewBinding.viewPager.setCurrentItem(0);
            setMenuSelected(true);
        } else {
            viewBinding.viewPager.setCurrentItem(1);
            setMenuSelected(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }

    public void showContent(String category) {
        gankPagerAdapter.updateContent(category);
        gankPagerAdapter.notifyDataSetChanged();
        viewBinding.viewPager.setCurrentItem(1);
    }

    private class GankPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setMenuSelected(position == 0);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class OnGankMenuItemClickListener implements Toolbar.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.action_about) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.action_more) {
                Toast.makeText(MainActivity.this, getString(R.string.expect_toast), Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    }

    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (viewBinding.viewPager.getCurrentItem() == 1) {
                viewBinding.viewPager.setCurrentItem(0);
                setMenuSelected(true);
                return true;
            }
            if (System.currentTimeMillis() - firstTime > 2000) {
                ToastUtil.show(MainActivity.this, getString(R.string.clickmore_exit));
                firstTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}