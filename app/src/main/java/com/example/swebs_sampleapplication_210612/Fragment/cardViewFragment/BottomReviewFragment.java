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
import com.example.swebs_sampleapplication_210612.adapter.ReviewProductAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentBottomReviewBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BottomReviewFragment extends Fragment {

    private FragmentBottomReviewBinding binding;
    private ReviewRepository repository;
    private String prodSrl;
    private ReViewViewModel reViewViewModel;

    public BottomReviewFragment(String prodSrl){
        this.prodSrl = prodSrl;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new ReviewRepository(requireActivity().getApplication());
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


        return binding.getRoot();
    }

    private void initRecyclerView(List<ReviewModel> reviewModelArrayList){
        ReviewProductAdapter adapter = new ReviewProductAdapter(requireContext(), reviewModelArrayList);
        LinearLayoutManager manager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false);
        binding.recyclerViewProductReview.setLayoutManager(manager);
        binding.recyclerViewProductReview.setAdapter(adapter);
    }

}