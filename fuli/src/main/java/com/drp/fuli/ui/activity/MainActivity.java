package com.drp.fuli.ui.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.drp.fuli.R;
import com.drp.fuli.adapter.GirlAdapter;
import com.drp.fuli.base.BaseViewBindingActivity;
import com.drp.fuli.databinding.ActivityMainBinding;
import com.drp.fuli.model.Image;
import com.drp.fuli.net.ApiService;
import com.drp.fuli.util.ConfigUtils;
import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent;
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseViewBindingActivity<ActivityMainBinding> {

    private List<Image> mImages = new ArrayList<>();
    private boolean refreshing;
    private int page = 1;
    private GirlAdapter girlAdapter;
    private Picasso picasso;

    @Override
    protected void init() {
        picasso = new Picasso.Builder(MainActivity.this).build();
        setupRecyclerView();
        viewBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                refreshing = true;
                mImages.clear();
                fetchGirlData();
            }
        });
        page = 1;
        refreshing = true;
        viewBinding.refreshLayout.setRefreshing(true);
        fetchGirlData();
    }

    @SuppressLint("CheckResult")
    private void setupRecyclerView() {
        girlAdapter = new GirlAdapter(this, mImages);
        girlAdapter.setOnItemClickListener((view, image) -> {
            Intent intent = new Intent(MainActivity.this, PictureActivity.class);
            intent.putExtra("url", image.getUrl());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ActivityOptionsCompat girl = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, view, "girl");
            ActivityCompat.startActivity(MainActivity.this, intent, girl.toBundle());
        });
        int spanCount = 2;
        if (ConfigUtils.isOrientationPortrait(this)) {
            spanCount = 2;
        } else {
            spanCount = 3;
        }
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        viewBinding.recyclerView.setLayoutManager(staggeredGridLayoutManager);
        viewBinding.recyclerView.setAdapter(girlAdapter);

        /*RxRecyclerView.scrollEvents(viewBinding.recyclerView)
                .compose(bindToLifecycle())
                .subscribe(new Consumer<RecyclerViewScrollEvent>() {
                    @Override
                    public void accept(RecyclerViewScrollEvent recyclerViewScrollEvent) throws Exception {

                    }
                });*/
        viewBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isBottom;
                if (ConfigUtils.isOrientationPortrait(MainActivity.this)) {
                    isBottom = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1] >= mImages.size() - 4;
                } else {
                    isBottom = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(new int[3])[2] >= mImages.size() - 4;
                }
                if (isBottom && !viewBinding.refreshLayout.isRefreshing()) {
                    if (refreshing) {
                        page = 0;
                        refreshing = false;
                    }
                    page++;
                    viewBinding.refreshLayout.setRefreshing(true);
                    fetchGirlData();
                }
            }
        });
    }

    @SuppressLint("CheckResult")
    private void fetchGirlData() {
        ApiService.getInstance().getGirlApi().fetchPrettyGirl(page)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .filter(girlDataResult -> !girlDataResult.isError() && girlDataResult.response().isSuccessful())
                .map(girlDataResult -> girlDataResult.response().body())
                .subscribe(girlData -> Observable.fromIterable(girlData.results)
                                .map(prettyGirl -> {
                                    try {
                                        Bitmap bitmap = picasso.load(prettyGirl.url).get();
                                        Image image = new Image(bitmap.getWidth(), bitmap.getHeight(), bitmap);
                                        image.setUrl(prettyGirl.url);
                                        return image;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    return "";
                                }).filter(object -> object instanceof Image)
                                .map(o -> (Image) o)
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnComplete(() -> {
                                    viewBinding.refreshLayout.setRefreshing(false);
                                })
                                .subscribe(image -> {
                                    mImages.add(image);
                                    girlAdapter.notifyDataSetChanged();
                                }, throwable -> {
                                    throwable.printStackTrace();
                                    viewBinding.refreshLayout.setRefreshing(false);
                                    Snackbar.make(viewBinding.refreshLayout, throwable.getMessage(), Snackbar.LENGTH_LONG).show();
                                }),
                        throwable -> {
                            throwable.printStackTrace();
                            viewBinding.refreshLayout.setRefreshing(false);
                            Snackbar.make(viewBinding.refreshLayout, throwable.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                );
    }

    @Override
    protected Toolbar getToolBar() {
        return viewBinding.toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_settings) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}