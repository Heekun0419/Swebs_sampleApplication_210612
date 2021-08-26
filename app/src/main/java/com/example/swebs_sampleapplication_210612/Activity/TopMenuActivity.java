package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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

        // 뒤로가기
        binding.btnTopMenuBack.setOnClickListener(v -> onBackPressed());

        // intent 넘길때 result code 받아와야함.
        String requestCode = getIntent().getStringExtra("resultCode");

        manager = getSupportFragmentManager();
        // 코드따라서 프라그먼트 불러옴.
        switch (requestCode) {
            case "event":
                binding.textViewTopMenuName.setText("이벤트");
                moveFragment(new MoreEventFragment());
                break;
            case "certified":
                binding.textViewTopMenuName.setText("인증업체");
                moveFragment(new MoreCertifiedFragment());
                break;
            case "review":
                binding.textViewTopMenuName.setText("리뷰");
                moveFragment(new MoreReviewFragment());
                break;
            case "survey":
                binding.textViewTopMenuName.setText("서베이");
                moveFragment(new MoreSurveyFragment());
                break;
        }

    }
    public void moveFragment(Fragment fragment){
        manager.beginTransaction().replace(R.id.frameLayout_more_top, fragment).commit();
    }

}