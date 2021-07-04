package com.example.swebs_sampleapplication_210612.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

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

        manager = getSupportFragmentManager();

        manager.beginTransaction().add(R.id.frame_scan_history, new ScanHistory_SwebsFragment()).commit();


    }
}
