package com.example.swebs_sampleapplication_210612.IntroPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ItemIntroSliderBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class IntroAdapter extends RecyclerView.Adapter<IntroAdapter.IntroViewHolder> {
    private ItemIntroSliderBinding binding;
    private final Context context;
    private final ArrayList<Integer> drawable;

    public IntroAdapter(Context context, ArrayList<Integer> drawable) {
        this.context = context;
        this.drawable = drawable;
    }

    @NonNull
    @Override
    public IntroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemIntroSliderBinding.inflate(LayoutInflater.from(context),parent,false);
        return new IntroViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull IntroViewHolder holder, int position) {
        int drawableId =  drawable.get(position);
        holder.view.setBackgroundResource(drawableId);
    }

    @Override
    public int getItemCount() {
        return drawable.size();
    }

    public static class IntroViewHolder extends RecyclerView.ViewHolder {
        ImageView view;
        public IntroViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.ImageView_Intro);
        }
    }

}
