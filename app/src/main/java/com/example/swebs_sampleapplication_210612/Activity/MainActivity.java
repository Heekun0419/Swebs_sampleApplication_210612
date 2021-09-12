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
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Activity.TopMenuActivity.TopMenuActivity;
import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.Fragment.MainFragment.ScanZxingFragment;
import com.example.swebs_sampleapplication_210612.Fragment.bottomSheetFragment;
import com.example.swebs_sampleapplication_210612.IntroPage.IntroActivity;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.databinding.ActivityMainBinding;
import com.example.swebs_sampleapplication_210612.Fragment.MainFragment.myPageFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MainFragment.productionInfoFragment;
import com.google.android.material.navigation.NavigationView;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.common.util.Utility;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends FragmentActivity {

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

        ScreenSlidePagerAdapter viewPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        ViewPager viewPager =  binding.viewpagerMain;

        viewPager.setAdapter(viewPagerAdapter);
        binding.viewpagerMain.setCurrentItem(1, false);
        binding.viewpagerMain.setOffscreenPageLimit(2);

       // Log.d("keyhas",getKeyHash(MainActivity.this));

        // Intro Page...
        if (!sPmanager.getIntroPage()) {
            sPmanager.setIntroPage(true);
            Intent intent = new Intent(this, IntroActivity.class);
            startActivity(intent);
        }

        Toolbar toolbar = findViewById(R.id.toolBar);
        DrawerLayout drawerLayout = binding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open, R.string.closed
        );
        drawerLayout.addDrawerListener(toggle);

        GlideImage(binding.navView.imageViewProfile);



        manager.setFragmentResultListener("Y", this, (requestKey, result) -> {

        });

        //기업사 제품 등록하기
        binding.navView.companyProductRegister.setOnClickListener(v -> {
            IntentInfoActivity("product");
        });
        // 어플 정보
        binding.navView.textviewNavDrawerAppInfo.setOnClickListener(v -> {
            IntentInfoActivity("app_info");
        });
        // 제품등록 리스트
        binding.navView.textviewNavDrawerProductList.setOnClickListener(v -> {

        });
        // 인증업체
        binding.navView.textviewNavDrawerCertifiedCompany.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TopMenuActivity.class);
            intent.putExtra("resultCode","certified");
            startActivity(intent);
        });
        // 구매문의
        binding.navView.textviewNavDrawerPurchaseQuestion.setOnClickListener(v -> {
            IntentInfoActivity("purchase_question");
        });

        // 공지사항
        binding.navView.textviewNavDrawerNotice.setOnClickListener(v -> {
            IntentInfoActivity("notice");
        });

        // 복제품 신고
        binding.navView.textviewNavDrawerReportCopy.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ScanHistoryActivity.class);
            intent.putExtra("resultCode","copy");
            startActivity(intent);
        });

        // 스캔 히스토리
        binding.navView.textviewNavDrawerScanHistory.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ScanHistoryActivity.class);
            intent.putExtra("resultCode","scanHistory");
            startActivity(intent);
        });

        // FAQ
        binding.navView.textviewNavDrawerFaq.setOnClickListener(v -> {
            IntentInfoActivity("FAQ");
        });

        // 사용법
        binding.navView.textviewNavDrawerManual.setOnClickListener(v -> {
            IntentInfoActivity("manual");
        });
    }
    private void IntentInfoActivity(String code){
        Intent intent = new Intent(getApplicationContext(),InformationActivity.class);
        intent.putExtra("resultCode",code);
        startActivity(intent);
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
        myInfoRepository.getValueToLiveData("nickName").observe(this, new Observer<String>() {
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

    public static String getKeyHash(final Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            if (packageInfo == null)
                return null;

            for (Signature signature : packageInfo.signatures) {
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    return android.util.Base64.encodeToString(md.digest(), android.util.Base64.NO_WRAP);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}


