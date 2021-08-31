package com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.swebs_sampleapplication_210612.databinding.ActivitySurveyBinding;

public class SurveyActivity extends AppCompatActivity {
    private ActivitySurveyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySurveyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnItemClickedBack.setOnClickListener(v -> onBackPressed());
    }
}