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

    private List<ProductListModel> models;

    public CertifiedCompanyAdapter(Context context, List<ProductListModel> models, OnItemClickListener listener){
        this.context = context;
        this.listener = listener;
        this.models = models;
    }

    @NonNull
    @NotNull
    @Override
    public Certified_itemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = ItemProductCertifiedCompanyBinding.inflate(LayoutInflater.from(context),parent,false);
        return new CertifiedCompanyAdapter.Certified_itemViewHolder(binding.getRoot(), listener, binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Certified_itemViewHolder holder, int position) {
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

        // 제품 이름..
        holder.binding.textViewProductName.setText(models.get(position).getProd_title());

        // 업체 이름
        holder.binding.corpName.setText(models.get(position).getCorp_name());

        // 제품 이미지
        GlideImage(holder.binding.imageViewProductMainProfile, getImageViewUrl(models.get(position).getFile_srl(), "300"));
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public ProductListModel getItem(int position) {
        return models.get(position);
    }

    public static class Certified_itemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnItemClickListener listener;
        ItemProductCertifiedCompanyBinding binding;
        public Certified_itemViewHolder(@NonNull @NotNull View itemView, OnItemClickListener listener, ItemProductCertifiedCompanyBinding binding) {
            super(itemView);
            this.listener =listener;
            this.binding = binding;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemSelected(itemView, getAdapterPosition(),"certified");
        }
    }

    private void GlideImage(ImageView view, String url){
        Glide.with(context).load(url).into(view);
    }

    private String getImageViewUrl(String fileSrl, String Width) {
        String result = context.getString(R.string.IMAGE_VIEW_URL) + "?inputFileSrl=" + fileSrl;
        if (Width != null)
            result += "&inputImageWidth=" + Width;
        return result;
    }

}
