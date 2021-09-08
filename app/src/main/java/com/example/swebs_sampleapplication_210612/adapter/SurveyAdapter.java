package com.example.swebs_sampleapplication_210612.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.adapter.Listener.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.databinding.ItemSurveyBinding;


public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.surveyViewHolder> {


    private ItemSurveyBinding binding;
    OnItemClickListener listener;
    String ImageUrl = "https://cdn.pixabay.com/photo/2020/04/02/05/19/beauty-4993472_1280.jpg";
    Context context;


    public SurveyAdapter(Context context, OnItemClickListener listener){
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public surveyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        binding = ItemSurveyBinding.inflate(LayoutInflater.from(context),parent,false);
        return new surveyViewHolder(binding.getRoot(),listener);
    }

    @Override
    public void onBindViewHolder(@NonNull surveyViewHolder holder, int position) {
        GlideImage(holder.view);
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
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class surveyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView view;
        OnItemClickListener listener;
        public surveyViewHolder(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            view = itemView.findViewById(R.id.imageView_product_survey_profile);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemSelected(itemView,getAdapterPosition(),"survey");
        }
    }

    private void GlideImage(ImageView view){
        Glide.with(context).load(ImageUrl).into(view);
    }
}


