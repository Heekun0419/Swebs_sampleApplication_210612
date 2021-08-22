package com.example.swebs_sampleapplication_210612.Fragment.MainFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.Activity.AdressModifyActivity;
import com.example.swebs_sampleapplication_210612.Activity.InformationActivity;
import com.example.swebs_sampleapplication_210612.Activity.LoginActivity;
import com.example.swebs_sampleapplication_210612.Activity.MainActivity;
import com.example.swebs_sampleapplication_210612.Activity.ModifyUserInfoActivity;
import com.example.swebs_sampleapplication_210612.Activity.MyTopMenuActivity;
import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.Data.Room.Swebs.Entity.MyInfo;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.NumberPickerDialog;
import com.example.swebs_sampleapplication_210612.Dialog.NumberPickerDialog2;
import com.example.swebs_sampleapplication_210612.Dialog.NumberPickerModel;
import com.example.swebs_sampleapplication_210612.Dialog.NumberPickerModel2;
import com.example.swebs_sampleapplication_210612.Dialog.RecommendCodeDialog;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMyPageBinding;
import com.example.swebs_sampleapplication_210612.util.UserLoginController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class myPageFragment extends Fragment {
    private FragmentMyPageBinding binding;
    private String country;
    private final Context context;
    private String userType, userBirthDay;
    private SPmanager sPmanager;
    private final Animation fadeOut = new AlphaAnimation(1,0);
    private final Animation fadeIn = new AlphaAnimation(0,1);
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
        RenderMyPageFromUserType(sPmanager.getUserType());
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

        //추천인 코드 클릭시
        binding.mypageBtnRecommendCode.setOnClickListener(v -> {
            RecommendCodeDialog dialog = new RecommendCodeDialog(requireContext(), new DialogClickListener() {
                @Override
                public void onPositiveClick(int position) {

                }

                @Override
                public void onNegativeClick() {

                }

                @Override
                public void onCloseClick() {

                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.show();
        });

        // 내 리뷰 버튼 클릭시
        binding.btnMypageMyReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), MyTopMenuActivity.class);
                intent.putExtra("resultCode","review");
                startActivity(intent);
            }
        });

        //출생년도 클릭시
        binding.mypageBirthday.setOnClickListener(v -> {
            // 게스트 일때 다이얼로그 표시
            if(userType.equals("guest")){
                dialogBirthday();
            } else {
                // 게스트 아닐땐 회원정보 수정 표시
                Intent modify_userInfo_intent = new Intent(requireContext(), ModifyUserInfoActivity.class);
                startActivity(modify_userInfo_intent);
            }

        });


        // 성별 클릭시
        binding.mypageGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userType.equals("guest")){
                    dialogGender();
                }else{
                    // 게스트 아닐땐 회원정보 수정 표시
                    Intent modify_userInfo_intent = new Intent(requireContext(), ModifyUserInfoActivity.class);
                    startActivity(modify_userInfo_intent);
                }
            }
        });

        // 국가지역 클릭시
        binding.mypageCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userType.equals("guest")) {
                    dialogCountry();
                } else {
                    // 게스트 아닐땐 회원정보 수정 표시
                    Intent modify_userInfo_intent = new Intent(requireContext(), ModifyUserInfoActivity.class);
                    startActivity(modify_userInfo_intent);
                }
            }
        });

        // Point 정책 자세히 보기
        binding.mypagePoint.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), InformationActivity.class);
            intent.putExtra("resultCode","point");
            startActivity(intent);
        });

        // Login Page 이동
        binding.mypageLogin.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            startActivity(intent);
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

    private void SetUserInfo() {
        //국가 설정
        if(country.equals("KR")) {
            binding.mypageCountryTextView.setText("대한민국");
        }else if(country.equals("US")){
            binding.mypageCountryTextView.setText("United States");
        }else {
            binding.mypageCountryTextView.setText("中國");
        }

        binding.mypageCountryTextView.setText("미등록");
    }

    @Override
    public void onResume() {
        super.onResume();
        SetUserInfo();

        setTutorial();

        // Data Observe -- START
        myInfoRepository.getValueToLiveData("userSrl").observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s == null)
                    new UserLoginController(requireActivity().getApplication()).signUpForGuest();
            }
        });

        // userType
        myInfoRepository.getValueToLiveData("userType").observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null)
                    RenderMyPageFromUserType(s);
                userType = s;
            }
        });

        // Email
        myInfoRepository.getValueToLiveData("email").observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null)
                    setEmail(s);
            }
        });

        // Name
        myInfoRepository.getValueToLiveData("name").observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    String viewText = s + " 님";
                    binding.mypageProfileName.setText(viewText);
                    binding.mypageTextViewName.setText(s);
                }
            }
        });

        // Point
        myInfoRepository.getValueToLiveData("point").observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    String viewText = s + " P";
                    binding.mypagePointText.setText(viewText);
                }
            }
        });

        // Birthday
        myInfoRepository.getValueToLiveData("birthday").observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                String viewText;
                if (s != null) {
                    viewText = s.length() > 4 ? s.substring(0, 4) : s;
                    viewText += "년";
                    binding.mypageBirthdayTextview.setTextColor(Color.parseColor("#3E3A39"));
                } else {
                    viewText = "미등록";
                }
                binding.mypageBirthdayTextview.setText(viewText);
            }
        });

        // Gender
        myInfoRepository.getValueToLiveData("gender").observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                renderGenderView(s);
            }
        });

        // country
        myInfoRepository.getValueToLiveData("country").observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                String viewText;
                if (s != null) {
                    viewText = countryCodeToName(s);
                    binding.mypageCountryTextView.setTextColor(Color.parseColor("#3E3A39"));
                } else {
                    viewText = "미등록";
                }
                binding.mypageCountryTextView.setText(viewText);
            }
        });
        // Data observe -- END
    }

    private String countryCodeToName(String countryCode) {
        return new Locale("", countryCode).getDisplayCountry();
    }

    private String countryNameToCode(String countryName) {
        Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
            if (!locale.getCountry().equals("")
            && locale.getDisplayCountry().equals(countryName)) {
                return locale.getCountry();
            }
        }

        return null;
    }

    private void dialogBirthday() {
        Calendar cal = Calendar.getInstance();

        List<String> inputData = new ArrayList<>();
        for (int i=1900; i<=cal.get(Calendar.YEAR); i++) {
            inputData.add(String.valueOf(i));
        }

        // 기본값
        int defaultValue;
        if (binding.mypageBirthdayTextview.getText().toString().equals("미등록"))
            defaultValue = 95;
        else
            defaultValue = Integer.parseInt(binding.mypageBirthdayTextview.getText().toString().replaceAll("[^0-9]", "")) - 1900;

        NumberPickerDialog2 dialog2 = new NumberPickerDialog2(
                requireContext(),
                new NumberPickerModel2(
                        "출생년도",
                        inputData,
                        defaultValue,
                        "확인",
                        "취소"
                ),
                new DialogClickListener() {
                    @Override
                    public void onPositiveClick(int position) {
                        myInfoRepository.insertMyInfo("birthday", inputData.get(position));
                    }

                    @Override
                    public void onNegativeClick() {

                    }

                    @Override
                    public void onCloseClick() {

                    }
                }
        );
        dialog2.setCancelable(false);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog2.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog2.show();
    }


    private void dialogGender() {
        List<String> inputData = new ArrayList<>();
        inputData.add("남자");
        inputData.add("여자");

        int defaultValue = 0;
        if (binding.mypageGenderTextView.getText().toString().equals("여자"))
            defaultValue = 1;


        NumberPickerDialog2 dialog = new NumberPickerDialog2(
                requireContext(),
                new NumberPickerModel2("성별",
                        inputData,
                        defaultValue,
                        "확인",
                        "취소"
                ),
                new DialogClickListener() {
                    @Override
                    public void onPositiveClick(int position) {
                        if (position == 0)
                            myInfoRepository.insertMyInfo("gender", "male");
                        else
                            myInfoRepository.insertMyInfo("gender", "female");
                    }

                    @Override
                    public void onNegativeClick() {

                    }

                    @Override
                    public void onCloseClick() {

                    }
                }
        );
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
    }

    private void dialogCountry() {
        int defaultValue = 1;
        List<String> inputData = new ArrayList<>();

        Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
            if (!locale.getCountry().equals("")) {
                if (!inputData.contains(locale.getDisplayCountry())) {
                    inputData.add(locale.getDisplayCountry());
                }
            }
        }
        Collections.sort(inputData);

        // 기본 선택 구하기.
        int countryCount = 0;
        for (String data : inputData) {
            if (binding.mypageCountryTextView.getText().toString().equals(data))
                defaultValue = countryCount;
            else if (binding.mypageCountryTextView.getText().toString().equals("미등록")
                    && country.equals(data)) {
                defaultValue = countryCount;
            }

            countryCount++;
        }

        NumberPickerDialog2 dialog = new NumberPickerDialog2(
                requireContext(),
                new NumberPickerModel2(
                        "국가지역",
                        inputData,
                        defaultValue,
                        "확인",
                        "취소"
                ),
                new DialogClickListener() {
                    @Override
                    public void onPositiveClick(int position) {
                        myInfoRepository.insertMyInfo("country", countryNameToCode(inputData.get(position)));
                    }

                    @Override
                    public void onNegativeClick() {

                    }

                    @Override
                    public void onCloseClick() {

                    }
                }
        );
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();

    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTutorial(){
        if (binding.tutorialMyPage.getRoot().getVisibility() == View.GONE
                && !sPmanager.getMyTutorialExit()) {
            fadeIn.setDuration(700);
            binding.tutorialMyPage.getRoot().setOnTouchListener((v, event) -> true);
            binding.tutorialMyPage.getRoot().setVisibility(View.VISIBLE);
            binding.tutorialMyPage.getRoot().setAnimation(fadeIn);
        }
    }

    private void setEmail(String email){
        char s = email.charAt(0);
        int startIndex = email.indexOf("@");
        String mailAddress = email.substring(startIndex);
        StringBuffer buffer = new StringBuffer();
        buffer.append(s);
        for(int i = 0; i< startIndex; i++)
            buffer.append("*");

        buffer.append(mailAddress);
        binding.mypageTextViewID.setText(buffer);
    }

    private void RenderMyPageFromUserType(String userType){
        switch (userType) {
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

    private void renderGenderView(String gender) {
        if (gender == null) {
            binding.mypageImageViewGender.setVisibility(View.GONE);
            binding.mypageGenderTextView.setText("미등록");
        } else if (gender.equals("male")) {
            binding.mypageGenderTextView.setText("남자");
            binding.mypageGenderTextView.setTextColor(Color.parseColor("#3E3A39"));
            binding.mypageImageViewGender.setVisibility(View.VISIBLE);
            binding.mypageImageViewGender.setImageResource(R.drawable.ic_male);
        } else if (gender.equals("female")) {
            binding.mypageGenderTextView.setText("여자");
            binding.mypageGenderTextView.setTextColor(Color.parseColor("#3E3A39"));
            binding.mypageImageViewGender.setVisibility(View.VISIBLE);
            binding.mypageImageViewGender.setImageResource(R.drawable.ic_female);
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


        binding.mypageLogin.setVisibility(View.VISIBLE);
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