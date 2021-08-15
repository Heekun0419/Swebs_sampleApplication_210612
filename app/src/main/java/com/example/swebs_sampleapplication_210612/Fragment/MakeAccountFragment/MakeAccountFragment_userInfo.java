package com.example.swebs_sampleapplication_210612.Fragment.MakeAccountFragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.Activity.MakeAccountActivity;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Model.SignUpModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.RetroAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.RetroClient;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retroAPI = RetroClient.getRetrofitClient().create(RetroAPI.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMakeAccountUserInfoBinding.inflate(inflater,container,false);

        binding.btnMakeAccount.setOnClickListener(v -> {
            userSingUpUpload();
            ((MakeAccountActivity) requireActivity()).moveFragment(new MakeAccountFragment_success());
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

    private void userSingUpUpload() {
        // 서버로 전송할 데이터 만들기.
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("inputEmail", RequestBody.create(MediaType.parse("text/plane"), binding.editTextEmail.getText().toString()));
        map.put("inputPassword", RequestBody.create(MediaType.parse("text/plane"), binding.editTextUserInfoPassword.getText().toString()));
        map.put("inputType", RequestBody.create(MediaType.parse("text/plane"), "normal"));
        map.put("inputName", RequestBody.create(MediaType.parse("text/plane"), binding.editTextUserInfoUsername.getText().toString()));
        map.put("inputNickName", RequestBody.create(MediaType.parse("text/plane"), binding.editTextUserInfoNickname.getText().toString()));
        map.put("inputBday", RequestBody.create(MediaType.parse("text/plane"),binding.editTextUserInfoBirthday.getText().toString()));
        map.put("inputPhoneNumber", RequestBody.create(MediaType.parse("text/plane"), binding.editTextUserInfoPhoneNumber.getText().toString()));
        map.put("inputGender", RequestBody.create(MediaType.parse("text/plane"),gender));
        map.put("inputRegion", RequestBody.create(MediaType.parse("text/plane"), "korea"));

        Call<SignUpModel> call = retroAPI.userSingUp(map);
        call.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {

            }

            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {

            }
        });

    }
}