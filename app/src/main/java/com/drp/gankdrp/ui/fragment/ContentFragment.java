package com.drp.gankdrp.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.drp.gankdrp.R;
import com.drp.gankdrp.base.BaseViewBindingFragment;
import com.drp.gankdrp.databinding.FragmentContentBinding;
import com.drp.gankdrp.model.multitype.GankItem;
import com.drp.gankdrp.model.multitype.GankTimeDivide;
import com.drp.gankdrp.model.multitype.GankTitle;
import com.drp.gankdrp.model.multitype.GankWealImage;
import com.drp.gankdrp.model.type.CategoryEnum;
import com.drp.gankdrp.model.type.PageSizeEnum;
import com.drp.gankdrp.network.ApiService;
import com.drp.gankdrp.ui.binder.GankContentBinder;
import com.drp.gankdrp.ui.binder.GankTimeDivideBinder;
import com.drp.gankdrp.ui.binder.GankTitleBinder;
import com.drp.gankdrp.ui.binder.GankWealImageBinder;
import com.drp.gankdrp.utils.DateUtil;
import com.drp.gankdrp.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author durui
 * @date 2021/3/9
 * @description
 */
public class ContentFragment extends BaseViewBindingFragment<FragmentContentBinding> {
    private MultiTypeAdapter mContentAdapter;
    private String mCategory = "all";
    private int mPage = 1;
    private int mSize = PageSizeEnum.TWENTY.size;
    private List<GankItem> mGankDatas = new ArrayList<>();

    public static ContentFragment getInstance(String category) {
        ContentFragment contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", category);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        initListener();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadFirstPage();
        }
    }

    private void init() {
        viewBinding.rvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        mContentAdapter = new MultiTypeAdapter();
        mContentAdapter.register(GankTitle.class, new GankTitleBinder());
        mContentAdapter.register(GankItem.class, new GankContentBinder());
        mContentAdapter.register(GankTimeDivide.class, new GankTimeDivideBinder());
        mContentAdapter.register(GankWealImage.class, new GankWealImageBinder());
        viewBinding.rvContent.setAdapter(mContentAdapter);
        mCategory = getArguments().getString("category");
    }

    private void initListener() {
        viewBinding.refreshlayout.setOnRefreshListener(this::loadFirstPage);
        viewBinding.refreshlayout.setOnLoadListener(this::loadNextPage);
    }

    private void loadNextPage() {
        viewBinding.refreshlayout.setLoading(true);
        mPage++;
        showGankDataByCategory();
    }

    private void loadFirstPage() {
        viewBinding.refreshlayout.setRefreshing(true);
        mPage = 1;
        showGankDataByCategory();
    }

    @SuppressLint("CheckResult")
    private void showGankDataByCategory() {
        ApiService.getInstance().getApi().getGankListByCategory(mCategory, mPage, mSize)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(gankCategory -> gankCategory != null && gankCategory.results != null)
                .subscribe(gankCategory -> {
                    if (gankCategory.error) {
                        ToastUtil.show(getActivity(), R.string.net_error);
                        return;
                    }
                    if (gankCategory.results == null || gankCategory.results.size() <= 0) {
                        ToastUtil.show(getActivity(), R.string.no_data);
                        return;
                    }
                    if (mPage == 1) {
                        mGankDatas = gankCategory.results;
                    } else {
                        mGankDatas.addAll(gankCategory.results);
                    }
                    mContentAdapter.setItems(handleResults(mGankDatas));
                    mContentAdapter.notifyDataSetChanged();
                    onRefreshLoadOk();
                    if (mPage == 1)
                        viewBinding.rvContent.scrollToPosition(0);
                }, throwable -> {
                    onRefreshLoadOk();
                });
    }

    public void onRefreshLoadOk() {
        viewBinding.refreshlayout.setRefreshing(false);
        viewBinding.refreshlayout.setLoading(false);
    }

    /**
     * 将服务器返回数据进行处理
     * 加入Title，Divide
     */
    private List<Object> handleResults(List<GankItem> initResults) {
        List<Object> results = new ArrayList<>();
        if (initResults == null || initResults.size() <= 0)
            return results;

        if (mCategory.equals(CategoryEnum.WEAL.category)) {
            for (int i = 0; i < initResults.size(); i++) {
                results.add(new GankWealImage(initResults.get(i).url));
            }
            return results;
        }

        for (int i = 0; i < initResults.size(); i++) {
            String nowDate;
            String lastDate;
            GankItem item = initResults.get(i);
            if (i > 0) {
                GankItem lastItem = initResults.get(i - 1);
                nowDate = item.publishedAt.split("T")[0];
                lastDate = lastItem.publishedAt.split("T")[0];

                if (!nowDate.equals(lastDate))
                    addDivideAndTitle(results, nowDate, item, true);
                else if (!lastItem.type.equals(item.type))
                    addDivideAndTitle(results, null, item, true);
            } else {
                nowDate = DateUtil.convertDate2String(new Date());
                lastDate = item.publishedAt.split("T")[0];

                if (!nowDate.equals(lastDate))
                    addDivideAndTitle(results, lastDate, item, true);
                else
                    addDivideAndTitle(results, null, item, false);
            }
            results.add(item.type.equals(CategoryEnum.WEAL.category) ? new GankWealImage(item.url) : item);
        }
        return results;
    }

    private void addDivideAndTitle(List<Object> results, String date, GankItem item, boolean isAddDivide) {
        if (isAddDivide) {
            if (!TextUtils.isEmpty(date)) {
                date = DateUtil.convertString2String(date, "M月dd日");
                date = date.replaceAll("", " ");
                date = date.substring(1, date.length() - 1);
            }
            results.add(new GankTimeDivide(date));
        }
        results.add(new GankTitle(item.createdAt, item.publishedAt, item.type, item.url));
    }

    public void updateArguments(String category) {
        mCategory = category;
        mContentAdapter.setItems(new ArrayList<>());
        mContentAdapter.notifyDataSetChanged();
        loadFirstPage();
    }
}
