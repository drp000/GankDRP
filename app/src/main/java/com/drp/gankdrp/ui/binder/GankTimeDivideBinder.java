package com.drp.gankdrp.ui.binder;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.drp.gankdrp.databinding.ItemGankDivideBinding;
import com.drp.gankdrp.model.multitype.GankTimeDivide;

import io.reactivex.annotations.NonNull;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author KG on 2017/6/14.
 */

public class GankTimeDivideBinder extends ItemViewBinder<GankTimeDivide, GankTimeDivideBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        ItemGankDivideBinding binding = ItemGankDivideBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GankTimeDivide item) {
        if (TextUtils.isEmpty(item.publishedAt)) {
            holder.viewBinding.tvTime.setVisibility(View.GONE);
            return;
        }
        holder.viewBinding.tvTime.setVisibility(View.VISIBLE);
        holder.viewBinding.tvTime.setText(item.publishedAt);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemGankDivideBinding viewBinding;

        ViewHolder(ItemGankDivideBinding binding) {
            super(binding.getRoot());
            viewBinding = binding;
        }
    }
}
