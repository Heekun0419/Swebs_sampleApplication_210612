package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.example.swebs_sampleapplication_210612.Fragment.Information_menu.AppInformationFragment;
import com.example.swebs_sampleapplication_210612.Fragment.Information_menu.FAQFragment;
import com.example.swebs_sampleapplication_210612.Fragment.Information_menu.ManualFragment;
import com.example.swebs_sampleapplication_210612.Fragment.Information_menu.ProductRegisterFragment;
import com.example.swebs_sampleapplication_210612.Fragment.Information_menu.PurchaseQuestionFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MakeAccountFragment.TermsFragment;
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
        switch (requestCode) {
            case "manual":
                moveFragment(new ManualFragment(), "사용법안내");
                break;
            case "FAQ":
                moveFragment(new FAQFragment(), "FAQ");
                break;
            case "purchase_question":
                moveFragment(new PurchaseQuestionFragment(), "구매문의");
                break;
            case "app_info":
                moveFragment(new AppInformationFragment(), "어플정보");
                break;
            case "point":
                moveFragment(new PointFragment(), "스웹스 포인트");
                break;
            case "AS":
                moveFragment(new ServiceNotReadyFragment(), "");
                break;
            case "product":
                moveFragment(new ProductRegisterFragment(), "제품등록");
                break;
            case "notice":
                moveFragment(TermsFragment.newInstance(""), "공지사항");
                break;
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