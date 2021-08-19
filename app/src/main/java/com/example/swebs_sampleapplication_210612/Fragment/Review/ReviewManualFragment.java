package com.example.swebs_sampleapplication_210612.Fragment.Review;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.swebs_sampleapplication_210612.Activity.ReviewActivity;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.FragmentReviewManualBinding;


public class ReviewManualFragment extends Fragment {

    private FragmentReviewManualBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentReviewManualBinding.inflate(inflater,container,false);
        binding.btnReviewWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ReviewActivity)requireActivity()).onBackPressed();
            }
        });
        return binding.getRoot();
    }
}