package com.example.swebs_sampleapplication_210612.Activity.TopMenuActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.lifecycle.Observer;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.CategoryDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.CategoryModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;

import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreCertifiedFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreEventFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreReviewFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment.MoreSurveyFragment;

import com.example.swebs_sampleapplication_210612.Fragment.MyTopMenuFragment.MySurveyFragment;
import com.example.swebs_sampleapplication_210612.ViewModel.CategoryViewModel;
import com.example.swebs_sampleapplication_210612.databinding.ActivityTopMenuBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
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
        // retrofit Api ????????????
        retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);
        viewModel =new CategoryViewModel(getApplication());

        binding = ActivityTopMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // ???????????? ??????
        viewPager =  binding.viewPager2TabLayoutActivity;

        // ????????????
        binding.btnTopMenuBack.setOnClickListener(v -> onBackPressed());

        // intent ?????? ????????? ????????? ??????.
        String requestCode = getIntent().getStringExtra("resultCode");
        setActivityTitle(requestCode);

        tabLayout = binding.tabLayout;
        //????????? 0 -> ?????? ???????????? ????????? ??????.
        list.add(new CategoryDetailModel("0","??????"));

        // ???????????? ???????????? ????????????
        getCategoryFromServer(requestCode);

        viewModel.getCategoryDetailModelLiveData().observe(this, list -> {
            initViewPager(requestCode, list);
        });
    }

    private void setActivityTitle(String requestCode){
        // ??????????????? ??????????????? ?????????.
        switch (requestCode) {
            case "event":
                binding.textViewTopMenuName.setText("?????????");
                break;
            case "certified":
                binding.textViewTopMenuName.setText("????????????");
                break;
            case "review":
                binding.textViewTopMenuName.setText("??????");
                break;
            case "survey":
                binding.textViewTopMenuName.setText("?????????");
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
                    return MoreCertifiedFragment.newInstance(detailModel.getCategory_srl());
                case "event":
                    return MoreEventFragment.newInstance(detailModel.getCategory_srl());
                case "review":
                    return MoreReviewFragment.newInstance(detailModel.getCategory_srl());
                default:
                    return MySurveyFragment.newInstance(detailModel.getCategory_srl());
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private void getCategoryFromServer(String categoryName){
        HashMap<String, RequestBody> formData = new HashMap<>();
        formData.put("inputCategoryName", RequestBody.create(categoryName, MediaType.parse("text/plane")));
        Call<CategoryModel> call = retroAPI.getCategory(formData);
        call.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
               if(response.isSuccessful()
               && response.body()!=null) {
                   // ???????????? List ????????????
                   CategoryModel categoryModel = response.body();
                   list.addAll(categoryModel.getCategory());

                   // ??????????????? ????????? ?????? ?????? ??????
                   if(list.size() <= 3)
                       binding.tabLayout.setTabMode(TabLayout.MODE_FIXED);
                   else
                       binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

                   // ???????????? Data Setting
                   viewModel.setCategoryDetailModelLiveData(list);
               }
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {

            }
        });
    }

    private void initViewPager(String title, ArrayList<CategoryDetailModel> list) {
       // ViewPager Adapter ??????.
        ViewPagerAdapter adapter = new ViewPagerAdapter(this,this, title, list);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            // ????????? ?????????????????? set
            tab.setText(list.get(position).getCategory_title());
        }).attach();

        viewPager.setOffscreenPageLimit(list.size()-1);
    }
}