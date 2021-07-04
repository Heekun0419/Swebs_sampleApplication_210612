package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.swebs_sampleapplication_210612.Fragment.MoreCertifiedFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreEventFragment;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityTopMenuBinding;

public class TopMenuActivity extends AppCompatActivity {

    private ActivityTopMenuBinding binding;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTopMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // intent 넘길때 result code 받아와야함.
        String requestCode = getIntent().getStringExtra("resultCode");

        if(requestCode.equals("event")) {
            binding.textViewTopMenuName.setText("이벤트");
            manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.frameLayout_more_top, new MoreEventFragment()).commit();
        }else if(requestCode.equals("certified")){
            binding.textViewTopMenuName.setText("인증업체");
            manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.frameLayout_more_top, new MoreCertifiedFragment()).commit();
        }



    }
}