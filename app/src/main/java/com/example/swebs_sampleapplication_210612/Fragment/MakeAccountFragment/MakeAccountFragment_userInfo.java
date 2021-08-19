package com.example.swebs_sampleapplication_210612.Fragment.MakeAccountFragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.Activity.MakeAccountActivity;
import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Model.NormalSignUpModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.RetroAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.RetroClient;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMakeAccountUserInfoBinding;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeAccountFragment_userInfo extends Fragment {

    private FragmentMakeAccountUserInfoBinding binding;
    RetroAPI retroAPI;
    private String gender;

    private SPmanager sPmanager;

    private MyInfoRepository myInfoRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retroAPI = RetroClient.getRetrofitClient().create(RetroAPI.class);
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
        // 서버로 전송할 데이터 만들기.
        String inputEmail = binding.editTextEmail.getText().toString().toLowerCase();
        String inputPhoneNumber = binding.editTextUserInfoPhoneNumber.getText().toString().replace("-", "");

        HashMap<String, RequestBody> body = new HashMap<>();
        body.put("inputEmail", RequestBody.create(inputEmail, MediaType.parse("text/plane")));
        body.put("inputPassword", RequestBody.create(binding.editTextUserInfoPassword.getText().toString(), MediaType.parse("text/plane")));
        body.put("inputName", RequestBody.create(binding.editTextUserInfoUsername.getText().toString(), MediaType.parse("text/plane")));
        body.put("inputNickName", RequestBody.create(binding.editTextUserInfoNickname.getText().toString(), MediaType.parse("text/plane")));
        body.put("inputBday", RequestBody.create(binding.editTextUserInfoBirthday.getText().toString(), MediaType.parse("text/plane")));
        body.put("inputPhoneNumber", RequestBody.create(inputPhoneNumber, MediaType.parse("text/plane")));
        body.put("inputGender", RequestBody.create(gender, MediaType.parse("text/plane")));
        body.put("inputSrl", RequestBody.create(sPmanager.getUserSrl(), MediaType.parse("text/plane")));
        body.put("inputType", RequestBody.create("normal", MediaType.parse("text/plane")));

        // 추후 변경하기...
        body.put("inputRegion", RequestBody.create("KR", MediaType.parse("text/plane")));

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
                        myInfoRepository.insertMyInfo("region", responseData.getRegion());
                        myInfoRepository.insertMyInfo("point", responseData.getPoint());
                        myInfoRepository.insertMyInfo("birthday", responseData.getBirthday());
                        myInfoRepository.insertMyInfo("userType", responseData.getUserType());
                        myInfoRepository.insertMyInfo("gender", responseData.getGender());
                        myInfoRepository.insertMyInfo("email", inputEmail);

                        sPmanager.setUserGender(responseData.getGender());
                        sPmanager.setUserType(responseData.getUserType());
                        sPmanager.setUserToken(responseData.getToken());

                        ((MakeAccountActivity)requireActivity()).moveFragment(new MakeAccountFragment_success());
                    }
                }
            }

            @Override
            public void onFailure(Call<NormalSignUpModel> call, Throwable t) {

            }
        });

    }
}