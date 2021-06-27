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
import com.example.swebs_sampleapplication_210612.databinding.ItemProductEventBinding;

import org.jetbrains.annotations.NotNull;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    Context context;
    private ItemProductEventBinding binding;
    String ImageUrl = "https://images.otwojob.com/product/l/r/P/lrP1mUhYpnR780M.jpg";

    public EventAdapter (Context context){
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = ItemProductEventBinding.inflate(LayoutInflater.from(context),parent,false);
        return new EventViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull EventViewHolder holder, int position) {
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
        return 7;
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        ImageView view;
        public EventViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.imageView_product_event_profile);
        }
    }
    private void GlideImage(ImageView view){
        Glide.with(context).load(ImageUrl).into(view);
    }
}
