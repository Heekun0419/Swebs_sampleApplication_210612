package com.example.swebs_sampleapplication_210612.Fragment.MainFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.example.swebs_sampleapplication_210612.Activity.AdressModifyActivity;
import com.example.swebs_sampleapplication_210612.Activity.InformationActivity;
import com.example.swebs_sampleapplication_210612.Activity.LoginActivity;
import com.example.swebs_sampleapplication_210612.Activity.MainActivity;
import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.Data.Room.Swebs.Entity.MyInfo;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMyPageBinding;

import java.util.List;
import java.util.Locale;

public class myPageFragment extends Fragment {

    private FragmentMyPageBinding binding;
    private String country;
    private final Context context;
    private SPmanager sPmanager;
    private Animation fadeOut = new AlphaAnimation(1,0);
    private Animation fadeIn = new AlphaAnimation(0,1);

    private MyInfoRepository myInfoRepository;

    public myPageFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //국가코드 가져오기
        Locale locale;
        locale = requireContext().getResources().getConfiguration().getLocales().get(0);
        country = locale.getCountry();

        myInfoRepository = new MyInfoRepository(requireActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyPageBinding.inflate(inflater,container,false);
        // user type 검사해서 View 변환
        sPmanager = new SPmanager(context);
        binding.tutorialMyPage.getRoot().setVisibility(View.GONE);

        Check_userType();
        SetUserInfo();
        // 닫기버튼 누르면 튜토리얼 닫힘.
        binding.tutorialMyPage.textViewMyPageTutorialClose.setOnClickListener(v -> {
            binding.tutorialMyPage.getRoot().setVisibility(View.GONE);
            binding.tutorialMyPage.getRoot().setAnimation(fadeOut);
            sPmanager.setMyTutorialExit(true);
        });

        binding.tutorialMyPage.imageButton5.setOnClickListener(v -> {
            binding.tutorialMyPage.getRoot().setVisibility(View.GONE);
            binding.tutorialMyPage.getRoot().setAnimation(fadeOut);
            sPmanager.setMyTutorialExit(true);
        });

        // 네비게이션 드로어 열기
        binding.includedAppbarMy.imageButton.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).drawer.openDrawer(GravityCompat.START);
        });

        // APP bar 로고 안보이기
        binding.includedAppbarMy.imageView19.setVisibility(View.INVISIBLE);

        // 바텀시트 열기
        binding.includedAppbarMy.imageButton2.setOnClickListener(v ->
                ((MainActivity) requireActivity()).BottomSheetOpen()
        );

        // Point 정책 자세히 보기
        binding.mypagePoint.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), InformationActivity.class);
            intent.putExtra("resultCode","point");
            startActivity(intent);
        });

        // Login Page 이동
        binding.mypageLogin.setOnClickListener(v -> {
            myInfoRepository.insertMyInfo("data123", "gfdgdf");
            //Intent intent = new Intent(requireContext(), LoginActivity.class);
            //startActivity(intent);
        });

        binding.mypageModifyMyAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), AdressModifyActivity.class);
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }

    private void SetUserInfo(){
        //국가 설정
        if(country.equals("KR")) {
          binding.mypageCountryTextView.setText("대한민국");
        }else if(country.equals("US")){
          binding.mypageCountryTextView.setText("United States");
        }else {
            binding.mypageCountryTextView.setText("中國");
        }

        //이름 설정
        //binding.mypageProfileName.setText(sPmanager.getUserName() + " 님");
        binding.mypageTextViewName.setText(sPmanager.getUserName());
        //email
        setEmail();
        // 생일설정
        binding.mypageBirthdayTextview.setText(sPmanager.getUserBirth());
        // 포인트 설정
        //binding.mypagePointText.setText(sPmanager.getUserPoint() + " P");
        //성별
        Check_gender();
    }


    @Override
    public void onResume() {
        super.onResume();
        Check_userType();
        SetUserInfo();
        if(sPmanager.getMyTutorialExit()){
            fadeOut.setDuration(300);
            binding.tutorialMyPage.getRoot().setVisibility(View.GONE);
            binding.tutorialMyPage.getRoot().setAnimation(fadeOut);
        }else{
            fadeIn.setDuration(500);
            binding.tutorialMyPage.getRoot().setVisibility(View.VISIBLE);
            binding.tutorialMyPage.getRoot().setAnimation(fadeIn);
        }


        // Data Observe -- START
        // Name
        myInfoRepository.swebsDao.getValueLiveDataForMyInfo("name").observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                String viewText = s + " 님";
                binding.mypageProfileName.setText(viewText);
            }
        });

        // Point
        myInfoRepository.swebsDao.getValueLiveDataForMyInfo("point").observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                String viewText = s + " P";
                binding.mypagePointText.setText(viewText);
            }
        });

        // Data observe -- END
    }

    private void setEmail(){
        String content = sPmanager.getUserEmail();
        char s = content.charAt(0);

      /*  int startIndex = content.indexOf("@");
        String mailAddress = content.substring(startIndex);
       */

        StringBuffer buffer = new StringBuffer();
        buffer.append(s);
        for(int i =0; i<content.length()-1;i++)
            buffer.append("*");

        binding.mypageTextViewID.setText(buffer);
    }

    private void Check_userType(){
        switch (sPmanager.getUserType()) {
            case "guest":  // 게스트
                setVisible_of_Guest();
                break;
            case "normal":  // 일반유저
                setVisible_of_NormalUser();
                break;
            case "company":  // 기업회원
                setVisible_of_CompanyUser();
                break;
        }
    }

    private void Check_gender(){
        switch (sPmanager.getUserGender()){
            case "male": // 남자
                binding.mypageGenderTextView.setText("남자");
                binding.mypageImageViewGender.setVisibility(View.VISIBLE);
                binding.mypageImageViewGender.setImageResource(R.drawable.ic_male);
                break;
            case "female":// 여자
                binding.mypageGenderTextView.setText("여자");
                binding.mypageImageViewGender.setVisibility(View.VISIBLE);
                binding.mypageImageViewGender.setImageResource(R.drawable.ic_female);
            case "미등록":
                binding.mypageImageViewGender.setVisibility(View.GONE);
                binding.mypageGenderTextView.setText("미등록");
        }
    }


    private void setVisible_of_Guest(){
        // 프로필 이미지, 기업 아이콘, 추천인코드
        binding.mypageCompanyIcon.setVisibility(View.GONE);
        binding.mypageBtnRecommendCode.setVisibility(View.GONE);
        binding.mypageImageProfile.setVisibility(View.GONE);
        // 버튼 3개
        binding.linearLayout.setVisibility(View.GONE);
        //아이디, 실명
        binding.mypageId.setVisibility(View.GONE);
        binding.mypageName.setVisibility(View.GONE);
        // 구분선 및 이벤트 주소변경 탭
        binding.mypageModifyMyAdress.setVisibility(View.GONE);
        binding.divider1.setVisibility(View.GONE);
        binding.divider2.setVisibility(View.GONE);
        binding.divider3.setVisibility(View.GONE);
        binding.divider4.setVisibility(View.GONE);

        //기업계정, SNS
        binding.mypageCompanyAccount.setVisibility(View.GONE);
        binding.mypageCompanyAccount2.setVisibility(View.GONE);
        binding.mypageSnsAccount.setVisibility(View.GONE);

    }

    private void setVisible_of_NormalUser(){
        // 기업계정 아이콘
        binding.mypageCompanyIcon.setVisibility(View.GONE);
        // 로그인 버튼
        binding.mypageLogin.setVisibility(View.GONE);
        //기업 계정 정보
        binding.mypageCompanyAccount2.setVisibility(View.GONE);

       // binding.divider5.setVisibility(View.GONE);

        binding.mypageBtnRecommendCode.setVisibility(View.VISIBLE);
        binding.mypageImageProfile.setVisibility(View.VISIBLE);
        // 버튼 3개
        binding.linearLayout.setVisibility(View.VISIBLE);
        //아이디, 실명
        binding.mypageId.setVisibility(View.VISIBLE);
        binding.mypageName.setVisibility(View.VISIBLE);
        // 구분선 및 이벤트 주소변경 탭
        binding.mypageModifyMyAdress.setVisibility(View.VISIBLE);
        binding.divider1.setVisibility(View.VISIBLE);
        binding.divider2.setVisibility(View.VISIBLE);
        binding.divider3.setVisibility(View.VISIBLE);
        binding.divider4.setVisibility(View.VISIBLE);

        //기업계정, SNS
        binding.mypageCompanyAccount.setVisibility(View.VISIBLE);
        binding.mypageCompanyAccountApprove.setText("미등록");
        binding.mypageSnsAccount.setVisibility(View.VISIBLE);

    }

    private void setVisible_of_CompanyUser(){

        // 기업계정 아이콘
        binding.mypageCompanyIcon.setVisibility(View.VISIBLE);
        // 로그인 버튼
        binding.mypageLogin.setVisibility(View.GONE);
        //기업 계정 정보
        binding.mypageCompanyAccount2.setVisibility(View.VISIBLE);

        binding.mypageBtnRecommendCode.setVisibility(View.VISIBLE);
        binding.mypageImageProfile.setVisibility(View.VISIBLE);
        // 버튼 3개
        binding.linearLayout.setVisibility(View.VISIBLE);
        //아이디, 실명
        binding.mypageId.setVisibility(View.VISIBLE);
        binding.mypageName.setVisibility(View.VISIBLE);
        // 구분선 및 이벤트 주소변경 탭
        binding.mypageModifyMyAdress.setVisibility(View.VISIBLE);
        binding.divider1.setVisibility(View.VISIBLE);
        binding.divider2.setVisibility(View.VISIBLE);
        binding.divider3.setVisibility(View.VISIBLE);
        binding.divider4.setVisibility(View.VISIBLE);

        //기업계정, SNS
        binding.mypageCompanyAccount.setVisibility(View.VISIBLE);
        binding.mypageSnsAccount.setVisibility(View.VISIBLE);

    }

}