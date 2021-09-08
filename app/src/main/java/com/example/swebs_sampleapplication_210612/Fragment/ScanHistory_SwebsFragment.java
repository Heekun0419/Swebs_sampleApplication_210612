package com.example.swebs_sampleapplication_210612.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.swebs_sampleapplication_210612.Activity.ReviewWriteActivity;
import com.example.swebs_sampleapplication_210612.Activity.ServiceNotReadyActivity;
import com.example.swebs_sampleapplication_210612.adapter.Listener.HistoryListClickListener;
import com.example.swebs_sampleapplication_210612.adapter.ScanHistoryAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentScanHistorySwebsBinding;

public class ScanHistory_SwebsFragment extends Fragment implements HistoryListClickListener {
    private FragmentScanHistorySwebsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentScanHistorySwebsBinding.inflate(inflater,container,false);

        ScanHistoryAdapter scanHistoryAdapter = new ScanHistoryAdapter(requireContext(),this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewScanHistorySwebs.setLayoutManager(linearLayoutManager);
        binding.recyclerViewScanHistorySwebs.setAdapter(scanHistoryAdapter);

        return binding.getRoot();
    }

    @Override
    public void positiveButtonClicked(ImageButton button, int position, @Nullable @org.jetbrains.annotations.Nullable String code) {
         Intent intent = new Intent(requireActivity(), ServiceNotReadyActivity.class);
         startActivity(intent);
    }

    @Override
    public void negativeButtonClicked(ImageButton button, int position, @Nullable @org.jetbrains.annotations.Nullable String code) {
        Intent intent = new Intent(requireActivity(), ReviewWriteActivity.class);
        startActivity(intent);
    }

    @Override
    public void companyNameClicked(TextView textView, int position, @Nullable @org.jetbrains.annotations.Nullable String code) {

    }
}
