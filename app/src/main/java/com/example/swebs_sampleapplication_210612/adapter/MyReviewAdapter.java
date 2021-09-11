package com.example.swebs_sampleapplication_210612.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ReviewModel;
import com.example.swebs_sampleapplication_210612.databinding.ItemMyReviewBinding;

import org.jetbrains.annotations.NotNull;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.MyReviewViewHolder>{

    private Context context;
    private ItemMyReviewBinding binding;
    private SPmanager sPmanager;
    private List<ReviewModel> list;

    public MyReviewAdapter(Context context, List<ReviewModel> list) {
        this.context = context;
        this.list = list;
        sPmanager = new SPmanager(context);
    }

    @NonNull
    @Override
    public MyReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemMyReviewBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MyReviewViewHolder(binding);
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull MyReviewViewHolder holder, int position) {
        ReviewModel model = list.get(position);

     /*   // 좋아요.
        if (model.isCan_like())
            holder.binding.imageViewLike.setImageResource(R.drawable.ic_heart_simple_shape_silhouette);
        else
            holder.binding.imageViewLike.setImageResource(R.drawable.ic_heart_simple_shape_filled);*/

        holder.binding.imageViewLike.setImageResource(R.drawable.ic_heart_simple_shape_silhouette);
        holder.binding.likeNum.setText(model.getLike_count());
        // 리뷰 내용
        holder.binding.textViewCommentContent.setText(model.getContent());

        // 날짜
        Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(model.getRegdate(), new ParsePosition(0));
        //holder.binding.textViewMyReviewDate.setText(new SimpleDateFormat("yyyy.MM.dd").format(date));
        holder.binding.textViewCommentOfReview.setText(new SimpleDateFormat("yyyy.MM.dd").format(date));
        // 별점
        holder.binding.ratingBarReview.setRating(Float.parseFloat(model.getRate()));
        holder.binding.textViewRatingNumMyReview.setText(Float.toString(binding.ratingBarReview.getRating()));

        // 리뷰 제목
        holder.binding.textViewMyReviewTitle.setText(model.getReview_title());

        holder.binding.textViewMyReviewUserName.setText(model.getNickname());

        // 회사명
        holder.binding.textViewReviewBrandName.setText(model.getCorp_name());
        // 제품 이름
        holder.binding.textViewReviewTitle.setText(model.getProd_title());


        GlideImage_circle(holder.binding.imageViewMyReviewUserProfile, getImageViewUrl(model.getProfile_srl(), "100"));
        GlideImage(holder.binding.imageViewReviewProfile, getImageViewUrl(model.getProd_file_srl(),"200"));

        ReviewPhotoListAdapter photoListAdapter = new ReviewPhotoListAdapter(context, model.getFile_srl());
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.binding.recyclerViewMyReviewPhoto.setLayoutManager(manager);
        holder.binding.recyclerViewMyReviewPhoto.setAdapter(photoListAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyReviewViewHolder extends RecyclerView.ViewHolder{
        ItemMyReviewBinding binding;
        public MyReviewViewHolder(ItemMyReviewBinding binding) {
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

    private void GlideImage_circle(ImageView view, String url){
        Glide.with(context).load(url).placeholder(R.drawable.ic_profile_basic).circleCrop().into(view);
    }
    private void GlideImage(ImageView view, String url){
        Glide.with(context).load(url).centerCrop().into(view);
    }

}
