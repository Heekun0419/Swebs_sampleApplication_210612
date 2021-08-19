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
import com.example.swebs_sampleapplication_210612.databinding.ItemMyReviewBinding;

import org.jetbrains.annotations.NotNull;

public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.MyReviewViewHolder>{

    private Context context;
    private ItemMyReviewBinding binding;
    private String Url = "https://cdn.pixabay.com/photo/2020/04/02/05/19/beauty-4993472_1280.jpg";

    public MyReviewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemMyReviewBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MyReviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyReviewViewHolder holder, int position) {
        GlideImage(holder.viewProfile,"");
        GlideImage(holder.viewTop,Url);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class MyReviewViewHolder extends RecyclerView.ViewHolder{
        ImageView viewTop, viewProfile;
        ItemMyReviewBinding binding;
        public MyReviewViewHolder(ItemMyReviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            viewTop = binding.imageViewReviewProfile;
            viewProfile = binding.imageViewMyReviewUserProfile;
        }
    }

    private void GlideImage(ImageView view, String url){
        if(url.equals("")){
            Glide.with(context).load(R.drawable.userprofile).circleCrop().into(view);
        }else{
            Glide.with(context).load(url).into(view);
        }

    }
}
