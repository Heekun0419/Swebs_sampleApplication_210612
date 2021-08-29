package com.example.swebs_sampleapplication_210612.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.Fragment.MainFragment.ScanZxingFragment;
import com.example.swebs_sampleapplication_210612.Fragment.bottomSheetFragment;
import com.example.swebs_sampleapplication_210612.IntroPage.IntroActivity;
import com.example.swebs_sampleapplication_210612.IntroPage.IntroAdapter;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.Fragment.MainFragment.ScanFragment;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.databinding.ActivityMainBinding;
import com.example.swebs_sampleapplication_210612.Fragment.MainFragment.myPageFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MainFragment.productionInfoFragment;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    public static final int NUM_PAGES = 4;
    private ActivityMainBinding binding;
    private FragmentManager manager;
    private MyInfoRepository myInfoRepository;

    private final SPmanager sPmanager = new SPmanager(this);
    private long backBtnTime = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        manager = getSupportFragmentManager();
        myInfoRepository = new MyInfoRepository(getApplication());

        ScreenSlidePagerAdapter viewPagerAdapter = new ScreenSlidePagerAdapter(manager);
        binding.viewpager2Main.setAdapter(viewPagerAdapter);
        binding.viewpager2Main.setCurrentItem(1, false);
        binding.viewpager2Main.setOffscreenPageLimit(2);

        // Intro Page...
        if (!sPmanager.getIntroPage()) {
            sPmanager.setIntroPage(true);
            Intent intent = new Intent(this, IntroActivity.class);
            startActivity(intent);
        }

        Toolbar toolbar = findViewById(R.id.toolBar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, toolbar, R.string.open, R.string.closed
        );
        binding.drawerLayout.addDrawerListener(toggle);

        GlideImage(binding.navView.imageViewProfile);

        binding.navView.companyProductRegister.setOnClickListener(v -> {
            Intent intent1 = new Intent(MainActivity.this,InformationActivity.class);
            intent1.putExtra("resultCode","product");
            startActivity(intent1);
        });

        manager.setFragmentResultListener("Y", this, (requestKey, result) -> {

        });


        binding.navView.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                Intent intent = null;
                if (item.getItemId() == R.id.drawer_item_scan_history) {
                    intent = new Intent(getApplicationContext(), ScanHistoryActivity.class);
                    intent.putExtra("resultCode","scanHistory");
                } else if (item.getItemId() == R.id.drawer_item_report_copy) {
                    intent = new Intent(getApplicationContext(),ScanHistoryActivity.class);
                    intent.putExtra("resultCode","copy");
                } else if (item.getItemId() == R.id.drawer_item_APP_info) {
                    intent = new Intent(getApplicationContext(),InformationActivity.class);
                    intent.putExtra("resultCode","app_info");
                } else if (item.getItemId() == R.id.drawer_item_FAQ) {
                    intent = new Intent(getApplicationContext(),InformationActivity.class);
                    intent.putExtra("resultCode","FAQ");
                } else if (item.getItemId() == R.id.drawer_item_manual) {
                    intent = new Intent(getApplicationContext(),InformationActivity.class);
                    intent.putExtra("resultCode","manual");
                } else if (item.getItemId() == R.id.drawer_item_purchasing) {
                    intent = new Intent(getApplicationContext(),InformationActivity.class);
                    intent.putExtra("resultCode","purchase_question");
                } else if (item.getItemId() == R.id.drawer_item_company_info) {
                    intent = new Intent(getApplicationContext(),TopMenuActivity.class);
                    intent.putExtra("resultCode", "certified");
                }

                    startActivity(intent);
                return true;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("zxing", "call - Main");
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

    @Override
    public void onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START);
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

    public void openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START);
    }

    private static class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
        FragmentManager manager;
        ArrayList<Fragment> fragments = new ArrayList<>();

        public ScreenSlidePagerAdapter(@NonNull FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.manager = fm;
            fragments.add(new productionInfoFragment());
            //fragments.add(new ScanFragment());
            fragments.add(new ScanZxingFragment());
            fragments.add(new myPageFragment());
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
           return fragments.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.destroyItem(container, position, object);
        }
    }


    public void BottomSheetOpen(){
        manager.beginTransaction().add(new bottomSheetFragment(),"dialog").commit();
    }

    private void GlideImage(ImageView view){
        Glide.with(getApplicationContext()).load(R.drawable.ic_profile_basic).circleCrop().into(view);
    }
}


