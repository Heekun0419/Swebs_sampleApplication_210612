package com.example.swebs_sampleapplication_210612.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ChattingItem;
import com.example.swebs_sampleapplication_210612.databinding.ItemCommentBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Comment_EventInfoAdapter extends RecyclerView.Adapter<Comment_EventInfoAdapter.CommentViewHolder> {
    Context context;
    private ItemCommentBinding binding;
    private ArrayList<ChattingItem> chattingItems;

    public Comment_EventInfoAdapter(Context context, ArrayList<ChattingItem> chattingItems){
        this.context = context;
        this.chattingItems = chattingItems;
    }

    @Override
    public CommentViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        binding = ItemCommentBinding.inflate(LayoutInflater.from(context),parent,false);
        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CommentViewHolder holder, int position) {
        ChattingItem chattingItem = chattingItems.get(position);
        holder.textView_Comment.setText(chattingItem.getContent());
        holder.textView_Date.setText(chattingItem.getRegDate());
        GlideImage(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return chattingItems.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView textView_Comment, textView_Date, TextView_name;
        ImageView imageView;

        public CommentViewHolder(ItemCommentBinding binding) {
            super(binding.getRoot());
            textView_Comment = binding.textViewCommentContent;
            textView_Date = binding.textViewCommentDate;
            imageView = binding.imageViewEventInfoCommentProfile;
        }
    }

    private void GlideImage(ImageView view){
        Glide.with(context).load(R.drawable.userprofile).override(300,300).circleCrop().into(view);
    }
}
