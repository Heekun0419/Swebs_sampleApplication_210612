package com.example.swebs_sampleapplication_210612.adapter;

import android.content.Context;
import android.graphics.Color;
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
    private boolean isClicked;

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

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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

            binding.getRoot().setOnClickListener(v ->
                    listener.onItemSelected(binding.btnSurvey,getAdapterPosition(), ""));
        }
    }

    public void setButtonChange(int position){
        isClicked = true;
    }
}
