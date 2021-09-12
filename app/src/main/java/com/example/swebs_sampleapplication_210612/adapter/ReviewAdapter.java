package com.example.swebs_sampleapplication_210612.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.MainReviewModel;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.adapter.Listener.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.databinding.ItemReviewBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>{

    Context context;
    private List<MainReviewModel> list;
    private ItemReviewBinding binding;
    private OnItemClickListener listener;

    public ReviewAdapter (Context context, List<MainReviewModel> list, OnItemClickListener listener){
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = ItemReviewBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ReviewViewHolder(binding,listener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ReviewViewHolder holder, int position) {
        MainReviewModel model = list.get(position);
        holder.binding.ratingBar.setRating(Float.parseFloat(model.getRate()));
        holder.binding.textViewRatingNum.setText(Float.toString(holder.binding.ratingBar.getRating()));
        holder.binding.textViewReviewTitle.setText(model.getProd_title());
        holder.binding.textViewReviewBrandName.setText(model.getCorp_name());
        holder.binding.textViewReviewDescription.setText(model.getReview_title());

        GlideImage(holder.binding.imageViewReviewProfile, getImageViewUrl(model.getFile_srl(),"300"));
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public MainReviewModel getItem(int position){
        return list.get(position);
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ItemReviewBinding binding;
        OnItemClickListener listener ;
        public ReviewViewHolder(ItemReviewBinding binding, OnItemClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemSelected(binding.getRoot(),getAdapterPosition(),"review");
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
