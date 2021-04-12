package com.drp.fuli.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.drp.fuli.databinding.ItemGirlAdapterBinding;
import com.drp.fuli.model.Image;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author durui
 * @date 2021/3/11
 * @description
 */
public class GirlAdapter extends RecyclerView.Adapter<GirlAdapter.GirlViewHolder> {
    private List<Image> images;
    private Context context;
    private Glide glide;
    private OnItemClickListener onItemClickListener;

    public GirlAdapter(Context context, List<Image> images) {
        this.context = context;
        this.images = images;
        glide = Glide.get(context);
        glide.setMemoryCategory(MemoryCategory.HIGH);
    }

    @NonNull
    @Override
    public GirlAdapter.GirlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemGirlAdapterBinding binding = ItemGirlAdapterBinding.inflate(inflater, parent, false);
        return new GirlViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GirlAdapter.GirlViewHolder holder, int position) {
        Image image = images.get(position);
        Disposable subscribe = RxView.clicks(holder.viewBinding.getRoot())
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (onItemClickListener != null)
                            onItemClickListener.onItemClick(holder.viewBinding.getRoot(), images.get(position));
                    }
                });
        holder.viewBinding.ratioImageView.setOriginalWidth(image.getWidth());
        holder.viewBinding.ratioImageView.setOriginalHeight(image.getHeight());
//        Glide.with(context).load(image.getUrl()).into(holder.viewBinding.ratioImageView);
        holder.viewBinding.ratioImageView.setImageBitmap(image.getBitmap());
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    static class GirlViewHolder extends RecyclerView.ViewHolder {

        private ItemGirlAdapterBinding viewBinding;

        public GirlViewHolder(@NonNull ItemGirlAdapterBinding binding) {
            super(binding.getRoot());
            viewBinding = binding;
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Image image);
    }
}
