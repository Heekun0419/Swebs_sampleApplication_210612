package com.example.swebs_sampleapplication_210612.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.databinding.ItemProductCertifiedCompanyBinding;

public class GridMoreCretifiedAdapter extends BaseAdapter {

    private Context context;
    private ItemProductCertifiedCompanyBinding binding;
    String ImageUrl = "https://i.pinimg.com/originals/a2/4f/e6/a24fe6cabab71872039e30af52e7dd9e.png";


    public GridMoreCretifiedAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        binding = ItemProductCertifiedCompanyBinding.inflate(LayoutInflater.from(context),parent,false);
        convertView = binding.getRoot();
        GlideImage(binding.imageViewProductMainProfile);
        return convertView;
    }

    private void GlideImage(ImageView view) {
        Glide.with(context).load(ImageUrl).into(view);
    }
}
