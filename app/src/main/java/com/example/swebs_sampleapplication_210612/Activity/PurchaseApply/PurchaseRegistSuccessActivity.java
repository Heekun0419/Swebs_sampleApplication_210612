package com.example.swebs_sampleapplication_210612.Activity.PurchaseApply;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Activity.ReviewWriteActivity;
import com.example.swebs_sampleapplication_210612.R;
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
        String userName = getIntent().getStringExtra("name");
        String userPhone = getIntent().getStringExtra("phone");
        String userEmail = getIntent().getStringExtra("email");
        String ImageUrl = getIntent().getStringExtra("imageUrl");

        binding.textViewProductName.setText(productName);
        binding.textViewPurchaseDate.setText(Date);
        binding.textViewPurchaseStoreName.setText(store);
        binding.textViewProductSrl.setText(productSrl);
        binding.textViewName.setText(userName);
        binding.textviewEmail.setText(userEmail);
        binding.textviewPhoneNumber.setText(userPhone);


        binding.btnReviewWrite.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ReviewWriteActivity.class);
            startActivity(intent);
        });

        Glide.with(this).load(ImageUrl).placeholder(R.drawable.ic_camera).into(binding.ImageViewPurchaseImage);
    }
}