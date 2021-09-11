package com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.Activity.ServiceNotReadyActivity;
import com.example.swebs_sampleapplication_210612.Data.Repository.ReviewRepository;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ReviewModel;
import com.example.swebs_sampleapplication_210612.ViewModel.ReViewViewModel;
import com.example.swebs_sampleapplication_210612.adapter.Listener.ReviewClickListener;
import com.example.swebs_sampleapplication_210612.adapter.ReviewProductAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentBottomReviewBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BottomReviewFragment extends Fragment implements ReviewClickListener {

    private FragmentBottomReviewBinding binding;
    private String prodSrl;
    private ReViewViewModel reViewViewModel;
    ReviewProductAdapter adapter;

    public BottomReviewFragment(String prodSrl){
        this.prodSrl = prodSrl;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reViewViewModel = new ReViewViewModel(requireActivity().getApplication());

        reViewViewModel.getReviewOnlyList(prodSrl,null,null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBottomReviewBinding.inflate(inflater,container,false);

        reViewViewModel.getLiveDataReviewOnly().observe(getViewLifecycleOwner(), new Observer<List<ReviewModel>>() {
            @Override
            public void onChanged(List<ReviewModel> list) {
                initRecyclerView(list);
            }
        });

        reViewViewModel.getIsLike().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                adapter.isLiked(aBoolean);
            }
        });

        return binding.getRoot();
    }

    private void initRecyclerView(List<ReviewModel> reviewModelArrayList){
        adapter = new ReviewProductAdapter(requireContext(), reviewModelArrayList, this);
        LinearLayoutManager manager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false);
        binding.recyclerViewProductReview.setLayoutManager(manager);
        binding.recyclerViewProductReview.setAdapter(adapter);
    }

    @Override
    public void profileClicked(int position) {

    }

    @Override
    public void LikeClicked(int position, boolean isLike) {
       reViewViewModel.setIsLike(isLike);
    }

    @Override
    public void reportClicked(int position) {

    }

    @Override
    public void modifyClicked(int position) {

    }

    @Override
    public void removeClicked(int position) {

    }

    @Override
    public void CommentClicked(int position, ReviewModel model) {

    }
}