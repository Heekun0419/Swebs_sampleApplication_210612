package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.swebs_sampleapplication_210612.databinding.ActivityReviewWriteManualBinding;

public class ReviewWriteManualActivity extends AppCompatActivity {
    private ActivityReviewWriteManualBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewWriteManualBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}