package com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.adapter.ReviewAdapter;
import com.example.swebs_sampleapplication_210612.adapter.ReviewMoreAdapter;
import com.example.swebs_sampleapplication_210612.adapter.SurveyAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMoreReviewBinding;

public class MoreReviewFragment extends Fragment {

    private FragmentMoreReviewBinding binding;
    private int position;

    // viewPager 및 TabLayout position 받아옴.
    public MoreReviewFragment(int position) {
        // Required empty public constructor
        this.position = position;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMoreReviewBinding.inflate(inflater,container,false);

        ReviewMoreAdapter reviewAdapter = new ReviewMoreAdapter(requireContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewMoreReview.setLayoutManager(linearLayoutManager);
        binding.recyclerViewMoreReview.setAdapter(reviewAdapter);

        return binding.getRoot();
    }
}