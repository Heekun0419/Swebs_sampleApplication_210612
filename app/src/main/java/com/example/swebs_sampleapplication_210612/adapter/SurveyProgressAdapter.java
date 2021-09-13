package com.example.swebs_sampleapplication_210612.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.SurveyOptionModel;
import com.example.swebs_sampleapplication_210612.adapter.Listener.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.databinding.ItemProgressSurveyBinding;

import java.util.ArrayList;
import java.util.List;

public class SurveyProgressAdapter extends RecyclerView.Adapter<SurveyProgressAdapter.SurveyProgressViewHolder> {
    private OnItemClickListener listener;
    private Context context;
    private ItemProgressSurveyBinding binding;
    private List<SurveyOptionModel> list;
    private List<Boolean> selectedList;
    private List<Integer> integers = new ArrayList<>();

    public SurveyProgressAdapter(OnItemClickListener listener, List<SurveyOptionModel> list, Context context) {
        this.listener = listener;
        this.context = context;
        this.list = list;
        this.selectedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++)
            selectedList.add(false);
        for (int i = 0; i < list.size(); i++)
            integers.add(0);
    }

    @NonNull
    @Override
    public SurveyProgressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemProgressSurveyBinding.inflate(LayoutInflater.from(context), parent, false);
        return new SurveyProgressViewHolder(binding, listener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SurveyProgressViewHolder holder, int position) {
        SurveyOptionModel model = list.get(position);
        setVote(position, model.getVote_count());
        int percentage = integers.get(position);
        // 버튼 이름
        holder.binding.textViewSurveyProgressTitle.setText(model.getOption_title());
        // 서버에서 받은 퍼센트
        holder.binding.textViewPercentageOfSurvey1.setText(percentage + "%");
        holder.binding.btnSurvey1.setProgress(percentage);
        // 참여 인원
        holder.binding.textViewVoteCount.setText("(" + model.getVote_count() + ")");

        if (selectedList.get(position)) {
            holder.binding.btnSurvey1.setSelected(true);
            holder.binding.textViewPercentageOfSurvey1.setText(""+percentage+ "%");
            holder.binding.textViewVoteCount.setText("(" + (Integer.parseInt(model.getVote_count()) + 1) + ")");
            holder.binding.btnSurvey1.setProgress(percentage);
            holder.binding.textViewPercentageOfSurvey1.setTextColor(Color.parseColor("#000000"));
            holder.binding.textViewVoteCount.setTextColor(Color.parseColor("#000000"));
            holder.binding.textViewSurveyProgressTitle.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.binding.btnSurvey1.setSelected(false);
            holder.binding.textViewSurveyProgressTitle.setTextColor(Color.parseColor("#000000"));
            holder.binding.textViewPercentageOfSurvey1.setTextColor(Color.parseColor("#000000"));
            holder.binding.textViewVoteCount.setTextColor(Color.parseColor("#000000"));
            holder.binding.textViewVoteCount.setText("(" + (Integer.parseInt(model.getVote_count())) + ")");
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setSelectedList(int position, Boolean select) {
        selectedList.set(position, select);
    }

    public void setVote(int position, String vote){
        Log.d("votev", "position : "+ position +" vote : "+vote);
        int sum = 0;
        for(int i=0; i<list.size(); i++) {
            if (position == i) {
                sum = sum + Integer.parseInt(vote);
            } else {
               sum = sum + Integer.parseInt(list.get(i).getVote_count());
            }
        }

        for (int j =0; j<list.size(); j++){
            if (position == j){
                if (sum != 0){
                    integers.set(j,(Integer.parseInt(vote)/sum)*100);
                }
            } else {
                if( sum != 0){
                    integers.set(j,(Integer.parseInt(list.get(j).getVote_count())/sum)*100);
                }
            }
        }
    }

    public void clearSelectList(){
        selectedList.clear();
        integers.clear();
        for (int i = 0; i < list.size(); i++)
            selectedList.add(false);
    }

    public static class SurveyProgressViewHolder extends RecyclerView.ViewHolder {
        OnItemClickListener listener;
        ItemProgressSurveyBinding binding;

        public SurveyProgressViewHolder(ItemProgressSurveyBinding binding, OnItemClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }
    }
}
