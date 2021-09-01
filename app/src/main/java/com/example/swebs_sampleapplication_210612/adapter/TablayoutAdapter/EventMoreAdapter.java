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
    private ArrayList<EventModel> EventList;
    private OnItemClickListener listener;

    public EventMoreAdapter( Context context, ArrayList<EventModel> EventList, OnItemClickListener listener) {
        this.context = context;
        this.EventList = EventList;
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
       EventModel eventModel = EventList.get(position);
       GlideImage(profile,eventModel.getImageUrl());
       holder.binding.textViewCompanyName.setText(eventModel.getCompanyName());
       holder.binding.textViewEventDate.setText(eventModel.getDateOfEvent());
       holder.binding.textViewEventTitle.setText(eventModel.getTitle());

       if (eventModel.getStatus() == 1)
           holder.binding.imageViewEventState.setBackgroundResource(R.drawable.event_state_icon_proceeding);
       else if (eventModel.getStatus() == 2)
           holder.binding.imageViewEventState.setBackgroundResource(R.drawable.event_state_icon_not_progress);
       else
           holder.binding.imageViewEventState.setBackgroundResource(R.drawable.event_state_icon_finish);

        holder.binding.textviewEventState.setText(eventModel.getStatusText());
    }

    @Override
    public int getItemCount() {
        return EventList.size();
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

    public void addItem(EventModel model, int position){
        EventList.add(position,model);
        notifyItemInserted(position);
    }

    public void removeItem(int position){
        EventList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,EventList.size());
    }


}
