package com.example.swebs_sampleapplication_210612.Activity.TopMenuActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.CategoryDetailModel;
import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreCertifiedFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreEventFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreReviewFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreSurveyFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MyTopMenuFragment.MyEventFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MyTopMenuFragment.MyReviewFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MyTopMenuFragment.MySurveyFragment;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityMyTopMenuBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyTopMenuActivity extends AppCompatActivity {

    private ActivityMyTopMenuBinding binding;
    TabLayout tabLayout;
    public static int NUM_PAGES;
    private String[] List ={};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyTopMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String requestCode = getIntent().getStringExtra("resultCode");
        tabLayout = binding.tabLayout;

        // 뒤로가기
        binding.btnTopMenuBack.setOnClickListener(v -> onBackPressed());
        FragmentManager manager = getSupportFragmentManager();

        switch (requestCode){
            case "my_review" :
                setTabLayout(getResources().getStringArray(R.array.my_review_tab_list));
                binding.textViewTopMenuName.setText(getString(R.string.my_review_text_1));
                break;
            case "my_event" :
                setTabLayout(getResources().getStringArray(R.array.my_event_tab_list));
                binding.textViewTopMenuName.setText(getString(R.string.my_event_text_1));
                break;
            case "my_survey":
                setTabLayout(getResources().getStringArray(R.array.my_survey_tab_list));
                binding.textViewTopMenuName.setText(getString(R.string.my_survey_text_1));
                break;
        }

        ViewPager2 viewPager =  binding.viewPager2TabLayoutActivity;
        ViewPagerAdapter adapter = new ViewPagerAdapter(this,this, NUM_PAGES, requestCode);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(adapter);


        new TabLayoutMediator(tabLayout, viewPager, (tab, position)
                -> tab.setText(List[position])).attach();

    }


    private void setTabLayout(String[] list){
        NUM_PAGES = list.length;
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
            switch (title) {
                case "my_event":
                    return new MyEventFragment();
                case "my_survey":
                    return new MySurveyFragment();
                default:
                    return new MyReviewFragment();
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}