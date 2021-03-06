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
import com.example.swebs_sampleapplication_210612.ViewModel.Model.SurveyListDetailModel;
import com.example.swebs_sampleapplication_210612.adapter.Listener.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.databinding.ItemSurveyBinding;

import java.util.List;


public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.surveyViewHolder> {

    private ItemSurveyBinding binding;
    OnItemClickListener listener;
    Context context;
    List<SurveyListDetailModel> list;

    public SurveyAdapter(Context context, List<SurveyListDetailModel> list, OnItemClickListener listener){
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
        SurveyListDetailModel model = list.get(position);
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

        // ???????????? ??? ??????
        holder.binding.textViewStartDate.setText(model.getStart_date().substring(0,10));
        holder.binding.textViewEndDate.setText("~" + model.getEnd_date().substring(0,10));

        // ????????? ??????
        holder.binding.imageView6.setText("+"+model.getPoint()+"P");

        // ????????????...
        holder.binding.textViewCategoryTitle.setText(model.getCategory_title());

        // ?????? ?????????
        GlideImage(holder.binding.imageViewProductSurveyProfile, getImageViewUrl(model.getFile_srl(), "800"));

        // ?????? ??????
        holder.binding.textviewJoinCount.setText(model.getJoin_count() + "??? ?????????");

        // ????????? ??????
        holder.binding.textViewSurveyTitle.setText(model.getSurvey_title());

    }

    public SurveyListDetailModel getItem(int position){
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


