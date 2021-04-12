package com.drp.gankdrp.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.drp.gankdrp.base.BaseViewBindingFragment;
import com.drp.gankdrp.databinding.FragmentCategoryBinding;
import com.drp.gankdrp.ui.activity.MainActivity;
import com.drp.gankdrp.ui.adapter.GankCategoryAdapter;

/**
 * @author durui
 * @date 2021/3/9
 * @description
 */
public class CategoryFragment extends BaseViewBindingFragment<FragmentCategoryBinding> {

    public static CategoryFragment getInstance() {
        return new CategoryFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        checkNewVersion();
    }

    private void init() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        viewBinding.rvCategory.setLayoutManager(gridLayoutManager);
        GankCategoryAdapter mCategoryAdapter = new GankCategoryAdapter(getActivity(), category -> {
            MainActivity activity = (MainActivity) getActivity();
            activity.showContent(category);
        });
        viewBinding.rvCategory.setAdapter(mCategoryAdapter);
    }

    private void checkNewVersion() {

    }
}
