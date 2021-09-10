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
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.CommentModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ReviewListModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ReviewModel;
import com.example.swebs_sampleapplication_210612.adapter.Listener.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.databinding.ItemReviewBinding;
import com.example.swebs_sampleapplication_210612.databinding.ItemReviewListBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ReviewMoreAdapter extends RecyclerView.Adapter<ReviewMoreAdapter.ReviewViewHolder>{
    private Context context;
    private List<ReviewListModel> list = new ArrayList<>();
    private ItemReviewListBinding binding;
    private OnItemClickListener listener;

    public ReviewMoreAdapter(Context context, List<ReviewListModel> list, OnItemClickListener listener){
        this.context = context;
        this.listener = listener;
        this.list = list;
    }

    @NotNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = ItemReviewListBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ReviewViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ReviewViewHolder holder, int position) {
        ReviewListModel model = list.get(position);
        holder.binding.textViewRank.setText(""+(position+1));
        holder.binding.ratingBarReview.setRating(Float.parseFloat(model.getAvg_rate()));
        holder.binding.textViewRatingNum.setText(Float.toString(binding.ratingBarReview.getRating()));
        holder.binding.textViewReviewBrandName.setText(model.getCorp_name());
        holder.binding.textViewReviewCount.setText("("+model.getReview_count()+")");
        holder.binding.textViewReviewTitle.setText(model.getProd_title());

        GlideImage(holder.binding.imageViewReviewProfile, getImageViewUrl(model.getFile_srl(), "600"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder{
        ItemReviewListBinding binding;
        public ReviewViewHolder(ItemReviewListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void addItem(ReviewListModel model, int position){
        list.add(position, model);
        notifyItemInserted(position);
    }

    public void removeItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,list.size());
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
