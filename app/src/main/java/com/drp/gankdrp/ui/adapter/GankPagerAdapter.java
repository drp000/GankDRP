package com.drp.gankdrp.ui.adapter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.drp.gankdrp.ui.fragment.CategoryFragment;
import com.drp.gankdrp.ui.fragment.ContentFragment;

import java.util.ArrayList;
import java.util.List;

public class GankPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;

    public GankPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragmentList = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void updateContent(String category) {
        ContentFragment fragment = (ContentFragment) fragmentList.get(1);
        fragment.updateArguments(category);
    }

    /*@Override
    public Object instantiateItem(ViewGroup container, int position) {
        ContentFragment fragment = (ContentFragment) super.instantiateItem(container, position);
        fragment.updateArguments(mTitle.get(position).getCode());
        return fragment;
    }*/
}
