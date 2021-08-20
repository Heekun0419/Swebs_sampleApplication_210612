package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.example.swebs_sampleapplication_210612.Fragment.Information_menu.AppInformationFragment;
import com.example.swebs_sampleapplication_210612.Fragment.Information_menu.FAQFragment;
import com.example.swebs_sampleapplication_210612.Fragment.Information_menu.ManualFragment;
import com.example.swebs_sampleapplication_210612.Fragment.Information_menu.PurchaseQuestionFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreCertifiedFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreEventFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MoreReviewFragment;
import com.example.swebs_sampleapplication_210612.Fragment.PointFragment.PointFragment;
import com.example.swebs_sampleapplication_210612.Fragment.ServiceNotReadyFragment;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityInfomationBinding;

public class InformationActivity extends AppCompatActivity {

    private ActivityInfomationBinding binding;
    private FragmentManager manager;
    String requestCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInfomationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnInformationActivityBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        requestCode = getIntent().getStringExtra("resultCode");

        manager = getSupportFragmentManager();
        if(requestCode.equals("manual")) {
            moveFragment(new ManualFragment(),"사용법안내");
        }else if(requestCode.equals("FAQ")){
            moveFragment(new FAQFragment(),"FAQ");
        } else if(requestCode.equals("purchase_question")){
            moveFragment(new PurchaseQuestionFragment(),"구매문의");
        } else if(requestCode.equals("app_info")){
            moveFragment(new AppInformationFragment(),"어플정보");
        } else if(requestCode.equals("point")){
            moveFragment(new PointFragment(),"스웹스 포인트");
        } else if(requestCode.equals("AS")){
            moveFragment(new ServiceNotReadyFragment(),"");
        } else if(requestCode.equals("product")){

        }

    }
    public void moveFragment(Fragment fragment, String string){
        if(string.equals(getString(R.string.point_advice_0))){
            binding.textViewInformationActivityName.setText(string);
            manager.beginTransaction().setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left)
                    .replace(R.id.frameLayout_information_activity, fragment).addToBackStack(null).commit();
        }else{
            binding.textViewInformationActivityName.setText(string);
            manager.beginTransaction().replace(R.id.frameLayout_information_activity, fragment).addToBackStack(null).commit();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (requestCode.equals("app_info") && manager.getBackStackEntryCount()==1){
            binding.textViewInformationActivityName.setText("어플정보");
        }if (requestCode.equals("point") && manager.getBackStackEntryCount()==1){
            binding.textViewInformationActivityName.setText("스웹스 포인트");
        }
        if(manager.getBackStackEntryCount() ==0){
            finish();
        }
    }
}