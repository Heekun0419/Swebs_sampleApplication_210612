package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityPurchaseInfoBinding;
import com.google.android.material.datepicker.MaterialDatePicker;

public class PurchaseInfoActivity extends AppCompatActivity {

    private ActivityPurchaseInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPurchaseInfoBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.btnPurchaseDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date")
                        .build();

                datePicker.show(getSupportFragmentManager(),"datePicker");
            }
        });
    }
}