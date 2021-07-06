package com.example.swebs_sampleapplication_210612.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.adapter.EventAdapter;
import com.example.swebs_sampleapplication_210612.adapter.ScanHistoryAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentScanHistorySwebsBinding;

public class ScanHistory_SwebsFragment extends Fragment {
    private FragmentScanHistorySwebsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentScanHistorySwebsBinding.inflate(inflater,container,false);

        ScanHistoryAdapter scanHistoryAdapter = new ScanHistoryAdapter(requireContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewScanHistorySwebs.setLayoutManager(linearLayoutManager);
        binding.recyclerViewScanHistorySwebs.setAdapter(scanHistoryAdapter);

        return binding.getRoot();
    }
}
