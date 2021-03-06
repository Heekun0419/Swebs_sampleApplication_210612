package com.example.swebs_sampleapplication_210612.Fragment.MainFragment;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Activity.AdressModifyActivity;
import com.example.swebs_sampleapplication_210612.Activity.InformationActivity;
import com.example.swebs_sampleapplication_210612.Activity.Login_Signup.LoginActivity;
import com.example.swebs_sampleapplication_210612.Activity.MainActivity;
import com.example.swebs_sampleapplication_210612.Activity.ModifyUserInfoActivity;
import com.example.swebs_sampleapplication_210612.Activity.TopMenuActivity.MyTopMenuActivity;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Listener.netSignupListener;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickStringListener;
import com.example.swebs_sampleapplication_210612.Dialog.EditTextDialog;
import com.example.swebs_sampleapplication_210612.Dialog.InputPasswordDialog;
import com.example.swebs_sampleapplication_210612.Dialog.NumberPickerDialog2;
import com.example.swebs_sampleapplication_210612.Dialog.OneButtonBasicDialog;
import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.BasicDialogTextModel;
import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.NumberPickerModel2;
import com.example.swebs_sampleapplication_210612.Dialog.RecommendCodeDialog;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.ViewModel.MyInfoViewModel;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMyPageBinding;
import com.example.swebs_sampleapplication_210612.util.UserLoginController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class myPageFragment extends Fragment {
    private final String DIALOG_TITLE = "?????? ?????????";
    private FragmentMyPageBinding binding;
    private SPmanager sPmanager;
    private final Animation fadeOut = new AlphaAnimation(1,0);
    private final Animation fadeIn = new AlphaAnimation(0,1);

    private String country;
    private String userType;
    private String email;

    private MyInfoViewModel viewModel;

    private long mLastClickTime;

    public myPageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //???????????? ????????????
        Locale locale;
        locale = requireContext().getResources().getConfiguration().getLocales().get(0);
        country = locale.getCountry();

        // ?????? ?????????.
        mLastClickTime = SystemClock.elapsedRealtime();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyPageBinding.inflate(inflater,container,false);

        // ????????? ??????
        viewModel = new ViewModelProvider(this).get(MyInfoViewModel.class);

        // user type ???????????? View ??????
        sPmanager = new SPmanager(requireContext());
        binding.tutorialMyPage.getRoot().setVisibility(View.GONE);
        RenderMyPageFromUserType(sPmanager.getUserType());

        // ???????????? ????????? ???????????? ??????.
        binding.tutorialMyPage.textViewMyPageTutorialClose.setOnClickListener(v -> {
            binding.tutorialMyPage.getRoot().setVisibility(View.GONE);
            binding.tutorialMyPage.getRoot().setAnimation(fadeOut);
            sPmanager.setMyTutorialExit(true);
        });

        // ???????????? ????????? ???????????? ??????.
        binding.tutorialMyPage.imageButton5.setOnClickListener(v -> {
            binding.tutorialMyPage.getRoot().setVisibility(View.GONE);
            binding.tutorialMyPage.getRoot().setAnimation(fadeOut);
            sPmanager.setMyTutorialExit(true);
        });

        // ??????????????? ????????? ??????
        binding.includedAppbarMy.imageButton.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).openDrawer();
        });

        // APP bar ?????? ????????????
        binding.includedAppbarMy.imageView19.setVisibility(View.INVISIBLE);

        // ???????????? ??????
        binding.includedAppbarMy.imageButton2.setOnClickListener(v ->
                ((MainActivity) requireActivity()).BottomSheetOpen()
        );

        //????????? ?????? ?????????
        binding.mypageBtnRecommendCode.setOnClickListener(v -> {
            RecommendCodeDialog dialog = new RecommendCodeDialog(requireContext(), new DialogClickListener() {
                @Override
                public void onPositiveClick(int position) {
                    // ???????????? ?????? ??????
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    String shareText = getString(R.string.recommend_code_title_1) + sPmanager.getUserReferralCode()+ getString(R.string.recommend_code_title_2);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                    startActivity(Intent.createChooser(shareIntent, "????????? ????????? ?????? ??????"));
                }

                @Override
                public void onNegativeClick() {
                    // ????????? ?????? ??????
                    ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("????????? ????????? ??????" , sPmanager.getUserReferralCode());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(requireContext(), "??????????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCloseClick() {
                    // ????????? dismiss ???
                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.show();
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Toast.makeText(requireContext(), "?????????", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // ??? ?????? ?????? ?????????
        binding.btnMypageMyReview.setOnClickListener(v ->
                Intent_to_Activity("my_review", new Intent(requireContext(), MyTopMenuActivity.class)));

        // ??? ????????? ?????? ?????????
        binding.btnMypageMyEvent.setOnClickListener(v ->
                Intent_to_Activity("my_event", new Intent(requireContext(), MyTopMenuActivity.class)));

        // ??? ????????? ?????? ?????????
        binding.btnMypageMySurvey.setOnClickListener(v ->
                Intent_to_Activity("my_survey", new Intent(requireContext(), MyTopMenuActivity.class)));


        //???????????? ?????????
        binding.mypageBirthday.setOnClickListener(v -> {
            // ????????? ?????? ??????????????? ??????
            if(userType.equals("guest")){
                if (clickEnableFromLastClick(500))
                    dialogBirthday();

            } else {
                // ????????? ????????? ???????????? ?????? ??????
                dialogInputPass();
            }
        });

        // ?????? ?????????
        binding.mypageGender.setOnClickListener(v -> {
            if(userType.equals("guest")) {
                if (clickEnableFromLastClick(500))
                    dialogGender();
            }else{
                // ????????? ????????? ???????????? ?????? ??????
                dialogInputPass();
            }
        });

        // ????????? ?????????
        binding.mypageNickname.setOnClickListener(v -> {
            if(userType.equals("guest")){
                if (clickEnableFromLastClick(500))
                    dialogNickname();
            }else{
                // ????????? ????????? ???????????? ?????? ??????
                dialogInputPass();
            }
        });

        // ???????????? ?????????
        binding.mypageCountry.setOnClickListener(v -> {
            if (userType.equals("guest")) {
                if (clickEnableFromLastClick(500))
                    dialogCountry();
            } else {
                // ????????? ????????? ???????????? ?????? ??????
                dialogInputPass();
            }
        });

        //?????? ????????? ?????? ?????????
        binding.mypageImageProfile.setOnClickListener(v -> {
            // ???????????? ??????
            dialogInputPass();
        });

        // Point ?????? ????????? ??????
        binding.mypagePoint.setOnClickListener(v -> {
            Intent_to_Activity("point", new Intent(requireContext(), InformationActivity.class));
        });

        // Login Page ??????
        binding.mypageLogin.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            startActivity(intent);
        });

        binding.mypageModifyMyAdress.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AdressModifyActivity.class);
            startActivity(intent);
        });

        observerStart();

        return binding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        setTutorial();
    }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void observerStart() {
        // Data Observe -- START
        viewModel.getUserInfoFromKey("userSrl").observe(getViewLifecycleOwner(), s -> {
            Log.d("login", "???????????????1 : " + s);
            if (s == null) {
                Log.d("login", "???????????????2 : " + s);
                new UserLoginController(requireActivity().getApplication(), new netSignupListener() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailed() {

                    }

                    @Override
                    public void onServerError() {

                    }
                }).signUpForGuest();
            }
        });

        // userType
        viewModel.getUserInfoFromKey("userType").observe(getViewLifecycleOwner(), s -> {
            Log.d("login", "change : " + s);
            if (s != null)
                RenderMyPageFromUserType(s);
            userType = s;
        });

        // Email
        viewModel.getUserInfoFromKey("email").observe(getViewLifecycleOwner(), s -> {
            if (s != null) {
                binding.mypageTextViewID.setText(s);
                email = s;
            }
        });

        // ????????? ????????? ??????
        viewModel.getUserInfoFromKey("profileSrl").observe(getViewLifecycleOwner(), s -> {
            Log.d("test_", "profileSrl : " + s);
            if (s != null && !s.equals("0"))
                GlideImage(binding.mypageImageProfile, getImageViewUrl(s, "200"));
        });

        // Name
        viewModel.getUserInfoFromKey("name").observe(getViewLifecycleOwner(), s -> {
            if (s != null) {
                String viewText = s + " ???";
                binding.mypageTextViewName.setText(s);
            }
        });

        // Point
        viewModel.getUserInfoFromKey("point").observe(getViewLifecycleOwner(), s -> {
            if (s != null) {
                String viewText = s + " P";
                binding.mypagePointText.setText(viewText);
            }
        });

        // nickName
        viewModel.getUserInfoFromKey("nickName").observe(getViewLifecycleOwner(), s -> {
            binding.mypageTextViewNickname.setTextColor(Color.parseColor("#3E3A39"));
            binding.mypageTextViewNickname.setText(s);
            binding.mypageProfileName.setText(s + " ???");
        });

        // Birthday
        viewModel.getUserInfoFromKey("birthday").observe(getViewLifecycleOwner(), s -> {
            String viewText = "?????????";
            if (s != null) {
                viewText = s.length() > 4 ? s.substring(0, 4) : s;
                binding.mypageBirthdayTextview.setTextColor(Color.parseColor("#3E3A39"));
            }

            viewText = (viewText.contains("?????????")) ? viewText : viewText + "???";
            binding.mypageBirthdayTextview.setText(viewText);
        });

        // Gender
        viewModel.getUserInfoFromKey("gender").observe(getViewLifecycleOwner(), this::renderGenderView);

        // country
        viewModel.getUserInfoFromKey("country").observe(getViewLifecycleOwner(), s -> {
            String viewText = "?????????";
            if (s != null) {
                viewText = countryCodeToName(s);
                binding.mypageCountryTextView.setTextColor(Color.parseColor("#3E3A39"));
            }
            binding.mypageCountryTextView.setText(viewText);
        });

        // ???????????? ?????????...
        viewModel.getProgressResult().observe(getViewLifecycleOwner(), s -> {
            if (s != null) {
                switch (s) {
                    case "loginSuccess":
                        Intent_to_Activity("", new Intent(requireContext(), ModifyUserInfoActivity.class));
                        break;
                    case "loginFailed":
                        showOneButtonDialog(DIALOG_TITLE, "??????????????? ?????? ????????????.");
                        break;
                    case "serverError":
                        showOneButtonDialog(DIALOG_TITLE, "?????? ????????? ???????????? ????????????.\n?????? ??? ?????? ?????? ????????????.");
                        break;
                }
            }
        });

        // ?????? ??????
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean != null) {
                if (aBoolean)
                    binding.loadingView.getRoot().setOnTouchListener((v, event) -> true);
                binding.loadingView.getRoot().setVisibility(aBoolean ? View.VISIBLE : View.GONE);
            }
        });

        // Data observe -- END
    }

    private void Intent_to_Activity(String extra,  Intent intent){
        intent.putExtra("resultCode",extra);
        startActivity(intent);
    }

    private boolean clickEnableFromLastClick(int limitTime) {
        if (SystemClock.elapsedRealtime() - mLastClickTime > limitTime) {
            mLastClickTime = SystemClock.elapsedRealtime();
            return true;
        }
        return false;
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

    private void dialogInputPass() {
        if (sPmanager.getUserType().equals("normal")) {
            InputPasswordDialog dialog = new InputPasswordDialog(requireContext(), new DialogClickStringListener() {
                @Override
                public void onPositiveClick(String string) {
                    if (string != null && !string.equals(""))
                        viewModel.loginForNormal(email, string);
                }

                @Override
                public void onNegativeClick() {

                }

                @Override
                public void onCloseClick() {

                }
            });
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.show();
            dialog.setOnCancelListener(dialog1 -> {
                Toast.makeText(requireContext(), "?????????", Toast.LENGTH_SHORT).show();
            });
            return;
        }

        Intent_to_Activity("", new Intent(requireContext(), ModifyUserInfoActivity.class));
    }

    private void dialogBirthday() {
        Calendar cal = Calendar.getInstance();

        List<String> inputData = new ArrayList<>();
        for (int i=1900; i<=cal.get(Calendar.YEAR); i++) {
            inputData.add(String.valueOf(i));
        }

        // ?????????
        int defaultValue;
        if (binding.mypageBirthdayTextview.getText().toString().equals("?????????"))
            defaultValue = 95;
        else {
            String yearValue = binding.mypageBirthdayTextview.getText().toString();
            yearValue = yearValue.length() > 4 ? yearValue.substring(0, 4) : yearValue;
            defaultValue = Integer.parseInt(yearValue.replaceAll("[^0-9]", "")) - 1900;
        }

        NumberPickerDialog2 dialog2 = new NumberPickerDialog2(
                requireContext(),
                new NumberPickerModel2(
                        "????????????",
                        inputData,
                        defaultValue,
                        "??????",
                        "??????"
                ),
                new DialogClickListener() {
                    @Override
                    public void onPositiveClick(int position) {
                        viewModel.guestUserConfigModify(
                                null,
                                inputData.get(position),
                                null,
                                null
                        );
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

    private void dialogNickname() {
        EditTextDialog EditDialog = new EditTextDialog(requireContext(), new BasicDialogTextModel(
                "?????????",
                "???????????? ??????????????????",
                "??????",
                "??????"), new DialogClickStringListener() {
            @Override
            public void onPositiveClick(String string) {
                viewModel.guestUserConfigModify(
                        string,
                        null,
                        null,
                        null
                );
            }

            @Override
            public void onNegativeClick() {

            }

            @Override
            public void onCloseClick() {

            }
        });
        EditDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        EditDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        EditDialog.show();
        EditDialog.setOnCancelListener(dialog ->
                Toast.makeText(requireContext(), "?????????", Toast.LENGTH_SHORT).show());

    }

    private void dialogGender() {
        List<String> inputData = new ArrayList<>();
        inputData.add("??????");
        inputData.add("??????");

        int defaultValue = 0;
        if (binding.mypageGenderTextView.getText().toString().equals("??????"))
            defaultValue = 1;


        NumberPickerDialog2 dialog = new NumberPickerDialog2(
                requireContext(),
                new NumberPickerModel2("??????",
                        inputData,
                        defaultValue,
                        "??????",
                        "??????"
                ),
                new DialogClickListener() {
                    @Override
                    public void onPositiveClick(int position) {
                        if (position == 0)
                            viewModel.guestUserConfigModify(
                                    null,
                                    null,
                                    "male",
                                    null
                            );
                        else
                            viewModel.guestUserConfigModify(
                                    null,
                                    null,
                                    "female",
                                    null
                            );
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
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(requireContext(), "?????????", Toast.LENGTH_SHORT).show();
            }
        });
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

        // ?????? ?????? ?????????.
        int countryCount = 0;
        for (String data : inputData) {
            if (binding.mypageCountryTextView.getText().toString().equals(data))
                defaultValue = countryCount;
            else if (binding.mypageCountryTextView.getText().toString().equals("?????????")
                    && country.equals(data)) {
                defaultValue = countryCount;
            }

            countryCount++;
        }

        NumberPickerDialog2 dialog = new NumberPickerDialog2(
                requireContext(),
                new NumberPickerModel2(
                        "????????????",
                        inputData,
                        defaultValue,
                        "??????",
                        "??????"
                ),
                new DialogClickListener() {
                    @Override
                    public void onPositiveClick(int position) {
                        viewModel.guestUserConfigModify(
                                null,
                                null,
                                null,
                                countryNameToCode(inputData.get(position))
                        );
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
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(requireContext(), "?????????", Toast.LENGTH_SHORT).show();
            }
        });

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

    private void showOneButtonDialog(String title, String content) {
        OneButtonBasicDialog oneButtonBasicDialog = new OneButtonBasicDialog(getContext()
                , new BasicDialogTextModel(title, content, "??????", "")
                , new DialogClickListener() {
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

        oneButtonBasicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        oneButtonBasicDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        oneButtonBasicDialog.show();
    }

    private void RenderMyPageFromUserType(String userType){
        if (userType.equals("guest")) {
            setVisible_of_Guest();
        } else {
            setVisible_of_NormalUser();
            if (userType.equals("kakao"))
                renderSnsView("????????????",R.drawable.ic_kakao);
            if (userType.equals("google"))
                renderSnsView("??????",R.drawable.ic_google);
            if (userType.equals("naver"))
                renderSnsView("?????????",R.drawable.ic_naver);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void renderSnsView(String txt, int drawable) {
        binding.mypageSnsImageView.setVisibility(View.VISIBLE);
        binding.mypageSnsImageView.setBackground(requireActivity().getDrawable(drawable));
        binding.mypageSnsTextView.setText(txt);
    }


    private void renderGenderView(String gender) {
        if (gender == null) {
            binding.mypageImageViewGender.setVisibility(View.GONE);
            binding.mypageGenderTextView.setText("?????????");
        } else if (gender.equals("male")) {
            binding.mypageGenderTextView.setText("??????");
            binding.mypageGenderTextView.setTextColor(Color.parseColor("#3E3A39"));
            binding.mypageImageViewGender.setVisibility(View.VISIBLE);
            binding.mypageImageViewGender.setImageResource(R.drawable.ic_male);
        } else if (gender.equals("female")) {
            binding.mypageGenderTextView.setText("??????");
            binding.mypageGenderTextView.setTextColor(Color.parseColor("#3E3A39"));
            binding.mypageImageViewGender.setVisibility(View.VISIBLE);
            binding.mypageImageViewGender.setImageResource(R.drawable.ic_female);
        }
    }

    private void setVisible_of_Guest(){
        // ????????? ?????????, ?????? ?????????, ???????????????
        binding.mypageCompanyIcon.setVisibility(View.GONE);
        binding.mypageBtnRecommendCode.setVisibility(View.GONE);
        binding.mypageImageProfile.setVisibility(View.GONE);
        //?????????
        binding.mypageNickname.setVisibility(View.VISIBLE);
        // ?????? 3???
        //???????????? ?????? --> ????????? ????????? ???????????????. binding.linearLayout.setVisibility(View.GONE);
        binding.linearLayout.setVisibility(View.GONE);
        //?????????, ??????
        binding.mypageId.setVisibility(View.GONE);
        binding.mypageName.setVisibility(View.GONE);
        // ????????? ??? ????????? ???????????? ???
        binding.mypageModifyMyAdress.setVisibility(View.GONE);
        binding.divider1.setVisibility(View.GONE);
        binding.divider2.setVisibility(View.GONE);
        binding.divider3.setVisibility(View.GONE);
        binding.divider4.setVisibility(View.GONE);
        //????????????, SNS
        binding.mypageCompanyAccount.setVisibility(View.GONE);
        binding.mypageSnsTextView.setText("?????????");
        binding.mypageSnsImageView.setVisibility(View.GONE);
        binding.mypageCompanyAccount2.setVisibility(View.GONE);
        binding.mypageSnsAccount.setVisibility(View.GONE);


        binding.mypageLogin.setVisibility(View.VISIBLE);
    }

    private void setVisible_of_NormalUser(){
        // ???????????? ?????????
        binding.mypageCompanyIcon.setVisibility(View.GONE);
        // ????????? ??????
        binding.mypageLogin.setVisibility(View.GONE);
        //?????? ?????? ??????
        binding.mypageCompanyAccount2.setVisibility(View.GONE);
        binding.mypageBtnRecommendCode.setVisibility(View.VISIBLE);
        binding.mypageImageProfile.setVisibility(View.VISIBLE);
        // ?????? 3???
        binding.linearLayout.setVisibility(View.VISIBLE);
        //?????????, ??????
        binding.mypageId.setVisibility(View.VISIBLE);
        binding.mypageName.setVisibility(View.VISIBLE);
        //?????????
        binding.mypageNickname.setVisibility(View.GONE);
        // ????????? ??? ????????? ???????????? ???
        binding.mypageModifyMyAdress.setVisibility(View.VISIBLE);
        binding.divider1.setVisibility(View.VISIBLE);
        binding.divider2.setVisibility(View.VISIBLE);
        binding.divider3.setVisibility(View.VISIBLE);
        binding.divider4.setVisibility(View.VISIBLE);

        //????????????, SNS
        binding.mypageCompanyAccount.setVisibility(View.VISIBLE);
        binding.mypageCompanyAccountApprove.setText("?????????");
        binding.mypageSnsImageView.setVisibility(View.GONE);
        binding.mypageSnsAccount.setVisibility(View.VISIBLE);

    }

    private void setVisible_of_CompanyUser(){

        // ???????????? ?????????
        binding.mypageCompanyIcon.setVisibility(View.VISIBLE);
        // ????????? ??????
        binding.mypageLogin.setVisibility(View.GONE);
        //?????? ?????? ??????
        binding.mypageCompanyAccount2.setVisibility(View.VISIBLE);
        //?????????
        binding.mypageNickname.setVisibility(View.GONE);
        //????????? ??????
        binding.mypageBtnRecommendCode.setVisibility(View.VISIBLE);
        //????????? ?????????
        binding.mypageImageProfile.setVisibility(View.VISIBLE);
        // ?????? 3???
        binding.linearLayout.setVisibility(View.VISIBLE);
        //?????????, ??????
        binding.mypageId.setVisibility(View.VISIBLE);
        binding.mypageName.setVisibility(View.VISIBLE);
        // ????????? ??? ????????? ???????????? ???
        binding.mypageModifyMyAdress.setVisibility(View.VISIBLE);
        binding.divider1.setVisibility(View.VISIBLE);
        binding.divider2.setVisibility(View.VISIBLE);
        binding.divider3.setVisibility(View.VISIBLE);
        binding.divider4.setVisibility(View.VISIBLE);

        //????????????, SNS
        binding.mypageCompanyAccount.setVisibility(View.VISIBLE);
        binding.mypageSnsAccount.setVisibility(View.VISIBLE);
        binding.mypageSnsTextView.setText("?????????");
        binding.mypageSnsImageView.setVisibility(View.GONE);

    }

    private String getImageViewUrl(String fileSrl, String Width) {
        String result = getString(R.string.IMAGE_VIEW_URL) + "?inputFileSrl=" + fileSrl;
        if (Width != null)
            result += "&inputImageWidth=" + Width;
        return result;
    }

    private void GlideImage(ImageView view, String url){
        Glide.with(requireContext()).load(url).placeholder(R.drawable.ic_profile_basic).circleCrop().into(view);
    }

}