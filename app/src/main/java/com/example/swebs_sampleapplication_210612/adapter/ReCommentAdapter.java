package com.example.swebs_sampleapplication_210612.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.CommentModel;
import com.example.swebs_sampleapplication_210612.adapter.Listener.CommentClickListener;
import com.example.swebs_sampleapplication_210612.databinding.ItemReCommentBinding;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReCommentAdapter extends RecyclerView.Adapter<ReCommentAdapter.ReCommentViewHolder> {
    private Context context;
    private List<CommentModel> list;
    private ItemReCommentBinding binding;
    private SPmanager sPmanager;
    private CommentClickListener listener;

    public ReCommentAdapter(Context context, List<CommentModel> list, CommentClickListener listener) {
        sPmanager = new SPmanager(context);
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemReCommentBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ReCommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReCommentViewHolder holder, int position) {
        CommentModel model = list.get(position);

        // 댓글 본문
        holder.binding.textviewRecommentContent.setText(htmlToString(model.getContent()));

        // 댓글 등록 일자
        if (model.getLastupdate() == null || model.getLastupdate().equals(""))
            holder.binding.textViewRecommentRegDate.setText(fullDateToShortDate(model.getRegdate()));
        else
            holder.binding.textViewRecommentRegDate.setText(fullDateToShortDate(model.getLastupdate()) + " (수정됨)");

        // 댓글 닉네임
        holder.binding.textViewRecommentName.setText(model.getNickname());

        if (model.getMember_srl().equals(sPmanager.getUserSrl())) {
            holder.binding.deleteRecomment.setVisibility(View.VISIBLE);
            holder.binding.modifyRecomment.setVisibility(View.VISIBLE);
        }

        // 삭제 클릭시
        holder.binding.deleteRecomment.setOnClickListener(v -> {
            listener.removeClicked(position);
        });
        // 수정 클릭시
        holder.binding.modifyRecomment.setOnClickListener(v -> {
            listener.modifyClicked(position);
        });

        GlideImage(holder.binding.imageViewRecommentProfile, model.getProfile_srl());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public CommentModel getItem(int position) {
        return this.list.get(position);
    }

    public static class ReCommentViewHolder extends RecyclerView.ViewHolder {
        ItemReCommentBinding binding;
        public ReCommentViewHolder(ItemReCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void GlideImage(ImageView view, String srl){
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

    public void addItem(CommentModel model, int position) {
        list.add(position, model);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,list.size());
    }

    private String fullDateToShortDate(String s) {
        Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse("20210913185326", new ParsePosition(0));
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

}
