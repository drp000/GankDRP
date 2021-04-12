package com.drp.gankdrp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.drp.gankdrp.R;
import com.drp.gankdrp.base.BaseViewBindingActivity;
import com.drp.gankdrp.databinding.ActivityAboutBinding;
import com.drp.gankdrp.model.multitype.GankItem;
import com.drp.gankdrp.model.net.VersionEntity;
import com.drp.gankdrp.network.ApiService;
import com.drp.gankdrp.utils.AppUtil;
import com.drp.gankdrp.utils.Constants;
import com.drp.gankdrp.utils.TextUtil;
import com.drp.gankdrp.utils.ToastUtil;
import com.drp.gankdrp.utils.update.UpdateHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author durui
 * @date 2021/3/9
 * @description
 */
public class AboutActivity extends BaseViewBindingActivity<ActivityAboutBinding> implements View.OnClickListener {


    @Override
    protected void init() {
        viewBinding.ivBack.setOnClickListener(v -> finish());
        viewBinding.tvVersion.setText(getResources().getString(R.string.version, AppUtil.getVersionName()));
        AppUtil.singleClick(viewBinding.llOriginal, o -> {
            openGankIO();
        });
        AppUtil.singleClick(viewBinding.llVersion, o -> {
            checkNewVersion();
        });
        viewBinding.tvEmail.setOnClickListener(this);
        viewBinding.tvJianshu.setOnClickListener(this);
        viewBinding.tvWeibo.setOnClickListener(this);
    }

    @Override
    protected Toolbar getToolBar() {
        return viewBinding.toolbar;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_email) {
            copyToClipBoard("kuanggang_android@163.com", "邮箱已复制");
        } else if (id == R.id.tv_jianshu) {
            copyToClipBoard("小筐子", "简书已复制");
        } else if (id == R.id.tv_weibo) {
            copyToClipBoard("小筐子hhh", "微博已复制");
        }
    }

    private void copyToClipBoard(String copyText, String toastText) {
        TextUtil.copyToClipBoard(copyText);
        ToastUtil.show(this, toastText);
    }

    public void openGankIO() {
        GankItem gankItem = new GankItem();
        gankItem.type = "干货集中营";
        gankItem.url = "http://gank.io/";

        Intent intent = new Intent(this, BrowserActivity.class);
        intent.putExtra(Constants.URL_KEY, gankItem);
        startActivity(intent);
    }

    @SuppressLint("CheckResult")
    public void checkNewVersion() {
        ApiService.getInstance().getApi().getNowVersion()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(entity -> entity != null)
                .filter(entity -> !TextUtils.isEmpty(entity.version))
                .filter(versionEntity -> {
                    if (Integer.parseInt(versionEntity.version) <= AppUtil.getVersionCode()) {
                        Toast.makeText(this, "已是最新版本", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    return true;
                })
                .subscribe(new Consumer<VersionEntity>() {
                               @Override
                               public void accept(VersionEntity versionEntity) throws Exception {
                                   UpdateHelper updateHelper = new UpdateHelper(AboutActivity.this);
                                   updateHelper.dealWithVersion(versionEntity);
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(AboutActivity.this, "已是最新版本", Toast.LENGTH_SHORT).show();
                            }
                        });
    }
}
