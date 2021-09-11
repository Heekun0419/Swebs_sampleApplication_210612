package com.example.swebs_sampleapplication_210612.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ItemReviewViepagerBinding;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReviewViewPagerAdapter extends RecyclerView.Adapter<ReviewViewPagerAdapter.reviewPagerViewHolder> {
    private ItemReviewViepagerBinding binding;
    private Context context;
    private List<String> list;

    public ReviewViewPagerAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public reviewPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemReviewViepagerBinding.inflate(LayoutInflater.from(context),parent, false);
        return new reviewPagerViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull reviewPagerViewHolder holder, int position) {
        String file_srl = list.get(position);
        GlideImage(holder.view, getImageViewUrl(file_srl, "1000"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class reviewPagerViewHolder extends RecyclerView.ViewHolder {
        ImageView view;
        public reviewPagerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.imageview_review_pager);
        }
    }

    private String getImageViewUrl(String fileSrl, String Width) {
        String result = context.getString(R.string.IMAGE_VIEW_URL) + "?inputFileSrl=" + fileSrl;
        if (Width != null)
            result += "&inputImageWidth=" + Width;
        return result;
    }

    private void GlideImage(ImageView view, String url){
        Glide.with(context).load(url).centerCrop().into(view);
    }

}
