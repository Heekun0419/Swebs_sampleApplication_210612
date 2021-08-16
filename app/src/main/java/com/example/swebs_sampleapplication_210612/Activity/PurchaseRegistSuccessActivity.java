package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.swebs_sampleapplication_210612.databinding.ActivityPurchaseRegistSuccessBinding;

public class PurchaseRegistSuccessActivity extends AppCompatActivity {

    private ActivityPurchaseRegistSuccessBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPurchaseRegistSuccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}