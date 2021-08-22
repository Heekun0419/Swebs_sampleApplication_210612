package com.example.swebs_sampleapplication_210612.Fragment.MakeAccountFragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.swebs_sampleapplication_210612.Activity.MakeAccountActivity;
import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.NormalSignUpModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.BasicDialogTextModel;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.OneButtonBasicDialog;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMakeAccountUserInfoBinding;

import java.util.HashMap;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeAccountFragment_userInfo extends Fragment {

    private FragmentMakeAccountUserInfoBinding binding;
    SwebsAPI retroAPI;
    private String gender;

    private SPmanager sPmanager;

    private MyInfoRepository myInfoRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);
        sPmanager = new SPmanager(requireContext());

        myInfoRepository = new MyInfoRepository(requireActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMakeAccountUserInfoBinding.inflate(inflater,container,false);

        binding.btnMakeAccount.setOnClickListener(v -> {
            userSingUpUpload();
        });

        // 성별 설정 버튼 이벤트
        binding.btnGenderFemale.setOnClickListener(v -> {
            binding.btnGenderFemale.setSelected(true);
            binding.textViewMakeAccountGenderFemale.setTextColor(Color.parseColor("#21CCB2"));
            binding.btnGenderMale.setSelected(false);
            binding.textViewMakeAccountGenderMale.setTextColor(Color.parseColor("#C2C3C7"));
            gender = "female";
        });

        binding.btnGenderMale.setOnClickListener(v -> {
            binding.btnGenderMale.setSelected(true);
            binding.textViewMakeAccountGenderMale.setTextColor(Color.parseColor("#21CCB2"));
            binding.btnGenderFemale.setSelected(false);
            binding.textViewMakeAccountGenderFemale.setTextColor(Color.parseColor("#C2C3C7"));
            gender = "male";
        });


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.editTextUserInfoPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    private void userSingUpUpload() {
        ((MakeAccountActivity)requireActivity()).renderLoading(true);

        // 서버로 전송할 데이터 만들기.
        String inputEmail = binding.editTextEmail.getText().toString().toLowerCase();
        String inputPhoneNumber = binding.editTextUserInfoPhoneNumber.getText().toString().replace("-", "");

        HashMap<String, RequestBody> body = new HashMap<>();
        body.put("inputUserSrl", RequestBody.create(sPmanager.getUserSrl(), MediaType.parse("text/plane")));
        body.put("inputEmail", RequestBody.create(inputEmail, MediaType.parse("text/plane")));
        body.put("inputPassword", RequestBody.create(binding.editTextUserInfoPassword.getText().toString(), MediaType.parse("text/plane")));
        body.put("inputName", RequestBody.create(binding.editTextUserInfoUsername.getText().toString(), MediaType.parse("text/plane")));
        body.put("inputBirthday", RequestBody.create(binding.editTextUserInfoBirthday.getText().toString(), MediaType.parse("text/plane")));
        body.put("inputPhoneNumber", RequestBody.create(inputPhoneNumber, MediaType.parse("text/plane")));
        body.put("inputPhoneCountry", RequestBody.create("none", MediaType.parse("text/plane")));
        body.put("inputGender", RequestBody.create(gender, MediaType.parse("text/plane")));
        body.put("inputNickname", RequestBody.create(binding.editTextUserInfoNickname.getText().toString(), MediaType.parse("text/plane")));
        body.put("inputReferralCode", RequestBody.create("WSdcR", MediaType.parse("text/plane")));

        // 추후 변경하기...
        body.put("inputCountry", RequestBody.create("KR", MediaType.parse("text/plane")));
        body.put("inputCountryRegion", RequestBody.create("none", MediaType.parse("text/plane")));

        Call<NormalSignUpModel> call = retroAPI.userSingUp(body);
        call.enqueue(new Callback<NormalSignUpModel>() {
            @Override
            public void onResponse(Call<NormalSignUpModel> call, Response<NormalSignUpModel> response) {
                if (!response.isSuccessful())
                    return;

                if (response.body() != null) {
                    NormalSignUpModel responseData = response.body();
                    if (responseData.isSuccess()) {
                        myInfoRepository.insertMyInfo("userSrl", responseData.getUserSrl());
                        myInfoRepository.insertMyInfo("token", responseData.getToken());
                        myInfoRepository.insertMyInfo("nickName", responseData.getNickname());
                        myInfoRepository.insertMyInfo("name", responseData.getName());
                        myInfoRepository.insertMyInfo("country", responseData.getCountry());
                        myInfoRepository.insertMyInfo("point", responseData.getPoint());
                        myInfoRepository.insertMyInfo("birthday", responseData.getBirthday());
                        myInfoRepository.insertMyInfo("userType", responseData.getUserType());
                        myInfoRepository.insertMyInfo("gender", responseData.getGender());
                        myInfoRepository.insertMyInfo("email", inputEmail);

                        sPmanager.setUserSrl(responseData.getUserSrl());
                        sPmanager.setUserType(responseData.getUserType());
                        sPmanager.setUserToken(responseData.getToken());

                        ((MakeAccountActivity)requireActivity()).renderLoading(false);
                        ((MakeAccountActivity)requireActivity()).moveFragment(new MakeAccountFragment_success());
                    } else {
                        ((MakeAccountActivity)requireActivity()).renderLoading(false);

                        String dialogText;
                        switch (Objects.requireNonNull(responseData.getReason())) {
                            case "emailForm":
                                dialogText = "이메일 형식이 맞지 않습니다.\n이메일을 확인 해주세요.";
                                break;
                            case "overlapEmail":
                                dialogText = "이미 가입된 이메일 입니다.\n이메일을 확인 해주세요.";
                                break;
                            case "overlapNickname":
                                dialogText = "이미 가입된 닉네임 입니다.\n닉네임을 확인 해주세요.";
                                break;
                            case "alreadySignUp":
                                dialogText = "이미 회원가입을 진행된 게스트 계정입니다.\n어플을 종료 후 다시 실행 해주세요.";
                                break;
                            default:
                                dialogText = "회원가입을 실패 하였습니다.\n잠시 후 다시 시도 해주세요.";
                                break;
                        }

                        showOneButtonDialog("회원가입 안내", dialogText);

                    }
                }
            }

            @Override
            public void onFailure(Call<NormalSignUpModel> call, Throwable t) {
                ((MakeAccountActivity)requireActivity()).renderLoading(false);
                showOneButtonDialog("회원가입 안내", "회원가입을 실패 하였습니다.\n잠시 후 다시 시도 해주세요.");
            }
        });

    }

    private void showOneButtonDialog(String title, String content) {
        OneButtonBasicDialog oneButtonBasicDialog = new OneButtonBasicDialog(requireContext()
                , new BasicDialogTextModel(title, content, "확인", "")
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