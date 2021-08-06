package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.swebs_sampleapplication_210612.databinding.ActivityAdressModifyBinding;

public class AdressModifyActivity extends AppCompatActivity {

    private ActivityAdressModifyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdressModifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}