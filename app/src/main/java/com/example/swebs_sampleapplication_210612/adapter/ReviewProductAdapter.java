package com.example.swebs_sampleapplication_210612.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.LikeApplyModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;
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
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewProductAdapter extends RecyclerView.Adapter<ReviewProductAdapter.ReviewProductViewHolder> {

    private ItemReviewProductBinding binding;
    private final Context context;
    private List<ReviewModel> reviewList = new ArrayList<>();
    private ReviewClickListener listener;
    private final SPmanager sPmanager;
    private final SwebsAPI retroAPI;
    private boolean isLike = false;


    public ReviewProductAdapter(Context context, List<ReviewModel> reviewList, ReviewClickListener listener) {
        this.context = context;
        this.reviewList = reviewList;
        this.listener = listener;
        sPmanager = new SPmanager(context);
        this.retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);

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
        // 좋아요.
        if (model.isCan_like())
            holder.binding.imageViewLike.setImageResource(R.drawable.ic_heart_simple_shape_silhouette);
        else
            holder.binding.imageViewLike.setImageResource(R.drawable.ic_heart_simple_shape_filled);

        // 리뷰 내용
        holder.binding.textViewCommentContent.setText(model.getContent());

        // 날짜
        Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(model.getRegdate(), new ParsePosition(0));
        holder.binding.textViewMyReviewDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));

        // 별점
        holder.binding.ratingBarReview.setRating(Float.parseFloat(model.getRate()));
        holder.binding.textViewRatingNumMyReview.setText(Float.toString(binding.ratingBarReview.getRating()));

        holder.binding.textViewMyReviewTitle.setText(model.getReview_title());

        holder.binding.textViewMyReviewUserName.setText(model.getNickname());
        holder.binding.likeNum.setText(model.getLike_count());

        if (model.getMember_srl().equals(sPmanager.getUserSrl())) {
            holder.binding.reportReview.setVisibility(View.GONE);
            holder.binding.modifyReview.setVisibility(View.VISIBLE);
            holder.binding.deleteReview.setVisibility(View.VISIBLE);
        }

        holder.binding.imageViewLike.setOnClickListener(v -> {
            HashMap<String, RequestBody> formData = new HashMap<>();
            formData.put("inputTargetSrl", RequestBody.create(model.getReview_srl(), MediaType.parse("text/plane")));
            formData.put("inputUserSrl", RequestBody.create(sPmanager.getUserSrl(), MediaType.parse("text/plane")));
            formData.put("inputTargetTable", RequestBody.create("review", MediaType.parse("text/plane")));
            Call<LikeApplyModel> call = retroAPI.pushLike(formData);
            call.enqueue(new Callback<LikeApplyModel>() {
                @Override
                public void onResponse(Call<LikeApplyModel> call, Response<LikeApplyModel> response) {
                    if (response.isSuccessful()
                            && response.body() != null) {
                        if (response.body().getState().equals("Insert")) {
                            holder.binding.likeNum.setText(
                                    Integer.toString(Integer.parseInt(holder.binding.likeNum.getText().toString()) + 1)
                            );

                            holder.binding.imageViewLike.setImageResource(R.drawable.ic_heart_simple_shape_filled);
                        } else {
                            holder.binding.likeNum.setText(
                                    Integer.toString(Integer.parseInt(holder.binding.likeNum.getText().toString()) - 1)
                            );

                            holder.binding.imageViewLike.setImageResource(R.drawable.ic_heart_simple_shape_silhouette);
                        }
                    }
                }

                @Override
                public void onFailure(Call<LikeApplyModel> call, Throwable t) {

                }
            });
        });


        holder.binding.reportReview.setOnClickListener(v -> {
            listener.reportClicked(position);
        });

        holder.binding.deleteReview.setOnClickListener(v -> {
            listener.removeClicked(position);
        });

        holder.binding.layoutReview.setOnClickListener(v -> {
            listener.CommentClicked(position, model);
        });

        holder.binding.textViewCommentOfReview.setText("0개의 댓글");

        GlideImage(holder.binding.imageViewMyReviewUserProfile, getImageViewUrl(model.getProfile_srl(), "100"));


        ReviewPhotoListAdapter photoListAdapter = new ReviewPhotoListAdapter(context, model.getFile_srl());
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.binding.recyclerViewMyReviewPhoto.setLayoutManager(manager);
        holder.binding.recyclerViewMyReviewPhoto.setAdapter(photoListAdapter);

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
