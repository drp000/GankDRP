package com.drp.gankdrp.ui.binder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.drp.gankdrp.databinding.ItemGankTitleBinding;
import com.drp.gankdrp.model.multitype.GankTitle;
import com.drp.gankdrp.model.type.CategoryEnum;
import com.drp.gankdrp.utils.DateUtil;

import java.text.ParseException;
import java.util.Date;

import me.drakeet.multitype.ItemViewBinder;

/**
 * @author KG on 2017/6/14.
 */

public class GankTitleBinder extends ItemViewBinder<GankTitle, GankTitleBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        ItemGankTitleBinding binding = ItemGankTitleBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GankTitle item) {
        CategoryEnum to = CategoryEnum.to(item.type);
        holder.viewBinding.iv.setImageResource(to.drawableId);
        holder.viewBinding.tvCategory.setText(item.type);

        String smallDate = item.publishedAt.split("T")[0];
        String bigDate = DateUtil.convertDate2String(new Date());
        try {
            holder.viewBinding.tvTime.setVisibility(View.VISIBLE);
            int diff = DateUtil.getStrDateDiff(smallDate, bigDate);
            holder.viewBinding.tvTime.setText(diff > 0 ? diff + " 天前" : "今日更新");
        } catch (ParseException e) {
            holder.viewBinding.tvTime.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ItemGankTitleBinding viewBinding;

        ViewHolder(ItemGankTitleBinding binding) {
            super(binding.getRoot());
            viewBinding = binding;
        }
    }
}
