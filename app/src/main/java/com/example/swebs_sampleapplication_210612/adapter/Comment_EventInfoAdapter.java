package com.example.swebs_sampleapplication_210612.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.CommentModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.EventModel;
import com.example.swebs_sampleapplication_210612.adapter.Listener.CommentClickListener;
import com.example.swebs_sampleapplication_210612.databinding.ItemCommentBinding;

import org.jetbrains.annotations.NotNull;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Comment_EventInfoAdapter extends RecyclerView.Adapter<Comment_EventInfoAdapter.CommentViewHolder> {
    Context context;
    private SPmanager sPmanager;
    private ItemCommentBinding binding;
    private List<CommentModel> commentModels;
    private CommentClickListener listener;

    public Comment_EventInfoAdapter(Context context, List<CommentModel> commentModels, CommentClickListener listener){
        sPmanager = new SPmanager(context);
        this.context = context;
        this.commentModels = commentModels;
        this.listener = listener;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = ItemCommentBinding.inflate(LayoutInflater.from(context),parent,false);
        return new CommentViewHolder(binding, listener, commentModels);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull CommentViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CommentModel commentModel = commentModels.get(position);

        // 댓글 본문
        holder.binding.textViewCommentContent.setText(htmlToString(commentModel.getContent()));

        // 댓글 등록 일자
        if (commentModel.getLastupdate() == null || commentModel.getLastupdate().equals(""))
            holder.binding.textViewCommentDate.setText(fullDateToShortDate(commentModel.getRegdate()));
        else
            holder.binding.textViewCommentDate.setText(fullDateToShortDate(commentModel.getLastupdate()) + " (수정됨)");

        // 댓글 닉네임
        holder.binding.textViewUserName.setText(commentModel.getNickname());
        // 대댓글
        holder.binding.textViewRecommentCount.setText(commentModel.getRecomment_count()+" 개의 답글");


        // 답글달기 클릭시
        holder.binding.textViewRecommentCount.setOnClickListener(v ->
                listener.reCommentClicked(position,commentModel));

        // 뷰전체 클릭시
        holder.binding.layoutComment.setOnClickListener(v -> {
            listener.reCommentClicked(position,commentModel);
        });

        // 삭제 클릭시
        holder.binding.deleteComment.setOnClickListener(v -> {
            listener.removeClicked(position);
        });
        // 수정 클릭시
        holder.binding.modifyComment.setOnClickListener(v -> {
            listener.modifyClicked(position);
        });
        // 신고 클릭시
        holder.binding.reportComment.setOnClickListener(v -> {
            listener.reportClicked(position);
        });

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

    public CommentModel getItem(int position) {
        return this.commentModels.get(position);
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder{
        ItemCommentBinding binding;
        CommentClickListener listener;
        List<CommentModel> list;

        public CommentViewHolder(ItemCommentBinding binding, CommentClickListener listener, List<CommentModel> list) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
            this.list = list;

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

    public void addItem(CommentModel model, int position){
        commentModels.add(position, model);
        notifyItemInserted(position);
    }

    public void removeItem(int position){
        commentModels.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,commentModels.size());
    }

    private String fullDateToShortDate(String s) {
        Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse("20210913185326", new ParsePosition(0));
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}
