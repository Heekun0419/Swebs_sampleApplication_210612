package com.example.swebs_sampleapplication_210612.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ItemReviewPhotoListBinding;

import java.util.ArrayList;
import java.util.List;

public class ReviewPhotoListAdapter extends RecyclerView.Adapter<ReviewPhotoListAdapter.reviewPhotoViewHolder> {

    private Context context;
    private List<String> list = new ArrayList<>();
    private ItemReviewPhotoListBinding binding;

    public ReviewPhotoListAdapter(Context context, List<String> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public reviewPhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemReviewPhotoListBinding.inflate(LayoutInflater.from(context),parent,false);
        return new reviewPhotoViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull reviewPhotoViewHolder holder, int position) {
        GlideImage(holder.imageView,getImageViewUrl(list.get(position),"300"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class reviewPhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public reviewPhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_review_photo_list);
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
