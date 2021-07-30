package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.example.swebs_sampleapplication_210612.Fragment.MakeAccountFragment.MakeAccountFragment_terms;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityMakeAccountBinding;

public class MakeAccountActivity extends AppCompatActivity {

    private ActivityMakeAccountBinding binding;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMakeAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //String requestCode = getIntent().getStringExtra("resultCode");

        // 회원가입 Fragment
        moveFragment(new MakeAccountFragment_terms());

        //뒤로가기
        binding.btnTopMenuBack.setOnClickListener(v -> onBackPressed());
    }

    public void moveFragment(Fragment fragment){
        manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.frame_make_account, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(manager.getBackStackEntryCount() ==0){
            finish();
        }
    }
}