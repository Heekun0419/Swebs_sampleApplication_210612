package com.example.swebs_sampleapplication_210612.adapter.TablayoutAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.CertifiedCompanyDetailModel;
import com.example.swebs_sampleapplication_210612.databinding.ItemMoreCertifiedCompanyBinding;

import java.util.ArrayList;

public class GridMoreCretifiedAdapter extends BaseAdapter {

    private Context context;
    private ItemMoreCertifiedCompanyBinding binding;
    private ArrayList<CertifiedCompanyDetailModel> list= new ArrayList<>();
    String ImageUrl = "https://i.pinimg.com/originals/a2/4f/e6/a24fe6cabab71872039e30af52e7dd9e.png";


    public GridMoreCretifiedAdapter(Context context, ArrayList<CertifiedCompanyDetailModel> list){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        binding = ItemMoreCertifiedCompanyBinding.inflate(LayoutInflater.from(context),parent,false);
        convertView = binding.getRoot();
        CertifiedCompanyDetailModel model = list.get(position);
        GlideImage(binding.imageViewProductMainProfile);

        binding.textViewCompanyName.setText(model.getSerial1());
        binding.textViewProductName.setText(model.getProd_title());

        return convertView;
    }

    private void GlideImage(ImageView view) {
        Glide.with(context).load(ImageUrl).into(view);
    }

    private void addItem(){

    }

    private void removeItem(){

    }
}
