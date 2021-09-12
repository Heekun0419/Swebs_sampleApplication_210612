package com.example.swebs_sampleapplication_210612.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventListDetailModel;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.EventModel;
import com.example.swebs_sampleapplication_210612.adapter.Listener.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.databinding.ItemProductEventBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    Context context;
    private ItemProductEventBinding binding;
    OnItemClickListener listener;
    List<EventListDetailModel> list;

    public EventAdapter (Context context, List<EventListDetailModel> list, OnItemClickListener listener){
        this.context = context;
        this.listener = listener;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = ItemProductEventBinding.inflate(LayoutInflater.from(context),parent,false);
        return new EventViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull EventViewHolder holder, int position) {
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
        return list.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView view;
        OnItemClickListener listener;
        ItemProductEventBinding binding;
        public EventViewHolder(ItemProductEventBinding binding, OnItemClickListener listener) {
            super(binding.getRoot());
            this.listener = listener;
            this.binding = binding;
            view = binding.imageViewProductEventProfile;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemSelected(binding.getRoot(),getAdapterPosition(),"event");
        }
    }

}
