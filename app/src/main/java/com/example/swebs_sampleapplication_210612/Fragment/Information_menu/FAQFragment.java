package com.example.swebs_sampleapplication_210612.Fragment.Information_menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.adapter.BottomSheetAdapter;
import com.example.swebs_sampleapplication_210612.adapter.ManualAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentFAQBinding;

public class FAQFragment extends Fragment {

    private FragmentFAQBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFAQBinding.inflate(inflater,container,false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewFaq.setLayoutManager(linearLayoutManager);
        binding.recyclerViewFaq.setAdapter(new ManualAdapter(requireContext()));

        return binding.getRoot();
    }
}