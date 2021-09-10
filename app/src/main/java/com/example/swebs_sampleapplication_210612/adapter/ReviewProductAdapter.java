package com.example.swebs_sampleapplication_210612.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ReviewModel;
import com.example.swebs_sampleapplication_210612.ViewModel.ReViewViewModel;
import com.example.swebs_sampleapplication_210612.adapter.Listener.ReviewClickListener;
import com.example.swebs_sampleapplication_210612.databinding.ItemReviewProductBinding;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewProductAdapter extends RecyclerView.Adapter<ReviewProductAdapter.ReviewProductViewHolder> {

    private ItemReviewProductBinding binding;
    private Context context;
    private List<ReviewModel> reviewList = new ArrayList<>();
    private ReviewClickListener listener;
    private SPmanager sPmanager;
    private boolean isLike = false;


    public ReviewProductAdapter(Context context, List<ReviewModel> reviewList, ReviewClickListener listener) {
        this.context = context;
        this.reviewList = reviewList;
        this.listener = listener;
        sPmanager = new SPmanager(context);

    }

    @NonNull
    @Override
    public ReviewProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemReviewProductBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ReviewProductViewHolder(binding);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(@NonNull ReviewProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ReviewModel model = reviewList.get(position);

        // 리뷰 내용
        holder.binding.textViewCommentContent.setText(model.getContent());
        // 날짜
        Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(model.getRegdate(),new ParsePosition(0));
        holder.binding.textViewMyReviewDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));


        // 별점
        holder.binding.ratingBarReview.setRating(Float.parseFloat(model.getRate()));
        holder.binding.textViewRatingNumMyReview.setText(Float.toString(binding.ratingBarReview.getRating()));

        holder.binding.textViewMyReviewTitle.setText(model.getReview_title());

        holder.binding.textViewMyReviewUserName.setText(model.getNickname());
        holder.binding.likeNum.setText(model.getLike_count());

        if(model.getMember_srl().equals(sPmanager.getUserSrl())) {
            holder.binding.reportReview.setVisibility(View.GONE);
            holder.binding.modifyReview.setVisibility(View.VISIBLE);
            holder.binding.deleteReview.setVisibility(View.VISIBLE);
        }

        holder.binding.imageViewLike.setImageResource(R.drawable.ic_heart_simple_shape_silhouette);
        holder.binding.imageViewLike.setOnClickListener(v -> {
            if(!isLike){
                holder.binding.imageViewLike.setImageResource(R.drawable.ic_heart_simple_shape_filled);
                isLike = true;
            } else {
                holder.binding.imageViewLike.setImageResource(R.drawable.ic_heart_simple_shape_silhouette);
                isLike = false;
            }
            listener.LikeClicked(position,isLike);
        });

        holder.binding.textViewCommentOfReview.setText("0개의 댓글");

        GlideImage(holder.binding.imageViewMyReviewUserProfile, getImageViewUrl(model.getProfile_srl(), "100"));
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

    private String getImageViewUrl(String fileSrl, String Width) {
        String result = context.getString(R.string.IMAGE_VIEW_URL) + "?inputFileSrl=" + fileSrl;
        if (Width != null)
            result += "&inputImageWidth=" + Width;
        return result;
    }

    private void GlideImage(ImageView view, String url){
        Glide.with(context).load(url).placeholder(R.drawable.ic_profile_basic).circleCrop().into(view);
    }

    public void isLiked(boolean like){
        this.isLike = like;
    }
}
