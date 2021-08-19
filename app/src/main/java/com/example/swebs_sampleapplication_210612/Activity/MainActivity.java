package com.example.swebs_sampleapplication_210612.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.Fragment.bottomSheetFragment;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.Fragment.MainFragment.ScanFragment;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.databinding.ActivityMainBinding;
import com.example.swebs_sampleapplication_210612.Fragment.MainFragment.myPageFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MainFragment.productionInfoFragment;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends FragmentActivity {

    public static final int NUM_PAGES = 3;

    private FragmentStateAdapter adapter;
    private ActivityMainBinding binding;
    private FragmentManager manager;
    private MyInfoRepository myInfoRepository;

    private final SPmanager sPmanager = new SPmanager(this);
    public DrawerLayout drawer;
    private long backBtnTime = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = getSupportFragmentManager();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myInfoRepository = new MyInfoRepository(getApplication());
        ViewPager2 viewPager =  binding.viewpager2Main;
        adapter = new ScreenSlidePagerAdapter(this,this);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

        Toolbar toolbar = findViewById(R.id.toolBar);

        drawer = binding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.closed);
        drawer.addDrawerListener(toggle);

        GlideImage(binding.navView.imageViewProfile);


        binding.navView.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.drawer_item_scan_history:
                        Intent intent = new Intent(getApplicationContext(),ScanHistoryActivity.class);
                        intent.putExtra("resultCode","scanHistory");
                        startActivity(intent);
                        break;
                    case R.id.drawer_item_report_copy:
                        intent = new Intent(getApplicationContext(),ScanHistoryActivity.class);
                        intent.putExtra("resultCode","copy");
                        startActivity(intent);
                        break;
                    case R.id.drawer_item_APP_info:
                        intent = new Intent(getApplicationContext(),InformationActivity.class);
                        intent.putExtra("resultCode","app_info");
                        startActivity(intent);
                        break;

                    case R.id.drawer_item_FAQ:
                        intent = new Intent(getApplicationContext(),InformationActivity.class);
                        intent.putExtra("resultCode","FAQ");
                        startActivity(intent);
                        break;

                    case R.id.drawer_item_manual:
                        intent = new Intent(getApplicationContext(),InformationActivity.class);
                        intent.putExtra("resultCode","manual");
                        startActivity(intent);
                        break;

                    case R.id.drawer_item_purchasing:
                        intent = new Intent(getApplicationContext(),InformationActivity.class);
                        intent.putExtra("resultCode","purchase_question");
                        startActivity(intent);
                        break;

                    case R.id.drawer_item_company_info:
                        intent = new Intent(getApplicationContext(),TopMenuActivity.class);
                        intent.putExtra("resultCode","certified");
                        startActivity(intent);
                }
                return true;
            }
        });

    }

    private static class ScreenSlidePagerAdapter extends FragmentStateAdapter {

        Context context;// mypage Fragment에 유저타입 넘겨줌
        public ScreenSlidePagerAdapter(FragmentActivity fa, Context context) {
            super(fa);
            this.context = context;
        }

        @NotNull
        @Override
        public Fragment createFragment(int position) {

            if (position == 0)
                return new productionInfoFragment();
            else if (position == 1)
                return new ScanFragment();
            else if (position == 2)
                return new myPageFragment(context);
            else
                return new ScanFragment();

        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

    @Override
    public void onBackPressed() {

        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            long curTime = System.currentTimeMillis();
            long gapTime = curTime - backBtnTime;

            if(0 <= gapTime && 2000 >= gapTime) {
                super.onBackPressed();
            }
            else {
                backBtnTime = curTime;
                Toast.makeText(this, "한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
            }
        }

    }

        public void BottomSheetOpen(){
        manager.beginTransaction().add(new bottomSheetFragment(),"dialog").commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // userType -> 네비게이션 뷰 변환
        myInfoRepository.getValueToLiveData("userType").observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null)
                    if(s.equals("company")){
                        binding.navView.textviewNavDrawerProductList.setVisibility(View.VISIBLE);
                        binding.navView.constraintLayout3.setVisibility(View.VISIBLE);
                    }else{
                        binding.navView.textviewNavDrawerProductList.setVisibility(View.GONE);
                        binding.navView.constraintLayout3.setVisibility(View.GONE);
                    }
            }
        });

        // Point
        myInfoRepository.getValueToLiveData("point").observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    String viewText = s + " P";
                    binding.navView.textViewUserPoint.setText("잔여포인트 : "+ viewText);
                }
            }
        });

        // Name
        myInfoRepository.getValueToLiveData("name").observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    String viewText = s + " 님";
                    binding.navView.textViewUserName.setText(viewText);
                }
            }
        });
    }

    private void GlideImage(ImageView view){
        Glide.with(getApplicationContext()).load(R.drawable.ic_profile_basic).circleCrop().into(view);
    }
}


