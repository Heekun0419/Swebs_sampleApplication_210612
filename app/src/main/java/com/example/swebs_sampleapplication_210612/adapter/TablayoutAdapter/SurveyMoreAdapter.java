package com.example.swebs_sampleapplication_210612.adapter.TablayoutAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.SurveyListModel;
import com.example.swebs_sampleapplication_210612.databinding.ItemMoreSurveyBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SurveyMoreAdapter extends RecyclerView.Adapter<SurveyMoreAdapter.SurveyMoreHolder>{

    private ItemMoreSurveyBinding binding;
    Context context;
    List<SurveyListModel> list = new ArrayList<>();

    public SurveyMoreAdapter(Context context, List<SurveyListModel>  list){
        this.context = context;
        this.list = list;
    }

    @NotNull
    @Override
    public SurveyMoreHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        binding = ItemMoreSurveyBinding.inflate(LayoutInflater.from(context),parent,false);
        return new SurveyMoreHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull SurveyMoreHolder holder, int position) {
        SurveyListModel model = list.get(position);
        holder.binding.textviewJoinCount.setText(model.getJoinCount()+ "명 참여중");
        holder.binding.textViewDate.setText(model.getDateOfEvent());
        holder.binding.textViewSurveyTitle.setText(model.getTitle());
        holder.binding.textViewCategoryTitle.setText(model.getCategory_title());
        holder.binding.imageViewPoint.setText("+"+model.getPoint()+"P");

        GlideImage(holder.binding.imageViewProductSurveyProfile,getImageViewUrl(model.getImageSrl(),"1000"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class SurveyMoreHolder extends RecyclerView.ViewHolder{

        ItemMoreSurveyBinding binding;

        public SurveyMoreHolder(ItemMoreSurveyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
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

