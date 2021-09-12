package com.example.swebs_sampleapplication_210612.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.ProductListModel;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.adapter.Listener.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.databinding.ItemProductCertifiedCompanyBinding;
import com.example.swebs_sampleapplication_210612.databinding.ItemReCommentBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class CertifiedCompanyAdapter extends RecyclerView.Adapter<CertifiedCompanyAdapter.Certified_itemViewHolder> {

    Context context;
    private ItemProductCertifiedCompanyBinding binding;
    String ImageUrl = "https://i.pinimg.com/originals/a2/4f/e6/a24fe6cabab71872039e30af52e7dd9e.png";
    OnItemClickListener listener;
    List<ProductListModel> list;
    public CertifiedCompanyAdapter(Context context, List<ProductListModel> list, OnItemClickListener listener){
        this.context = context;
        this.listener = listener;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public Certified_itemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = ItemProductCertifiedCompanyBinding.inflate(LayoutInflater.from(context),parent,false);
        return new Certified_itemViewHolder(binding,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Certified_itemViewHolder holder, int position) {
        ProductListModel model = list.get(position);
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

        // 제품 타이틀
        holder.binding.textViewProductName.setText(model.getProd_title());
        // 회사명
        holder.binding.textViewCompanyName.setText(model.getCorp_name());
        // 회사 로고
        GlideImage(holder.binding.imageViewCompanyLogo, getImageViewUrl(model.getLogo_link(),"100"));
        // 제품 사진
        GlideImage(holder.binding.imageViewProductMainProfile, getImageViewUrl(model.getFile_srl(),"600"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Certified_itemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ItemProductCertifiedCompanyBinding binding;
        OnItemClickListener listener;
        public Certified_itemViewHolder(ItemProductCertifiedCompanyBinding binding, OnItemClickListener listener) {
            super(binding.getRoot());
            this.listener =listener;
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemSelected(binding.getRoot(), getAdapterPosition(),"certified");
        }
    }

    public ProductListModel getItem(int position) {
        return list.get(position);
    }

    private String getImageViewUrl(String fileSrl, String Width) {
        String result = context.getString(R.string.IMAGE_VIEW_URL) + "?inputFileSrl=" + fileSrl;
        if (Width != null)
            result += "&inputImageWidth=" + Width;
        return result;
    }

    private void GlideImage(ImageView view, String url){
        Glide.with(context).load(url).centerCrop().into(view);
    }

}
