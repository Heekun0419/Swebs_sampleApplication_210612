package com.example.swebs_sampleapplication_210612.IntroPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityIntroBinding;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;

public class IntroActivity extends AppCompatActivity {
    private ActivityIntroBinding binding;
    private final ArrayList<Integer> drawableList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        addIntroPage();

        IntroAdapter adapter = new IntroAdapter(this,drawableList);
        binding.viewPager2IntroLayout.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        binding.viewPager2IntroLayout.setAdapter(adapter);

        DotsIndicator indicator = binding.indicatorIntro;
        indicator.setViewPager2(binding.viewPager2IntroLayout);

        binding.layoutClose.setOnClickListener(v -> onBackPressed());
        binding.buttonClose.setOnClickListener(v -> onBackPressed());

        binding.viewPager2IntroLayout.setPageTransformer((page, position) -> {
            if (binding.viewPager2IntroLayout.getCurrentItem() >= drawableList.size()-1)
                binding.buttonNext.setText("지금 시작하기!");
            else
                binding.buttonNext.setText("Next");
        });

        binding.buttonNext.setOnClickListener(v -> {
            if (binding.viewPager2IntroLayout.getCurrentItem() >= drawableList.size()-1)
                finish();
            else
                binding.viewPager2IntroLayout.setCurrentItem(binding.viewPager2IntroLayout.getCurrentItem()+1);
        });



    }

    private void addIntroPage(){
        drawableList.add(R.drawable.intro_step00);
        drawableList.add(R.drawable.intro_step01);
        drawableList.add(R.drawable.intro_step02);
        drawableList.add(R.drawable.intro_step03);
        drawableList.add(R.drawable.intro_step04);
        drawableList.add(R.drawable.intro_step05);
        drawableList.add(R.drawable.intro_step06);
    }
}