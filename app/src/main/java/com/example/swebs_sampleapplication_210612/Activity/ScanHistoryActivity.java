package com.example.swebs_sampleapplication_210612.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScanHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String requestCode = getIntent().getStringExtra("TopMenu");

        manager = getSupportFragmentManager();
        switch (requestCode) {
            case "scanHistory":
                binding.textViewTopMenuName.setText("SCAN 히스토리");
                manager.beginTransaction().add(R.id.frame_scan_history, new ScanHistory_SwebsFragment()).commit();
                break;
            case "purchaseList":
                binding.textViewTopMenuName.setText("구매등록 리스트");
                manager.beginTransaction().add(R.id.frame_scan_history, new BuyingListFragment()).commit();
                break;
            case "copy":
                binding.btnFloatingScan.setVisibility(View.GONE);
                binding.textViewTopMenuName.setText("복제품 신고");
                manager.beginTransaction().add(R.id.frame_scan_history, new CloneReportFragment()).commit();
                break;
        }

    }
}
