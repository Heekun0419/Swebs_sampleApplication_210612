package com.example.swebs_sampleapplication_210612.adapter.TablayoutAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swebs_sampleapplication_210612.ViewModel.Model.EventModel;
import com.example.swebs_sampleapplication_210612.databinding.ItemViewpagerInRecyclerBinding;

import java.util.ArrayList;


public class TabViewPagerAdapter extends RecyclerView.Adapter<TabViewPagerAdapter.PagerViewHolder> {
    private Context context;
    private ArrayList<EventModel> list;

    public TabViewPagerAdapter(Context context, ArrayList<EventModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewpagerInRecyclerBinding binding = ItemViewpagerInRecyclerBinding.inflate(LayoutInflater.from(context), parent, false);
        return new PagerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PagerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class PagerViewHolder extends RecyclerView.ViewHolder {

        ItemViewpagerInRecyclerBinding binding;

        public PagerViewHolder(ItemViewpagerInRecyclerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
