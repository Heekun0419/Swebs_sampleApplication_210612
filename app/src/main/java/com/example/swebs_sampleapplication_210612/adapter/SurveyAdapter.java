package com.example.swebs_sampleapplication_210612.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.SurveyDetailModel;
import com.example.swebs_sampleapplication_210612.adapter.Listener.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.databinding.ItemSurveyBinding;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.surveyViewHolder> {

    private ItemSurveyBinding binding;
    OnItemClickListener listener;
    Context context;
    List<SurveyDetailModel> list;

    public SurveyAdapter(Context context, List<SurveyDetailModel> list, OnItemClickListener listener){
        this.context = context;
        this.listener = listener;
        this.list = list;
    }

    @NonNull
    @Override
    public surveyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        binding = ItemSurveyBinding.inflate(LayoutInflater.from(context),parent,false);
        return new surveyViewHolder(binding,listener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull surveyViewHolder holder, int position) {
        SurveyDetailModel model = list.get(position);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) binding.getRoot().getLayoutParams();
        if(position == 0 || position == 9) {
            if (position == 0) {
                layoutParams.leftMargin = 50;
                layoutParams.rightMargin = 0;
            } else {
                layoutParams.rightMargin = 50;
                layoutParams.leftMargin = 40;
            }
        } else {
            layoutParams.leftMargin = 50;
            layoutParams.rightMargin = 0;
        }

        // 시작날짜 끝 날짜
        holder.binding.textViewStartDate.setText(model.getStart_date().substring(0,10));
        holder.binding.textViewEndDate.setText("~" + model.getEnd_date().substring(0,10));

        // 포인트 설정
        holder.binding.imageView6.setText("+"+model.getPoint()+"P");

        // 카테고리...
        holder.binding.textViewCategoryTitle.setText(model.getCategory_title());

        // 대표 이미지
        GlideImage(holder.binding.imageViewProductSurveyProfile, getImageViewUrl(model.getFile_srl(), "800"));

        // 참여 인원
        holder.binding.textviewJoinCount.setText(model.getJoin_count() + "명 참여중");

        // 서베이 제목
        holder.binding.textViewSurveyTitle.setText(model.getSurvey_title());

    }

    public SurveyDetailModel getItem(int position){
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class surveyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        OnItemClickListener listener;
        ItemSurveyBinding binding;
        public surveyViewHolder(ItemSurveyBinding binding, OnItemClickListener listener) {
            super(binding.getRoot());
            this.listener = listener;
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemSelected(binding.getRoot(),getAdapterPosition(),"survey");
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


