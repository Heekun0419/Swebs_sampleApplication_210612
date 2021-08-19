package com.example.swebs_sampleapplication_210612.adapter;

import android.content.Context;
import android.icu.number.CompactNotation;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ItemScanHistoryListBinding;

import org.jetbrains.annotations.NotNull;

public class ScanHistoryAdapter extends RecyclerView.Adapter<ScanHistoryAdapter.ScanHistoryViewHolder> {

    private ItemScanHistoryListBinding binding;
    private HistoryListClickListener listener;
    private Context context;
    private  String ImageUrl ="https://i.pinimg.com/originals/a2/4f/e6/a24fe6cabab71872039e30af52e7dd9e.png";

    public ScanHistoryAdapter(Context context, HistoryListClickListener listener){
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public ScanHistoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = ItemScanHistoryListBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ScanHistoryViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ScanHistoryViewHolder holder, int position) {
        GlideImage(holder.view);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class ScanHistoryViewHolder extends RecyclerView.ViewHolder{
        ImageView view;
        ImageButton positiveButton, negativeButton; TextView textView;
        ItemScanHistoryListBinding binding;
        HistoryListClickListener listener;
        public ScanHistoryViewHolder(ItemScanHistoryListBinding binding, HistoryListClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
            view = binding.imageViewScanHistorySwebs;
            positiveButton = binding.btnScanHistoryAs;
            negativeButton = binding.btnReview;
            textView = binding.textViewReviewBrandName;
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.positiveButtonClicked(positiveButton,getAdapterPosition(),"");
                }
            });
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.negativeButtonClicked(negativeButton,getAdapterPosition(),"");
                }
            });
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.companyNameClicked(textView,getAdapterPosition(),textView.getText().toString());
                }
            });
        }

    }
    private void GlideImage(ImageView view) {
        Glide.with(context).load(ImageUrl).into(view);
    }
}
