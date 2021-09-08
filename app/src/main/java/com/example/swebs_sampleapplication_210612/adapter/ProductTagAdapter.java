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
import com.example.swebs_sampleapplication_210612.adapter.Listener.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.databinding.ItemProductTagBinding;

import java.util.ArrayList;

public class ProductTagAdapter extends RecyclerView.Adapter<ProductTagAdapter.TagViewHolder> {

    private Context context;
    private ArrayList<String> list;
    private ItemProductTagBinding binding;
    private OnItemClickListener listener;

    public ProductTagAdapter(Context context, ArrayList<String> list, OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemProductTagBinding.inflate(LayoutInflater.from(context),parent,false);
        return new TagViewHolder(binding.getRoot(),listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        if(list.get(position) !=null){
            String s = "# "+ list.get(position);
            holder.textView.setText(s);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class TagViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        OnItemClickListener listener;
        public TagViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            this.listener  = listener;
            textView = itemView.findViewById(R.id.textView_tag);
            imageView = itemView.findViewById(R.id.imageView_tag_close);

            imageView.setOnClickListener(v ->
                    listener.onItemSelected(imageView,getAdapterPosition(),""));
        }
    }
}
