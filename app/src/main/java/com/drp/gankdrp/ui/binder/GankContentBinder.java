package com.drp.gankdrp.ui.binder;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.drp.gankdrp.App;
import com.drp.gankdrp.R;
import com.drp.gankdrp.databinding.ItemGankContentBinding;
import com.drp.gankdrp.model.multitype.GankItem;
import com.drp.gankdrp.ui.activity.BrowserActivity;
import com.drp.gankdrp.utils.Constants;
import com.drp.gankdrp.utils.DensityUtil;
import com.drp.gankdrp.utils.GlideUtils;

import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

/**
 * @author KG on 2017/6/14.
 */

public class GankContentBinder extends ItemViewBinder<GankItem, GankContentBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        ItemGankContentBinding binding = ItemGankContentBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GankItem item) {
        // 设置16:9图片
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.viewBinding.iv.getLayoutParams();
        layoutParams.height = (DensityUtil.getScreenWidth(App.application) - DensityUtil.dip2px(App.application, 30)) / 16 * 9;
        holder.viewBinding.iv.setLayoutParams(layoutParams);
        holder.viewBinding.iv.setVisibility(View.GONE);

        List<String> images = item.images;
        if (images != null && images.size() > 0) {
            GlideUtils.newInstance().loadNetImage(images.get(0), holder.viewBinding.iv);
            holder.viewBinding.iv.setVisibility(View.VISIBLE);
        }

        holder.viewBinding.tvTitle.setText(item.desc);
        String author = !TextUtils.isEmpty(item.who) ? item.who : App.application.getResources().getString(R.string.no_author);
        holder.viewBinding.tvAuthor.setText(App.application.getString(R.string.author, author));

        holder.viewBinding.rlRoot.setOnClickListener(v -> {
            Context context = holder.viewBinding.rlRoot.getContext();
            Intent intent = new Intent(context, BrowserActivity.class);
            intent.putExtra(Constants.URL_KEY, item);
            context.startActivity(intent);
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemGankContentBinding viewBinding;

        ViewHolder(ItemGankContentBinding binding) {
            super(binding.getRoot());
            viewBinding = binding;
        }
    }
}
