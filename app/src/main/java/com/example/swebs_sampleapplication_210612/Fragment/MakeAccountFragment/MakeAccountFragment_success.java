package com.example.swebs_sampleapplication_210612.Fragment.MakeAccountFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.Activity.MainActivity;
import com.example.swebs_sampleapplication_210612.Activity.MakeAccountActivity;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMakeAccountSuccessBinding;

public class MakeAccountFragment_success extends Fragment {

    private FragmentMakeAccountSuccessBinding binding;
    private SPmanager sPmanager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =FragmentMakeAccountSuccessBinding.inflate(inflater,container,false);
       Context context = container.getContext();
       sPmanager = new SPmanager(context);

       binding.btnMakeAccountOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), MainActivity.class);
                startActivity(intent);
                ((MakeAccountActivity)requireActivity()).finish();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}