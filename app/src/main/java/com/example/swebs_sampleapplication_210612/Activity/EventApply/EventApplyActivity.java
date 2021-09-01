package com.example.swebs_sampleapplication_210612.Activity.EventApply;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.swebs_sampleapplication_210612.databinding.ActivityApplyEventBinding;

public class EventApplyActivity extends AppCompatActivity {

    private ActivityApplyEventBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApplyEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}