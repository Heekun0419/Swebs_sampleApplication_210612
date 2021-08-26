package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreCertifiedFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreEventFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreReviewFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreSurveyFragment;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityTopMenuBinding;

public class TopMenuActivity extends AppCompatActivity {

    private ActivityTopMenuBinding binding;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTopMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnTopMenuBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // intent 넘길때 result code 받아와야함.
        String requestCode = getIntent().getStringExtra("resultCode");

        manager = getSupportFragmentManager();
        if(requestCode.equals("event")) {
            binding.textViewTopMenuName.setText("이벤트");
            manager.beginTransaction().replace(R.id.frameLayout_more_top, new MoreEventFragment()).commit();
        }else if(requestCode.equals("certified")){
            binding.textViewTopMenuName.setText("인증업체");
            manager.beginTransaction().replace(R.id.frameLayout_more_top, new MoreCertifiedFragment()).commit();
        } else if(requestCode.equals("review")){
            binding.textViewTopMenuName.setText("리뷰");
            manager.beginTransaction().replace(R.id.frameLayout_more_top, new MoreReviewFragment()).commit();
        } else if(requestCode.equals("survey")){
            binding.textViewTopMenuName.setText("서베이");
            manager.beginTransaction().replace(R.id.frameLayout_more_top, new MoreSurveyFragment()).commit();
        }

    }

}