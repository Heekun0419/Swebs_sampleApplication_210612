package com.example.swebs_sampleapplication_210612.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.swebs_sampleapplication_210612.Fragment.MainFragment.ScanFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MainFragment.myPageFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MainFragment.productionInfoFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreCertifiedFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreEventFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreReviewFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreSurveyFragment;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityTopMenuBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class TopMenuActivity extends FragmentActivity {

    private ActivityTopMenuBinding binding;
    private FragmentManager manager;
    TabLayout tabLayout;
    public static int NUM_PAGES;
    private String[] List ={};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTopMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 뒤로가기
        binding.btnTopMenuBack.setOnClickListener(v -> onBackPressed());

        // intent 넘길때 result code 받아와야함.
        String requestCode = getIntent().getStringExtra("resultCode");
        tabLayout = binding.tabLayout;

        manager = getSupportFragmentManager();

        // 코드따라서 프라그먼트 불러옴.
        switch (requestCode) {
            case "event":
                setTabLayout(getResources().getStringArray(R.array.tabLayout_list_event));
                binding.textViewTopMenuName.setText("이벤트");
                break;
            case "certified":
                setTabLayout(getResources().getStringArray(R.array.tabLayout_list_certified_company));
                binding.textViewTopMenuName.setText("인증업체");
                break;
            case "review":
                setTabLayout(getResources().getStringArray(R.array.tabLayout_list_review));
                binding.textViewTopMenuName.setText("리뷰");
                break;
            case "survey":
                setTabLayout(getResources().getStringArray(R.array.tabLayout_list_survey));
                binding.textViewTopMenuName.setText("서베이");
                break;
        }

        ViewPager2 viewPager =  binding.viewPager2TabLayoutActivity;
        ViewPagerAdapter adapter = new ViewPagerAdapter(this,this, NUM_PAGES, requestCode);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(List[position]);
            }
        }).attach();

    }

    private void setTabLayout(String[] list){
        NUM_PAGES = list.length;
        // tabLayout.addTab(tabLayout.newTab().setText(string));
        List = list;
    }

    private static class ViewPagerAdapter extends FragmentStateAdapter {
        Context context;
        int numPage;
        String title;

        public ViewPagerAdapter(FragmentActivity fa, Context context, int numPage, String title) {
            super(fa);
            this.context = context;
            this.numPage = numPage;
            this.title = title;
        }

        @NotNull
        @Override
        public Fragment createFragment(int position) {
            if(title.equals("certified")) {
                return new MoreCertifiedFragment(position);
            } else if(title.equals("event")) {
                return new MoreEventFragment(position);
            } else if(title.equals("review")) {
                return new MoreReviewFragment(position);
            } else{
                return new MoreSurveyFragment(position);
            }
        }

        @Override
        public int getItemCount() {
            return numPage;
        }
    }
}