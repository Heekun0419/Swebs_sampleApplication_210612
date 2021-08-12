package com.example.swebs_sampleapplication_210612.Fragment.Information_menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.FragmentDeleteIdFargmentBinding;

public class DeleteIdFargment extends Fragment {

    private FragmentDeleteIdFargmentBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDeleteIdFargmentBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

}