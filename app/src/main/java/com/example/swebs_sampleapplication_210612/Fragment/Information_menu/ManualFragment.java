package com.example.swebs_sampleapplication_210612.Fragment.Information_menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.adapter.ManualAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentManualBinding;


public class ManualFragment extends Fragment {

    private FragmentManualBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentManualBinding.inflate(inflater,container,false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewManual.setLayoutManager(linearLayoutManager);
        binding.recyclerViewManual.setAdapter(new ManualAdapter(requireContext()));

        return binding.getRoot();
    }
}