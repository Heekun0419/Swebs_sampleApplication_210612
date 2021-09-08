package com.example.swebs_sampleapplication_210612.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.swebs_sampleapplication_210612.Activity.ScanHistoryActivity;
import com.example.swebs_sampleapplication_210612.adapter.Listener.HistoryListClickListener;
import com.example.swebs_sampleapplication_210612.adapter.ScanHistoryAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentBuyingListBinding;


public class BuyingListFragment extends Fragment implements HistoryListClickListener {

    private FragmentBuyingListBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentBuyingListBinding.inflate(inflater,container,false);

        ScanHistoryAdapter scanHistoryAdapter = new ScanHistoryAdapter(requireContext(),this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewBuyingList.setLayoutManager(linearLayoutManager);
        binding.recyclerViewBuyingList.setAdapter(scanHistoryAdapter);

        return binding.getRoot();
    }

    @Override
    public void positiveButtonClicked(ImageButton button, int position, @Nullable @org.jetbrains.annotations.Nullable String code) {
        ((ScanHistoryActivity)requireActivity()).ViewGone();
        ((ScanHistoryActivity)requireActivity()).moveFragment(new ServiceNotReadyFragment(),"");
    }

    @Override
    public void negativeButtonClicked(ImageButton button, int position, @Nullable @org.jetbrains.annotations.Nullable String code) {

    }

    @Override
    public void companyNameClicked(TextView textView, int position, @Nullable @org.jetbrains.annotations.Nullable String code) {

    }
}