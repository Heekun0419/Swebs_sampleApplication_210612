package com.example.swebs_sampleapplication_210612.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.lifecycle.Observer;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.CategoryDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.CategoryModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;

import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreCertifiedFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreEventFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreReviewFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreSurveyFragment;

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
    private SwebsAPI retroAPI;
    TabLayout tabLayout;
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
        // 뷰페이저 설정
        viewPager =  binding.viewPager2TabLayoutActivity;
        // 뒤로가기
        binding.btnTopMenuBack.setOnClickListener(v -> onBackPressed());

        // intent 에서 넘어온 값으로 구분.
        String requestCode = getIntent().getStringExtra("resultCode");
        setActivityTitle(requestCode);

        tabLayout = binding.tabLayout;
        //고정갑 0 -> 처음 카테고리 전체로 고정.
        list.add(new CategoryDetailModel(0,"전체"));

        // 서버에서 카테고리 불러오기
        getCategoryFromServer();

        // 탭레이아웃 개수에 따라 모드 변경
        if(list.size()<=4){
            binding.tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }else{
            binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

        viewModel.getCategoryDetailModelLiveData().observe(this, new Observer<ArrayList<CategoryDetailModel>>() {
            @Override
            public void onChanged(ArrayList<CategoryDetailModel> list) {
                initViewPager(requestCode,list);
            }
        });
    }

    private void setActivityTitle(String requestCode){
        // 코드따라서 프라그먼트 불러옴.
        switch (requestCode) {
            case "event":
                binding.textViewTopMenuName.setText("이벤트");
                break;
            case "certified":
                binding.textViewTopMenuName.setText("인증업체");
                break;
            case "review":
                binding.textViewTopMenuName.setText("리뷰");
                break;
            case "survey":
                binding.textViewTopMenuName.setText("서베이");
                break;
        }
    }



    private static class ViewPagerAdapter extends FragmentStateAdapter {

        Context context;
        String title;
        ArrayList<CategoryDetailModel> list = new ArrayList<>();

        public ViewPagerAdapter(FragmentActivity fa, Context context, String title, ArrayList<CategoryDetailModel> list) {
            super(fa);
            this.context = context;
            this.title = title;
            this.list = list;
        }

        @NotNull
        @Override
        public Fragment createFragment(int position) {
            CategoryDetailModel detailModel = list.get(position);
            switch (title) {
                case "certified":
                    return new MoreCertifiedFragment(detailModel.getCategory_srl());
                case "event":
                    return new MoreEventFragment(detailModel.getCategory_srl());
                case "review":
                    return new MoreReviewFragment(detailModel.getCategory_srl());
                default:
                    return new MoreSurveyFragment(detailModel.getCategory_srl());
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
                   // 카테고리 List 받아오기
                   CategoryModel categoryModel = response.body();
                   list.addAll(categoryModel.getCategory());
                   // 뷰모델에 Data Setting
                   viewModel.setCategoryDetailModelLiveData(list);
               }
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {

            }
        });
    }

    private void initViewPager(String title, ArrayList<CategoryDetailModel> list){

       // ViewPager Adapter 설정.
        ViewPagerAdapter adapter = new ViewPagerAdapter(this,this, title, list);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            // 타이틀 탭레이아웃에 set
            tab.setText(list.get(position).getCategory_title());
        }).attach();
    }
}