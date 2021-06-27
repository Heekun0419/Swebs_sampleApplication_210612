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
import com.example.swebs_sampleapplication_210612.databinding.ItemProductCertifiedCompanyBinding;

import org.jetbrains.annotations.NotNull;


public class CertifiedCompanyAdapter extends RecyclerView.Adapter<CertifiedCompanyAdapter.Certified_itemViewHolder> {

    Context context;
    private ItemProductCertifiedCompanyBinding binding;
    String ImageUrl = "https://i.pinimg.com/originals/a2/4f/e6/a24fe6cabab71872039e30af52e7dd9e.png";

    public CertifiedCompanyAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public Certified_itemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        binding = ItemProductCertifiedCompanyBinding.inflate(LayoutInflater.from(context),parent,false);

        return new Certified_itemViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Certified_itemViewHolder holder, int position) {
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

    public static class Certified_itemViewHolder extends RecyclerView.ViewHolder{

        ImageView view;

        public Certified_itemViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.imageView_product_main_profile);
        }

    }

    private void GlideImage(ImageView view){
        Glide.with(context).load(ImageUrl).into(view);
    }
}
