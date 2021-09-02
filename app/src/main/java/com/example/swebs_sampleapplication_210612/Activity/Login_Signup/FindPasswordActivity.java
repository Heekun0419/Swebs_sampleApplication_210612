package com.example.swebs_sampleapplication_210612.Activity.Login_Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.swebs_sampleapplication_210612.databinding.ActivityFindPasswordBinding;

public class FindPasswordActivity extends AppCompatActivity {
    private ActivityFindPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFindPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}