package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.swebs_sampleapplication_210612.Fragment.MakeAccountFragment.FindPassFragment;
import com.example.swebs_sampleapplication_210612.Fragment.MakeAccountFragment.MakeAccountFragment_terms;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityMakeAccountBinding;

public class MakeAccountActivity extends AppCompatActivity {

    private ActivityMakeAccountBinding binding;
    private FragmentManager manager;

    private boolean isLoadingView;
    private String referralCode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMakeAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String requestCode = getIntent().getStringExtra("resultCode");
        manager = getSupportFragmentManager();

        if(requestCode.equals("findPass")){
            binding.textViewTopMenuName.setText("비밀번호 찾기");
            moveFragment(new FindPassFragment(),"");
        }else if(requestCode.equals("makeAccount")){
            // 회원가입 Fragment
            moveFragment(new MakeAccountFragment_terms(),"");
        }

        //뒤로가기
        binding.btnTopMenuBack.setOnClickListener(v -> onBackPressed());

        // 로딩 페이지가 보일시
        isLoadingView = false;
    }

    @Override
    public void onBackPressed() {
        if (manager.getBackStackEntryCount() <= 1)
            finish();

        if (!isLoadingView)
            super.onBackPressed();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void renderLoading(boolean isView) {
        if (isView) {
            binding.loadingView.getRoot().setOnTouchListener((v, event) -> true);
            binding.loadingView.getRoot().setVisibility(View.VISIBLE);
            isLoadingView = true;
        } else {
            binding.loadingView.getRoot().setVisibility(View.GONE);
            isLoadingView = false;
        }
    }

    public void moveFragment(Fragment fragment, String code){
        // 키보드 숨기기 에러 발생... 수정요망
      //  InputMethodManager keyboardManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
      //  keyboardManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        // success 프라그먼트 이동시 애니매이션 추가
        if(code.equals("success")) {
            manager.beginTransaction().setCustomAnimations(R.anim.slide_up,R.anim.slide_out_up)
                    .replace(R.id.frame_make_account,fragment).addToBackStack(null).commit();
        } else {
            manager.beginTransaction().replace(R.id.frame_make_account, fragment).addToBackStack(null).commit();
        }

    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getReferralCode() {
        return referralCode;
    }
}