package com.example.swebs_sampleapplication_210612.adapter.TablayoutAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.ProductListModel;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.adapter.Listener.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.databinding.ItemMoreCertifiedCompanyBinding;

import java.util.ArrayList;
import java.util.List;

public class GridMoreCretifiedAdapter extends BaseAdapter {

    private Context context;
    private ItemMoreCertifiedCompanyBinding binding;
    private List<ProductListModel> list = new ArrayList<>();
    private OnItemClickListener listener;

    public GridMoreCretifiedAdapter(Context context, List<ProductListModel> list, OnItemClickListener listener){
        this.list = list;
        this.context = context;
        this.listener = listener;
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
        ProductListModel model = list.get(position);

        GlideImage(binding.imageViewProductMainProfile, getImageViewUrl(model.getFile_srl(), "300"));

        if (!model.getLogo_link().equals("")
        && model.getLogo_link() != null)
            GlideImage(binding.imageViewProductLogo, model.getLogo_link());
        else
            binding.imageViewProductLogo.setVisibility(View.GONE);

        binding.textViewCompanyName.setText(model.getCorp_name());
        binding.textViewProductName.setText(model.getProd_title());

        if (model.isNew())
            binding.layoutNewTab.setVisibility(View.VISIBLE);

        convertView.setOnClickListener(v -> {
            listener.onItemSelected(v, position, model.getProd_srl());
        });

        return convertView;
    }

    private void GlideImage(ImageView view, String url) {
        Glide.with(context).load(url).centerCrop().into(view);
    }

    public void addItem(ProductListModel model, int position) {
        this.list.add(position, model);
        notifyDataSetChanged();
    }

    public void changeItem(List<ProductListModel> list){
        this.list = list;
        notifyDataSetChanged();
    }

    private String getImageViewUrl(String fileSrl, String Width) {
        String result = context.getString(R.string.IMAGE_VIEW_URL) + "?inputFileSrl=" + fileSrl;
        if (Width != null)
            result += "&inputImageWidth=" + Width;
        return result;
    }
}
