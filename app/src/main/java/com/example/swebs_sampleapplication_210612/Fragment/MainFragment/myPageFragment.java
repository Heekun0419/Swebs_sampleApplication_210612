package com.example.swebs_sampleapplication_210612.Fragment.MainFragment;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

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
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.NumberPickerDialog2;
import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.NumberPickerModel2;
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
        // 레포지토리 설정
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

        // 국가 설정.
        SetUserInfo();

        // 닫기버튼 누르면 튜토리얼 닫힘.
        binding.tutorialMyPage.textViewMyPageTutorialClose.setOnClickListener(v -> {
            binding.tutorialMyPage.getRoot().setVisibility(View.GONE);
            binding.tutorialMyPage.getRoot().setAnimation(fadeOut);
            sPmanager.setMyTutorialExit(true);
        });

        // 닫기버튼 누르면 튜토리얼 닫힘.
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
                    // 공유하기 버튼 실행
                    Intent shareIntent = new Intent(Intent.ACTION_SENDTO);
                    shareIntent.setType("text/plain");
                    String shareText = "스웹스 가입하고 포인트를 받으세요.\n" +
                            "추천인 코드 : " + sPmanager.getUserReferralCode();
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                    startActivity(Intent.createChooser(shareIntent, "스웹스 추천인 코드 공유"));
                }

                @Override
                public void onNegativeClick() {
                    // 추천인 코드 복사
                    ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("스웹스 추천인 코드" , sPmanager.getUserReferralCode());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(requireContext(), "클립보드에 복사 하였습니다.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCloseClick() {
                    // 알아서 dismiss 됨
                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.show();
        });

        // 내 리뷰 버튼 클릭시
        binding.btnMypageMyReview.setOnClickListener(v ->
                Intent_to_Activity("review", new Intent(requireContext(), MyTopMenuActivity.class)));

        // 내 이벤트 버튼 클릭시
        binding.btnMypageMyEvent.setOnClickListener(v ->
                Intent_to_Activity("event", new Intent(requireContext(), MyTopMenuActivity.class)));

        // 내 서베이 버튼 클릭시
        binding.btnMypageMySurvey.setOnClickListener(v ->
                Intent_to_Activity("survey", new Intent(requireContext(), MyTopMenuActivity.class)));

        //출생년도 클릭시
        binding.mypageBirthday.setOnClickListener(v -> {
            // 게스트 일때 다이얼로그 표시
            if(userType.equals("guest")){
                dialogBirthday();
            } else {
                // 게스트 아닐땐 회원정보 수정 표시
                Intent_to_Activity("", new Intent(requireContext(), ModifyUserInfoActivity.class));
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
                    Intent_to_Activity("", new Intent(requireContext(), ModifyUserInfoActivity.class));
                }
            }
        });

        // 국가지역 클릭시
        binding.mypageCountry.setOnClickListener(v -> {
            if (userType.equals("guest")) {
                dialogCountry();
            } else {
                // 게스트 아닐땐 회원정보 수정 표시
                Intent_to_Activity("", new Intent(requireContext(), ModifyUserInfoActivity.class));
            }
        });

        //상단 프로필 사진 클릭시
        binding.mypageImageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원정보 수정
                Intent_to_Activity("", new Intent(requireContext(), ModifyUserInfoActivity.class));
            }
        });

        // Point 정책 자세히 보기
        binding.mypagePoint.setOnClickListener(v -> {
            Intent_to_Activity("point", new Intent(requireContext(), InformationActivity.class));
        });

        // Login Page 이동
        binding.mypageLogin.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            startActivity(intent);
        });

        binding.mypageModifyMyAdress.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AdressModifyActivity.class);
            startActivity(intent);
        });

        return binding.getRoot();
    }

    private void Intent_to_Activity(String extra,  Intent intent){
        intent.putExtra("resultCode",extra);
        startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    private void SetUserInfo() {
        //국가 설정
        switch (country) {
            case "KR":
                binding.mypageCountryTextView.setText("대한민국");
                break;
            case "US":
                binding.mypageCountryTextView.setText("UNITED STATES");
                break;
            case "CN":
                binding.mypageCountryTextView.setText("中國");
                break;
            default:
                binding.mypageCountryTextView.setText("미등록");
                break;
        }

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
      /*  String mailAddress = email.substring(startIndex);*/
        StringBuffer buffer = new StringBuffer();
        buffer.append(s);
        for(int i = 0; i< email.length(); i++)
            buffer.append("*");

       // buffer.append(mailAddress);
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
            case "kakao" : // 카카오 회원
                renderSnsView("카카오",R.drawable.ic_kakao);
                break;
            case "google":
                renderSnsView("구글",R.drawable.ic_google);
                break;
            case "naver":
                renderSnsView("네이버",R.drawable.ic_naver);
                break;
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void renderSnsView(String txt, int drawable){
        binding.mypageSnsImageView.setBackground(requireActivity().getDrawable(drawable));
        binding.mypageSnsTextView.setText(txt);
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
        //테스트용 수정 --> 나중에 이걸로 가져와야댐. binding.linearLayout.setVisibility(View.GONE);
        binding.linearLayout.setVisibility(View.VISIBLE);
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
        binding.mypageSnsTextView.setText("미등록");
        binding.mypageSnsImageView.setVisibility(View.GONE);
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
        binding.mypageSnsImageView.setVisibility(View.GONE);
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
        binding.mypageSnsTextView.setText("미등록");
        binding.mypageSnsImageView.setVisibility(View.GONE);

    }

}