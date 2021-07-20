package com.example.swebs_sampleapplication_210612.Fragment.MakeAccountFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.Activity.MakeAccountActivity;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMakeAccountTermsBinding;

public class MakeAccountFragment_terms extends Fragment {

    private FragmentMakeAccountTermsBinding binding;
    private Fragment fragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMakeAccountTermsBinding.inflate( inflater,container, false);

        binding.btnTermsAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MakeAccountActivity)requireActivity()).moveFragment(new MakeAccountFragment_userInfo());
            }
        });

        return binding.getRoot();
    }
}