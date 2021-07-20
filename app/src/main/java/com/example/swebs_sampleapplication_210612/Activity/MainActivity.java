package com.example.swebs_sampleapplication_210612.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.swebs_sampleapplication_210612.Fragment.bottomSheetFragment;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.Fragment.MainFragment.ScanFragment;
import com.example.swebs_sampleapplication_210612.databinding.ActivityMainBinding;
import com.example.swebs_sampleapplication_210612.Fragment.MainFragment.myPageFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MainFragment.productionInfoFragment;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends FragmentActivity {

    public static final int NUM_PAGES = 3;
    private boolean isFirst = true;
    private FragmentStateAdapter adapter;
    private ActivityMainBinding binding;
    private FragmentManager manager;

    public DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = getSupportFragmentManager();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       ViewPager2 viewPager =  binding.viewpager2Main;
        adapter = new ScreenSlidePagerAdapter(this);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);


        Toolbar toolbar = findViewById(R.id.toolBar);

        drawer = binding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.closed);
        drawer.addDrawerListener(toggle);

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

        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {

            if (position == 0)
                return new productionInfoFragment();
            else if (position == 1)
                return new ScanFragment();
            else if (position == 2)
                return new myPageFragment();
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
            super.onBackPressed();
        }
    }

    public void BottomSheetOpen(){
        manager.beginTransaction().add(new bottomSheetFragment(),"dialog").commit();
    }
}


