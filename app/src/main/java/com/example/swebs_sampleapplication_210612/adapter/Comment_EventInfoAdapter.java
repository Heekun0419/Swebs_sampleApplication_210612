package com.example.swebs_sampleapplication_210612.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.CommentModel;
import com.example.swebs_sampleapplication_210612.databinding.ItemCommentBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Comment_EventInfoAdapter extends RecyclerView.Adapter<Comment_EventInfoAdapter.CommentViewHolder> {
    Context context;
    private SPmanager sPmanager;
    private ItemCommentBinding binding;
    private ArrayList<CommentModel> commentModels;

    public Comment_EventInfoAdapter(Context context, ArrayList<CommentModel> commentModels){
        sPmanager = new SPmanager(context);
        this.context = context;
        this.commentModels = commentModels;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = ItemCommentBinding.inflate(LayoutInflater.from(context),parent,false);
        return new CommentViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull CommentViewHolder holder, int position) {
        CommentModel commentModel = commentModels.get(position);

        // 댓글 본문
        holder.binding.textViewCommentContent.setText(htmlToString(commentModel.getContent()));
        // 댓글 등록 일자
        if (commentModel.getRegdate().equals(commentModel.getLastupadate()))
            holder.binding.textViewCommentDate.setText(commentModel.getRegdate());
        else
            holder.binding.textViewCommentDate.setText(commentModel.getRegdate() + "(수정됨)");
        // 댓글 닉네임
        holder.binding.textViewUserName.setText(commentModel.getNickname());
        // 대댓글
        holder.binding.textViewRecommentCount.setText(commentModel.getRecomment_count()+" 개의 답글");

        if (commentModel.getMember_srl().equals(sPmanager.getUserSrl())) {
            holder.binding.deleteComment.setVisibility(View.VISIBLE);
            holder.binding.modifyComment.setVisibility(View.VISIBLE);
            holder.binding.reportComment.setVisibility(View.GONE);
        }
        GlideImage(holder.binding.imageViewCommentProfile, commentModel.getProfile_srl());
    }

    @Override
    public int getItemCount() {
        return commentModels.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        ItemCommentBinding binding;
        public CommentViewHolder(ItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void GlideImage(ImageView view,String srl){
        Glide.with(context).load(getImageViewUrl(srl,"300")).placeholder(R.drawable.ic_profile_basic).circleCrop().into(view);
    }

    private String getImageViewUrl(String fileSrl, String Width) {
        String result = context.getString(R.string.IMAGE_VIEW_URL) + "?inputFileSrl=" + fileSrl;
        if (Width != null) result += "&inputImageWidth=" + Width;
        return result;
    }

    private String htmlToString(String html) {
        return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
    }
}
