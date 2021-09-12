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
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.adapter.Listener.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.databinding.ItemSurveyButtonBinding;

import java.util.List;

public class SurveyButtonAdapter extends RecyclerView.Adapter<SurveyButtonAdapter.surveyButtonViewHolder>{

    private Context context;
    private List<SurveyOptionModel> list;
    private OnItemClickListener listener;
    private ItemSurveyButtonBinding binding;
    public boolean isClicked = false;

    public SurveyButtonAdapter(Context context, List<SurveyOptionModel> list, OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public surveyButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemSurveyButtonBinding.inflate(LayoutInflater.from(context), parent, false);
        return new surveyButtonViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull surveyButtonViewHolder holder, int position) {
        SurveyOptionModel model = list.get(position);
        holder.binding.textViewSurveyInfo.setText(model.getOption_title());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class surveyButtonViewHolder extends RecyclerView.ViewHolder {
        ItemSurveyButtonBinding binding;
        OnItemClickListener listener;
        public surveyButtonViewHolder(ItemSurveyButtonBinding binding, OnItemClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;

            binding.btnSurvey.setOnClickListener(v ->
                    listener.onItemSelected(binding.btnSurvey,getAdapterPosition(), ""));
        }
    }

}