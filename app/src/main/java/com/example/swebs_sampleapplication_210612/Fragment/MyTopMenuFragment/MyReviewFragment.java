package com.example.swebs_sampleapplication_210612.Fragment.MyTopMenuFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.adapter.MyReviewAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMyReviewBinding;

public class MyReviewFragment extends Fragment {
    private FragmentMyReviewBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMyReviewBinding.inflate(inflater,container,false);

        MyReviewAdapter adapter = new MyReviewAdapter(requireContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewMyReview.setLayoutManager(linearLayoutManager);
        binding.recyclerViewMyReview.setAdapter(adapter);

        return binding.getRoot();
    }
}