package com.example.swebs_sampleapplication_210612.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.swebs_sampleapplication_210612.databinding.ItemReviewPhotoAddBinding;

import java.util.ArrayList;

import retrofit2.http.Url;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull WriteReviewViewHolder holder, int position) {
        if (position == 0){
            holder.binding.btnClose.setVisibility(View.GONE);
            if(UrlList.size()<7)
                holder.binding.textViewPictureCount.setText("("+(UrlList.size()-1)+"/5)");
            else{
                holder.binding.textViewPictureCount.setText("(5/5)");
                Toast.makeText(context, "5개까지 등록 가능합니다.", Toast.LENGTH_SHORT).show();
            }

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
        return Math.min(UrlList.size(), 6);
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

