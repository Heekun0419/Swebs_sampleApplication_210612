package com.example.swebs_sampleapplication_210612.Fragment.MakeAccountFragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.Activity.Login_Signup.MakeAccountActivity;
import com.example.swebs_sampleapplication_210612.Activity.Login_Signup.MakeSNSAccountActivity;
import com.example.swebs_sampleapplication_210612.Activity.TermsActivity;
import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.LoginModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.OneButtonBasicDialog;
import com.example.swebs_sampleapplication_210612.Dialog.TwoButtonBasicDialog;
import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.BasicDialogTextModel;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMakeSnsAccountTermsBinding;
import com.example.swebs_sampleapplication_210612.util.UserLoginController;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeSnsAccountFragment_terms extends Fragment {
    private final String DIALOG_TITLE = "회원가입 안내";
    private FragmentMakeSnsAccountTermsBinding binding;
    private SwebsAPI retroAPI;
    private SPmanager sPmanager;
    private MyInfoRepository myInfoRepository;
    private UserLoginController userLoginController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);
        sPmanager = new SPmanager(requireContext());
        myInfoRepository = new MyInfoRepository(requireActivity().getApplication());
        userLoginController = new UserLoginController(requireActivity().getApplication());
        // Inflate the layout for this fragment
        binding = FragmentMakeSnsAccountTermsBinding.inflate(inflater,container,false);


        binding.servicePrivateTerms.setOnClickListener(v ->{
            IntentTermsActivity("http://3.35.249.81/ToS/ToS_S.html");
        });

        binding.locationTerms.setOnClickListener(v ->{
            IntentTermsActivity("http://3.35.249.81/ToS/ToS_L.html");
        });

        binding.marketingTerms.setOnClickListener(v ->{
            IntentTermsActivity("http://3.35.249.81/ToS/ToS_M.html");
        });

        binding.checkBoxMakeAccountTermsAll.setOnClickListener(v -> allTermsCheck());
        binding.layoutTermsAll.setOnClickListener(v -> {
            binding.checkBoxMakeAccountTermsAll.setChecked(!binding.checkBoxMakeAccountTermsAll.isChecked());
            allTermsCheck();
        });

        binding.checkBoxMakeAccountTerms1.setOnClickListener(v -> termsCheck());
        binding.layoutTerms1.setOnClickListener(v ->{
            binding.checkBoxMakeAccountTerms1.setChecked(!binding.checkBoxMakeAccountTerms1.isChecked());
            termsCheck();
        });

        binding.checkBoxMakeAccountTerms2.setOnClickListener(v -> termsCheck());
        binding.layoutTerms2.setOnClickListener(v ->{
            binding.checkBoxMakeAccountTerms2.setChecked(!binding.checkBoxMakeAccountTerms2.isChecked());
            termsCheck();
        });

        binding.checkBoxMakeAccountTerms3.setOnClickListener(v -> termsCheck());
        binding.layoutTerms3.setOnClickListener(v ->{
            binding.checkBoxMakeAccountTerms3.setChecked(!binding.checkBoxMakeAccountTerms3.isChecked());
            termsCheck();
        });

        binding.btnTermsAgree.setOnClickListener(v -> {
            existReferralCode(binding.editTextRecommendCode.getText().toString());
        });

        return binding.getRoot();
    }

    private void IntentTermsActivity(String Url) {
        Intent intent = new Intent(requireActivity().getApplicationContext(), TermsActivity.class);
        intent.putExtra("url", Url);
        startActivity(intent);
    }

    private void termsCheck() {
        // 전체선택 되어있으면 해제
        if(binding.checkBoxMakeAccountTermsAll.isChecked()) {
            binding.checkBoxMakeAccountTermsAll.setChecked(false);
        }
        // 아닐시에는 전체 동의
        else if(binding.checkBoxMakeAccountTerms1.isChecked() && binding.checkBoxMakeAccountTerms2.isChecked()
                && binding.checkBoxMakeAccountTerms3.isChecked()){
            binding.checkBoxMakeAccountTermsAll.setChecked(true);
        }
    }

    private void allTermsCheck() {
        if (binding.checkBoxMakeAccountTermsAll.isChecked()) {
            binding.checkBoxMakeAccountTerms1.setChecked(true);
            binding.checkBoxMakeAccountTerms2.setChecked(true);
            binding.checkBoxMakeAccountTerms3.setChecked(true);
        } else {
            binding.checkBoxMakeAccountTerms1.setChecked(false);
            binding.checkBoxMakeAccountTerms2.setChecked(false);
            binding.checkBoxMakeAccountTerms3.setChecked(false);
        }
    }

    private void existReferralCode(String referralCode) {
        if (referralCode == null || referralCode.equals("")) {
            progressMakeAccount(null);
            return;
        }

        HashMap<String, RequestBody> body = new HashMap<>();
        body.put("inputReferralCode", RequestBody.create(referralCode, MediaType.parse("text/plane")));
        Call<Boolean> call = retroAPI.existReferralCode(body);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body()) {
                        progressMakeAccount(referralCode);
                    } else {
                        showReferralErrorDialog(DIALOG_TITLE, "추천코드가 존재하지 않습니다.\n그대로 진행하히겠습니까?");
                    }
                } else {
                    showOneButtonDialog(DIALOG_TITLE, "서버 연결이 원활하지 않습니다.\n잠시 후 다시 시도 해주세요.");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                showOneButtonDialog(DIALOG_TITLE, "서버 연결이 원활하지 않습니다.\n잠시 후 다시 시도 해주세요.");
            }
        });
    }

    private void progressMakeAccount(String referralCode) {
        myInfoRepository.getSignupForSocial(
                sPmanager.getUserSrl(),
                ((MakeSNSAccountActivity)requireActivity()).getEmail(),
                ((MakeSNSAccountActivity)requireActivity()).getNickname(),
                ((MakeSNSAccountActivity)requireActivity()).getUid(),
                ((MakeSNSAccountActivity)requireActivity()).getUserType(),
                referralCode
                ).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()
                && response.body() != null) {
                    userLoginController.userDataSaveWhenLogin(response.body());
                    Toast.makeText(requireContext(), "성공!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

            }
        });
    }


    private void showReferralErrorDialog(String title, String content) {
        TwoButtonBasicDialog twoButtonBasicDialog = new TwoButtonBasicDialog(requireContext()
                , new BasicDialogTextModel(title, content, "확인", "취소")
                , new DialogClickListener() {
            @Override
            public void onPositiveClick(int position) {
                progressMakeAccount(null);
            }

            @Override
            public void onNegativeClick() {

            }

            @Override
            public void onCloseClick() {

            }
        });
        twoButtonBasicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        twoButtonBasicDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        twoButtonBasicDialog.show();
    }

    private void showOneButtonDialog(String title, String content) {
        OneButtonBasicDialog oneButtonBasicDialog = new OneButtonBasicDialog(requireContext()
                , new BasicDialogTextModel(title, content, "확인", "취소")
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
}