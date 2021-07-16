package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityPermissionCheckBinding;

public class PermissionCheckActivity extends AppCompatActivity {

    private ActivityPermissionCheckBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPermissionCheckBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnPermissionCheckBack.setOnClickListener(v -> onBackPressed());
    }
}