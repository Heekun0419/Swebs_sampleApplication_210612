package com.example.swebs_sampleapplication_210612.adapter.TablayoutAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.EventModel;
import com.example.swebs_sampleapplication_210612.adapter.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.databinding.ItemMoreEventBinding;

import java.util.ArrayList;

public class EventMoreAdapter extends RecyclerView.Adapter<EventMoreAdapter.EventMoreViewHolder> {

    private ItemMoreEventBinding binding;
    private Context context;
    private ArrayList<EventModel> model;
    private OnItemClickListener listener;

    public EventMoreAdapter( Context context, ArrayList<EventModel> model, OnItemClickListener listener) {
        this.context = context;
        this.model = model;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventMoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemMoreEventBinding.inflate(LayoutInflater.from(context),parent,false);
        return new EventMoreViewHolder(binding,context,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull EventMoreViewHolder holder, int position) {
       ImageView profile = holder.binding.imageViewProductEventProfile;
       EventModel eventModel = model.get(position);
       GlideImage(profile,eventModel.getImageUrl());
       holder.binding.textViewCompanyName.setText(eventModel.getCompanyName());
       holder.binding.textViewEventDate.setText(eventModel.getDateOfEvent());
       holder.binding.textViewEventTitle.setText(eventModel.getTitle());

       holder.binding.imageViewEventState.setBackgroundResource(R.drawable.event_state_icon_proceeding);
       holder.binding.textviewEventState.setText("체험단");
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public static class EventMoreViewHolder extends RecyclerView.ViewHolder {
        ItemMoreEventBinding binding;
        OnItemClickListener listener;
        Context context;

        public EventMoreViewHolder(ItemMoreEventBinding binding, Context context, OnItemClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
            this.listener = listener;

            binding.getRoot().setOnClickListener(v ->
                    listener.onItemSelected(binding.getRoot(),getAdapterPosition(),""));
        }
    }
    private void GlideImage(ImageView view, String url){
        Glide.with(context).load(url).into(view);
    }
}
