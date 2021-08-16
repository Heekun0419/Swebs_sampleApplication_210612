package com.example.swebs_sampleapplication_210612.util;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Model.GuestSignUpModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.RetroAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.RetroClient;
import com.example.swebs_sampleapplication_210612.Data.Room.Swebs.Entity.MyInfo;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginController {
    private final RetroAPI retroAPI;
    private final Context context;
    private final MyInfoRepository myInfoRepository;
    private SPmanager sPmanager;

    public UserLoginController(Application application) {
        this.retroAPI = RetroClient.getRetrofitClient().create(RetroAPI.class);
        this.context = application.getApplicationContext();
        this.myInfoRepository = new MyInfoRepository(application);
        this.sPmanager = new SPmanager(application.getApplicationContext());
    }
    public void signUpForGuest() {
        HashMap<String, RequestBody> body = new HashMap<>();

        getRegionFromSystem getRegionFromSystem = new getRegionFromSystem(context);

        body.put("inputRegion", RequestBody.create(getRegionFromSystem.getCountry(), MediaType.parse("text/plane")));

        Call<GuestSignUpModel> call = retroAPI.guestSignUp(body);
        call.enqueue(new Callback<GuestSignUpModel>() {
            @Override
            public void onResponse(Call<GuestSignUpModel> call, Response<GuestSignUpModel> response) {
                if (!response.isSuccessful())
                    return;

                if (response.body() != null) {
                    GuestSignUpModel responseData = response.body();
                    if (responseData.isSuccess()) {
                        myInfoRepository.insertMyInfo("userSrl", responseData.getMember_srl());
                        myInfoRepository.insertMyInfo("token", responseData.getToken());
                        myInfoRepository.insertMyInfo("nickName", responseData.getNickname());
                        myInfoRepository.insertMyInfo("name", responseData.getNickname());
                        myInfoRepository.insertMyInfo("region", responseData.getRegion());
                        myInfoRepository.insertMyInfo("point", responseData.getPoint());
                        myInfoRepository.insertMyInfo("userType", "guest");


                        sPmanager.setUserType("guest");
                        sPmanager.setUserSrl(responseData.getMember_srl());
                        sPmanager.setUserToken(responseData.getToken());
                    }
                }
            }

            @Override
            public void onFailure(Call<GuestSignUpModel> call, Throwable t) {
            }
        });
    }
}
