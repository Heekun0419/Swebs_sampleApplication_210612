package com.example.swebs_sampleapplication_210612.Fragment.MakeAccountFragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.Activity.MakeAccountActivity;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMakeAccountUserInfoBinding;

public class MakeAccountFragment_userInfo extends Fragment {

    private FragmentMakeAccountUserInfoBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMakeAccountUserInfoBinding.inflate(inflater,container,false);

        binding.btnMakeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MakeAccountActivity)requireActivity()).moveFragment(new MakeAccountFragment_success());
            }
        });

        // 성별 설정 버튼 이벤트
        binding.btnGenderFemale.setOnClickListener(v -> {
            binding.btnGenderFemale.setSelected(true);
            binding.textViewMakeAccountGenderFemale.setTextColor(Color.parseColor("#21CCB2"));
            binding.btnGenderMale.setSelected(false);
            binding.textViewMakeAccountGenderMale.setTextColor(Color.parseColor("#C2C3C7"));
        });
        binding.btnGenderMale.setOnClickListener(v -> {
            binding.btnGenderMale.setSelected(true);
            binding.textViewMakeAccountGenderMale.setTextColor(Color.parseColor("#21CCB2"));
            binding.btnGenderFemale.setSelected(false);
            binding.textViewMakeAccountGenderFemale.setTextColor(Color.parseColor("#C2C3C7"));
        });


        return binding.getRoot();
    }
}