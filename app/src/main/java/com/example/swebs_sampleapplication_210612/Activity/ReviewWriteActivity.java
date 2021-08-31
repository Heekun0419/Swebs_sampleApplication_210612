package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.swebs_sampleapplication_210612.Fragment.Review.WriteReviewFragment;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityReviewBinding;
import com.example.swebs_sampleapplication_210612.databinding.ActivityReviewWriteBinding;

public class ReviewWriteActivity extends AppCompatActivity {
    private ActivityReviewWriteBinding binding;
    private FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnCloseReviewWrite.setVisibility(View.GONE);

        binding.btnCloseReviewWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.btnReviewActivityBack.setOnClickListener(v -> onBackPressed());

        manager = getSupportFragmentManager();
        moveFragment(new WriteReviewFragment(),getString(R.string.review_write_0));
    }

    public void moveFragment(Fragment fragment, String string){
        if(string.equals("")){
            binding.btnCloseReviewWrite.setVisibility(View.VISIBLE);
            binding.textViewReviewActivityName.setText(string);
            binding.btnReviewActivityBack.setVisibility(View.GONE);
            manager.beginTransaction().setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left)
                    .replace(R.id.frameLayout_review_activity, fragment).addToBackStack(null).commit();
        }else{
            binding.textViewReviewActivityName.setText(string);
            manager.beginTransaction().replace(R.id.frameLayout_review_activity, fragment).addToBackStack(null).commit();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(manager.getBackStackEntryCount() ==0){
            finish();
        }else {
            binding.btnReviewActivityBack.setVisibility(View.VISIBLE);
            binding.btnCloseReviewWrite.setVisibility(View.GONE);
            binding.textViewReviewActivityName.setText(getString(R.string.review_write_0));
        }
    }

}