package com.example.swebs_sampleapplication_210612.adapter.TablayoutAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ItemMoreSurveyBinding;

import org.jetbrains.annotations.NotNull;

public class SurveyMoreAdapter extends RecyclerView.Adapter<SurveyMoreAdapter.SurveyMoreHolder>{
    private ItemMoreSurveyBinding binding;
    Context context;
    String ImageUrl = "https://cdn.pixabay.com/photo/2020/04/02/05/19/beauty-4993472_1280.jpg";

    public SurveyMoreAdapter(Context context){
        this.context = context;
    }

    @NotNull
    @Override
    public SurveyMoreHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        binding = ItemMoreSurveyBinding.inflate(LayoutInflater.from(context),parent,false);
        return new SurveyMoreHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SurveyMoreHolder holder, int position) {
        GlideImage(holder.view);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class SurveyMoreHolder extends RecyclerView.ViewHolder{
        ImageView view;
        public SurveyMoreHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.imageView_product_survey_profile);
        }
    }
    private void GlideImage(ImageView view){
        Glide.with(context).load(ImageUrl).into(view);
    }
}

