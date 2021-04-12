package com.drp.gankdrp.ui.activity;

import androidx.appcompat.widget.Toolbar;

import com.drp.gankdrp.R;
import com.drp.gankdrp.base.BaseViewBindingActivity;
import com.drp.gankdrp.databinding.ActivityBrowserBinding;
import com.drp.gankdrp.model.multitype.GankItem;
import com.drp.gankdrp.model.type.CategoryEnum;
import com.drp.gankdrp.utils.Constants;
import com.drp.gankdrp.utils.ToastUtil;

/**
 * @author durui
 * @date 2021/3/9
 * @description
 */
public class BrowserActivity extends BaseViewBindingActivity<ActivityBrowserBinding> {
    @Override
    protected void init() {
        viewBinding.ivClose.setOnClickListener(v -> finish());
        loadUrl();
    }

    @Override
    protected Toolbar getToolBar() {
        return viewBinding.toolbar;
    }

    private void loadUrl() {
        GankItem entity = (GankItem) getIntent().getSerializableExtra(Constants.URL_KEY);
        if (entity == null) {
            ToastUtil.show(this, R.string.error_date);
            return;
        }
        viewBinding.tvTitle.setText(entity.type);
        viewBinding.ivCategory.setImageResource(CategoryEnum.to(entity.type) == null ? R.mipmap.ic_launcher_round : CategoryEnum.to(entity.type).drawableId);
        viewBinding.webViewLayout.loadUrl(entity.url);
    }
}
