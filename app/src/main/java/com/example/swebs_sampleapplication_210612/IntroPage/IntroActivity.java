package com.example.swebs_sampleapplication_210612.IntroPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityIntroBinding;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;

public class IntroActivity extends AppCompatActivity {
    private ActivityIntroBinding binding;
    private ArrayList<Integer> drawableList = new ArrayList<>();
    ViewPager2 viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        addList();
        IntroAdapter adapter = new IntroAdapter(this,drawableList);
        viewPager = binding.viewPager2IntroLayout;
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(adapter);

        DotsIndicator indicator = binding.indicatorIntro;
        indicator.setViewPager2(viewPager);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(position == drawableList.size()-1) {
                    binding.buttonNext.setText("시작하기");
                } else {
                    binding.buttonNext.setText("NEXT");
                }
            }
        });

        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void addList(){
        drawableList.add(R.drawable.intro_step00);
        drawableList.add(R.drawable.intro_step01);
        drawableList.add(R.drawable.intro_step02);
        drawableList.add(R.drawable.intro_step03);
        drawableList.add(R.drawable.intro_step04);
        drawableList.add(R.drawable.intro_step05);
        drawableList.add(R.drawable.intro_step06);
    }
}