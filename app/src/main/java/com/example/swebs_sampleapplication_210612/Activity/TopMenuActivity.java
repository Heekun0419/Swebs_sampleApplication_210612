package com.example.swebs_sampleapplication_210612.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.CategoryDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.CategoryModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;
import com.example.swebs_sampleapplication_210612.Fragment.MainFragment.ScanFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MainFragment.myPageFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MainFragment.productionInfoFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreCertifiedFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreEventFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreReviewFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreSurveyFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MyTopMenuFragment.MyEventFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MyTopMenuFragment.MyReviewFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MyTopMenuFragment.MySurveyFragment;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.CategoryViewModel;
import com.example.swebs_sampleapplication_210612.databinding.ActivityTopMenuBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopMenuActivity extends FragmentActivity {

    private ActivityTopMenuBinding binding;
    private FragmentManager manager;
    private SwebsAPI retroAPI;
    TabLayout tabLayout;
    public static int NUM_PAGES;
    private String[] List ={};
    ViewPager2 viewPager;
    private CategoryViewModel viewModel;
    ArrayList<CategoryDetailModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retrofit Api 불러오기
        retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);
        viewModel =new CategoryViewModel(getApplication());
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
                getCategoryFromServer();
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

        viewModel.getCategoryDetailModelLiveData().observe(this, new Observer<ArrayList<CategoryDetailModel>>() {
            @Override
            public void onChanged(ArrayList<CategoryDetailModel> list) {
                initViewPager(requestCode,list);
            }
        });


    }

    private void setTabLayout(String[] list){
        NUM_PAGES = list.length;
        List = list;
    }

    private static class ViewPagerAdapter extends FragmentStateAdapter {

        Context context;
        int numPage;
        String title;
        ArrayList<CategoryDetailModel> list = new ArrayList<>();

        public ViewPagerAdapter(FragmentActivity fa, Context context, int numPage, String title, ArrayList<CategoryDetailModel> list) {
            super(fa);
            this.context = context;
            this.numPage = numPage;
            this.title = title;
            this.list = list;
        }

        @NotNull
        @Override
        public Fragment createFragment(int position) {
            CategoryDetailModel detailModel = list.get(position);
            switch (title) {
                case "certified":
                    return new MoreCertifiedFragment(position);
                case "event":
                    return new MoreEventFragment(detailModel.getCategory_srl());
                case "review":
                    return new MoreReviewFragment(position);
                case "my_review":
                    return new MyReviewFragment();
                case "my_event":
                    return new MyEventFragment();
                case "my_survey":
                    return new MySurveyFragment();
                default:
                    return new MoreSurveyFragment(position);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private void getCategoryFromServer(){
        Call<CategoryModel> call = retroAPI.getCategory();
        call.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
               if(response.isSuccessful() && response.body()!=null) {

                   CategoryModel categoryModel = response.body();

                   for(CategoryDetailModel detailModel : categoryModel.getCategory()) {
                       list.add(detailModel);
                   }
                   viewModel.setCategoryDetailModelLiveData(list);

               }
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {

            }
        });
    }

    private void initViewPager(String title, ArrayList<CategoryDetailModel> list){
        viewPager =  binding.viewPager2TabLayoutActivity;
        ViewPagerAdapter adapter = new ViewPagerAdapter(this,this, NUM_PAGES, title, list);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(adapter);
        //viewPager.setOffscreenPageLimit(3);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(title.equals("event")) {
                    // 타이틀 탭레이아웃에 set
                    tab.setText(list.get(position).getCategory_title());
                } else {
                    tab.setText(List[position]);
                }

            }
        }).attach();
    }
}