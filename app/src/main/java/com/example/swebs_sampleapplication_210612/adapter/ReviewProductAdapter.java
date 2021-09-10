package com.example.swebs_sampleapplication_210612.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ReviewModel;
import com.example.swebs_sampleapplication_210612.databinding.ItemCommentBinding;
import com.example.swebs_sampleapplication_210612.databinding.ItemReviewProductBinding;

import java.util.ArrayList;
import java.util.List;

public class ReviewProductAdapter extends RecyclerView.Adapter<ReviewProductAdapter.ReviewProductViewHolder> {

    private ItemReviewProductBinding binding;
    private Context context;
    private List<ReviewModel> reviewList = new ArrayList<>();

    public ReviewProductAdapter(Context context, List<ReviewModel> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemReviewProductBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ReviewProductViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReviewProductViewHolder holder, int position) {
        GlideImage(binding.imageViewMyReviewUserProfile);
        ReviewModel model = reviewList.get(position);

        holder.binding.textViewCommentContent.setText(model.getContent());
        holder.binding.textViewMyReviewDate.setText(model.getRegdate());
        // 별점
        holder.binding.ratingBarReview.setRating(Float.parseFloat(model.getRate()));
        holder.binding.textViewRatingNumMyReview.setText(Float.toString(binding.ratingBarReview.getRating()));

        holder.binding.textViewMyReviewTitle.setText(model.getReview_title());

        holder.binding.textViewMyReviewUserName.setText(model.getNickname());
        holder.binding.likeNum.setText(model.getLike_count());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public static class ReviewProductViewHolder extends RecyclerView.ViewHolder {
        ItemReviewProductBinding binding;
        public ReviewProductViewHolder(ItemReviewProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    private void GlideImage(ImageView view){
        Glide.with(context).load(R.drawable.userprofile).override(300,300).circleCrop().into(view);
    }
}
