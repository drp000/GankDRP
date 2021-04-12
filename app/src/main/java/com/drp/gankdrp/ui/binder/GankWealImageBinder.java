package com.drp.gankdrp.ui.binder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.drp.gankdrp.App;
import com.drp.gankdrp.databinding.ItemGankWealimageBinding;
import com.drp.gankdrp.model.multitype.GankWealImage;
import com.drp.gankdrp.ui.activity.PhotoViewsActivity;
import com.drp.gankdrp.utils.Constants;
import com.drp.gankdrp.utils.GlideUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

/**
 * @author KG on 2017/7/27.
 */

public class GankWealImageBinder extends ItemViewBinder<GankWealImage, GankWealImageBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        ItemGankWealimageBinding binding = ItemGankWealimageBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GankWealImage item) {
        Glide.with(App.application);
        GlideUtils.newInstance().loadAutoHeightNetImage(item.imageUrl, holder.viewBinding.iv);

        holder.viewBinding.iv.setOnClickListener(v -> {
            List<Object> entities = (List<Object>) getAdapter().getItems();
            List<GankWealImage> items = new ArrayList<>();
            for (Object object : entities) {
                if (object instanceof GankWealImage) {
                    items.add((GankWealImage) object);
                }
            }

            if (items.size() <= 0) return;
            List<String> imgs = new ArrayList<>();
            for (GankWealImage entity : items) {
                imgs.add(entity.imageUrl);
            }

            Context context = holder.viewBinding.iv.getContext();
            Intent intent = new Intent(context, PhotoViewsActivity.class);
            intent.putExtra(Constants.IMAGE_URL_LIST_KEY, (Serializable) imgs);
            intent.putExtra(Constants.IMAGE, item.imageUrl);
            context.startActivity(intent);
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ItemGankWealimageBinding viewBinding;

        ViewHolder(ItemGankWealimageBinding binding) {
            super(binding.getRoot());
            this.viewBinding = binding;
        }
    }
}
