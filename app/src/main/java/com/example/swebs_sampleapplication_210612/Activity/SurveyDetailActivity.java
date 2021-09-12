package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.swebs_sampleapplication_210612.databinding.ActivitySurveyDetailBinding;

import java.util.ArrayList;
import java.util.List;

public class SurveyDetailActivity extends AppCompatActivity {

    private ActivitySurveyDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySurveyDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.textViewPercentageOfSurvey1.setTextColor(Color.parseColor("#FF72B9"));
        binding.textViewPercentageOfSurvey1.setText(binding.btnSurvey1.getProgress()+"%");
        binding.textViewPercentageOfSurvey2.setText(binding.btnSurvey2.getProgress()+"%");
        binding.textViewPercentageOfSurvey3.setText(binding.btnSurvey3.getProgress()+"%");
        binding.textViewPercentageOfSurvey4.setText(binding.btnSurvey4.getProgress()+"%");

    }
}