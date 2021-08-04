package com.example.swebs_sampleapplication_210612.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.swebs_sampleapplication_210612.Fragment.BuyingListFragment;
import com.example.swebs_sampleapplication_210612.Fragment.CloneReportFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreCertifiedFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreEventFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreReviewFragment;
import com.example.swebs_sampleapplication_210612.Fragment.ScanHistory_SwebsFragment;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityScanHistoryBinding;

public class ScanHistoryActivity extends AppCompatActivity {

    private ActivityScanHistoryBinding binding;
    private FragmentManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScanHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnScanHistoryBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String requestCode = getIntent().getStringExtra("resultCode");

        manager = getSupportFragmentManager();
        switch (requestCode) {
            case "scanHistory":
                moveFragment(new ScanHistory_SwebsFragment(),"SCAN 히스토리");
                break;
            case "purchaseList":
                moveFragment( new BuyingListFragment() ,"구매등록 리스트");
                break;
            case "copy":
                binding.btnFloatingScan.setVisibility(View.GONE);
                moveFragment( new CloneReportFragment() ,"복제품 신고");
                break;
        }
    }

    public void moveFragment(Fragment fragment, String string){
        binding.textViewTopMenuName.setText(string);
        manager.beginTransaction().replace(R.id.frame_scan_history, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(manager.getBackStackEntryCount() ==0){
            finish();
        }
    }
}
