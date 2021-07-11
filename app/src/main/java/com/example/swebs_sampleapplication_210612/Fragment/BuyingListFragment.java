package com.example.swebs_sampleapplication_210612.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.adapter.ScanHistoryAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentBuyingListBinding;


public class BuyingListFragment extends Fragment {

    private FragmentBuyingListBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentBuyingListBinding.inflate(inflater,container,false);

        ScanHistoryAdapter scanHistoryAdapter = new ScanHistoryAdapter(requireContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewBuyingList.setLayoutManager(linearLayoutManager);
        binding.recyclerViewBuyingList.setAdapter(scanHistoryAdapter);

        return binding.getRoot();
    }
}