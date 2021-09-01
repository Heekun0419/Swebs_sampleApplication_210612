package com.example.swebs_sampleapplication_210612.Activity.PurchaseApply;

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

        String store = getIntent().getStringExtra("store");
        String productName = getIntent().getStringExtra("productName");
        String productSrl = getIntent().getStringExtra("productSrl");
        String Date = getIntent().getStringExtra("date");

        binding.textViewProductName.setText(productName);
        binding.textViewPurchaseDate.setText(Date);
        binding.textViewPurchaseStoreName.setText(store);
        binding.textViewProductSrl.setText(productSrl);

    }
}