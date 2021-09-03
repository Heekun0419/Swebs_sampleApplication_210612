package com.example.swebs_sampleapplication_210612.Activity.Login_Signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.swebs_sampleapplication_210612.Fragment.MakeAccountFragment.MakeAccountFragment_terms;
import com.example.swebs_sampleapplication_210612.Fragment.MakeAccountFragment.MakeSnsAccountFragment_terms;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityMakeSnsaccountBinding;

public class MakeSNSAccountActivity extends AppCompatActivity {
    private ActivityMakeSnsaccountBinding binding;
    private FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMakeSnsaccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        manager = getSupportFragmentManager();

        // 회원가입 Fragment
        moveFragment(new MakeSnsAccountFragment_terms());

        //뒤로가기
        binding.btnTopMenuBack.setOnClickListener(v -> onBackPressed());
    }

    private void moveFragment(Fragment fragment){
        manager.beginTransaction().replace(R.id.frame_make_account_sns, fragment).addToBackStack(null).commit();
    }
}