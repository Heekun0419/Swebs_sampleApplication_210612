package com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment.BottomReviewFragment;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityReviewProductBinding;

public class ReviewProductActivity extends AppCompatActivity {

    private ActivityReviewProductBinding binding;
    private FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frameLayout_review_product_activity,new BottomReviewFragment()).commit();
    }
}