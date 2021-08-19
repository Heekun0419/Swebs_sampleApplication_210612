package com.example.swebs_sampleapplication_210612.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.swebs_sampleapplication_210612.databinding.ItemReviewPhotoAddBinding;

import java.util.ArrayList;

public class WriteReviewPhotoAdapter extends RecyclerView.Adapter<WriteReviewPhotoAdapter.WriteReviewViewHolder> {

    private ItemReviewPhotoAddBinding binding;
    private Context context;
    private HistoryListClickListener listener;
    private ArrayList<String> UrlList = new ArrayList<>();

    public WriteReviewPhotoAdapter( Context context, ArrayList<String> list , HistoryListClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.UrlList = list;
    }

    @NonNull
    @Override
    public WriteReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemReviewPhotoAddBinding.inflate(LayoutInflater.from(context),parent,false);
        return new WriteReviewViewHolder(binding,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull WriteReviewViewHolder holder, int position) {
        if (position == 0){
            holder.binding.btnClose.setVisibility(View.GONE);
            holder.binding.textViewPictureCount.setText("("+(UrlList.size()-1)+"/5)");

            holder.binding.imageViewReviewProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageButton btn  = binding.btnClose;
                    listener.positiveButtonClicked(btn, holder.getAdapterPosition(), "");
                }
            });
        }else{
            ImageView view = binding.imageViewReviewProfile;
            holder.binding.textViewPictureCount.setVisibility(View.GONE);
            holder.binding.imageViewPlus.setVisibility(View.GONE);

            holder.binding.btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageButton btn = binding.btnClose;
                    listener.negativeButtonClicked(btn,holder.getAdapterPosition(),"");
                }
            });
            GlideImage(view,UrlList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return UrlList.size();
    }

    public static class WriteReviewViewHolder extends RecyclerView.ViewHolder {
        HistoryListClickListener listener;
        ItemReviewPhotoAddBinding binding;

        public WriteReviewViewHolder(ItemReviewPhotoAddBinding binding, HistoryListClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;

        }

    }
    private void GlideImage(ImageView view, String url){
        Glide.with(context).load(url).into(view);
    }
}

