package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment.CertifiedCompanyInfoFragment;
import com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment.SurveyInfoFragment;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityItemClickViewActivtyBinding;

public class ItemClickViewActivty extends AppCompatActivity {

    private ActivityItemClickViewActivtyBinding binding;
    private FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemClickViewActivtyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String requestCode = getIntent().getStringExtra("resultCode");

        manager = getSupportFragmentManager();
        if(requestCode.equals("certified")) {
            manager.beginTransaction().add(R.id.frameLayout_itemClicked_activity, new CertifiedCompanyInfoFragment()).commit();
        } else if(requestCode.equals("survey")){
            manager.beginTransaction().add(R.id.frameLayout_itemClicked_activity, new SurveyInfoFragment()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}