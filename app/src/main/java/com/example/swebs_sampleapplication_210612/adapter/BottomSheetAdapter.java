package com.example.swebs_sampleapplication_210612.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swebs_sampleapplication_210612.R;

import org.jetbrains.annotations.NotNull;

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.BottomSheetViewHolder>{

    private OnItemClickListener listener;
    Context context;

    String[] title ={"SCAN 히스토리","구매등록 리스트","복제품 신고"};
    Integer[] Image ={R.drawable.ic_qr_code,
            R.drawable.ic_baseline_purchase_list,
            R.drawable.ic_alert};

    public BottomSheetAdapter(Context context, OnItemClickListener listener){
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public BottomSheetViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new BottomSheetViewHolder(LayoutInflater.from(parent.getContext()),parent,listener);    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BottomSheetViewHolder holder, int position) {
        holder.textView.setText(title[position]);
        holder.imageView.setImageResource(Image[position]);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class BottomSheetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        ImageView imageView;
        OnItemClickListener listener;

        public BottomSheetViewHolder(LayoutInflater inflater, @NonNull @NotNull ViewGroup parent, OnItemClickListener listener) {
            super(inflater.inflate(R.layout.item_topmenu_bottomsheet,parent,false));
            this.listener = listener;
            imageView = itemView.findViewById(R.id.imageView_bottom_sheet_item);
            textView = itemView.findViewById(R.id.textView_bottomSheet);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemSelected(itemView, getAdapterPosition());
        }
    }
}
