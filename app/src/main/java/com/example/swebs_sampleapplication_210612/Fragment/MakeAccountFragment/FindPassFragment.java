package com.example.swebs_sampleapplication_210612.Fragment.MakeAccountFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.Activity.MakeAccountActivity;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.FragmentFindPassBinding;

import org.jetbrains.annotations.NotNull;

public class FindPassFragment extends Fragment {

    private FragmentFindPassBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFindPassBinding.inflate(inflater,container,false);

        binding.btnFindPassOk.setOnClickListener(v ->
                ((MakeAccountActivity)requireActivity()).finish());

        return binding.getRoot();
    }
}