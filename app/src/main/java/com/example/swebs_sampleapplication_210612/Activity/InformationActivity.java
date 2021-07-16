package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.swebs_sampleapplication_210612.Fragment.Information_menu.AppInformationFragment;
import com.example.swebs_sampleapplication_210612.Fragment.Information_menu.FAQFragment;
import com.example.swebs_sampleapplication_210612.Fragment.Information_menu.ManualFragment;
import com.example.swebs_sampleapplication_210612.Fragment.Information_menu.PurchaseQuestionFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreCertifiedFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreEventFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreReviewFragment;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityInfomationBinding;

public class InformationActivity extends AppCompatActivity {

    private ActivityInfomationBinding binding;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInfomationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String requestCode = getIntent().getStringExtra("resultCode");

        manager = getSupportFragmentManager();
        if(requestCode.equals("manual")) {
            binding.textViewInformationActivityName.setText("사용법 안내");
            manager.beginTransaction().add(R.id.frameLayout_information_activity, new ManualFragment()).commit();
        }else if(requestCode.equals("FAQ")){
            binding.textViewInformationActivityName.setText("FAQ");
            manager.beginTransaction().add(R.id.frameLayout_information_activity, new FAQFragment()).commit();
        } else if(requestCode.equals("purchase_question")){
            binding.textViewInformationActivityName.setText("구매문의");
            manager.beginTransaction().add(R.id.frameLayout_information_activity, new PurchaseQuestionFragment()).commit();
        } else if(requestCode.equals("app_info")){
            binding.textViewInformationActivityName.setText("어플정보");
            manager.beginTransaction().add(R.id.frameLayout_information_activity, new AppInformationFragment()).commit();
        }
    }
}