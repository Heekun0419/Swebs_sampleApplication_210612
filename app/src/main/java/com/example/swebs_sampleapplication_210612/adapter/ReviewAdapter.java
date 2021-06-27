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
import com.example.swebs_sampleapplication_210612.databinding.ItemReviewBinding;

import org.jetbrains.annotations.NotNull;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>{

    String ImageUrl = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https:%2F%2Fblog.kakaocdn.net%2Fdn%2F7SwzA%2FbtqETXLt8U3%2F86KH5y4ZkEoRIGMHE07wa1%2Fimg.jpg";
    Context context;

    private ItemReviewBinding binding;

    public ReviewAdapter (Context context){
        this.context = context;
    }


    @NonNull
    @NotNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = ItemReviewBinding.inflate(LayoutInflater.from(context),parent,false);

        return new ReviewViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReviewViewHolder holder, int position) {
        GlideImage(holder.view);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder{
        ImageView view;
        public ReviewViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.imageView_review_profile);
        }
    }

    private void GlideImage(ImageView view){
        Glide.with(context).load(ImageUrl).into(view);
    }
}
