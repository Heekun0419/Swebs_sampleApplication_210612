package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.swebs_sampleapplication_210612.databinding.ActivityEventRegisterSuccessBinding;

public class EventRegisterSuccessActivity extends AppCompatActivity {

    private ActivityEventRegisterSuccessBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEventRegisterSuccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}