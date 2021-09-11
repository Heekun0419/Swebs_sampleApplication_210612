package com.example.swebs_sampleapplication_210612.Fragment.MyTopMenuFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.Activity.TopMenuActivity.TopMenuActivity;
import com.example.swebs_sampleapplication_210612.Data.Repository.ReviewRepository;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ReviewModel;
import com.example.swebs_sampleapplication_210612.ViewModel.ReViewViewModel;
import com.example.swebs_sampleapplication_210612.adapter.MyReviewAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMyReviewBinding;

import java.util.List;

public class MyReviewFragment extends Fragment {

    private FragmentMyReviewBinding binding;
    private MyReviewAdapter adapter;
    private ReviewRepository repository;
    private ReViewViewModel viewModel;
    private SPmanager sPmanager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ReViewViewModel(requireActivity().getApplication());
        sPmanager = new SPmanager(requireContext());

        viewModel.getMyReviewList(sPmanager.getUserSrl(),null,null);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMyReviewBinding.inflate(inflater,container,false);

        viewModel.getLiveDataReviewOnly().observe(getViewLifecycleOwner(), list -> {
            initRecycler(list);
        });

        return binding.getRoot();
    }

    private void initRecycler(List<ReviewModel> list){
        adapter = new MyReviewAdapter(requireContext(), list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewMyReview.setLayoutManager(linearLayoutManager);
        binding.recyclerViewMyReview.setAdapter(adapter);
    }
}