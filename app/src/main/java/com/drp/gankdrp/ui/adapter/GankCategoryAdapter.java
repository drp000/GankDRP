package com.drp.gankdrp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.drp.gankdrp.R;
import com.drp.gankdrp.databinding.ItemCategoryBinding;

public class GankCategoryAdapter extends RecyclerView.Adapter<GankCategoryAdapter.CategoryViewHolder> {

    private Context mContext;
    private ItemClickListener itemClickListener;
    private String[] categorys = new String[]{"全部", "Android", "iOS", "前端", "休息视频",
            "福利", "拓展资源", "瞎推荐", "App"};

    private int[] drawableIds = new int[]{R.mipmap.icon_all, R.mipmap.icon_android, R.mipmap.icon_ios,
            R.mipmap.icon_web, R.mipmap.icon_video, R.mipmap.icon_weal, R.mipmap.icon_expand,
            R.mipmap.icon_recommend, R.mipmap.icon_app};

    public GankCategoryAdapter(Context context, ItemClickListener listener) {
        this.mContext = context;
        this.itemClickListener = listener;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), null, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.viewBinding.tv.setText(categorys[position]);
        holder.viewBinding.iv.setImageResource(drawableIds[position]);
        holder.viewBinding.llRoot.setOnClickListener(v -> {
            String category = categorys[position].equals("全部") ? "all" : categorys[position];
//            mActivity.showContent();
            itemClickListener.onClick(category);
        });
    }

    @Override
    public int getItemCount() {
        return categorys.length;
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {

        private ItemCategoryBinding viewBinding;

        CategoryViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            viewBinding = binding;
        }
    }

    public interface ItemClickListener {
        void onClick(String category);
    }
}
