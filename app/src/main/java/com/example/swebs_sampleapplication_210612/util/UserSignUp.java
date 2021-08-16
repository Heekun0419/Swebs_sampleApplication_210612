package com.example.swebs_sampleapplication_210612.util;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Model.GuestSignUpModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.RetroAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.RetroClient;
import com.example.swebs_sampleapplication_210612.Data.Room.Swebs.Entity.MyInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSignUp {
    private RetroAPI retroAPI;
    private Context context;
    private MyInfoRepository myInfoRepository;

    public UserSignUp(Application application) {
        this.retroAPI = RetroClient.getRetrofitClient().create(RetroAPI.class);
        this.context = application.getApplicationContext();
        this.myInfoRepository = new MyInfoRepository(application);
    }

    public boolean signUpForGuest() {
        Log.d("signUp", "guest signUp Progress");
        HashMap<String, RequestBody> body = new HashMap<>();

        getRegionFromSystem getRegionFromSystem = new getRegionFromSystem(context);

        body.put("inputRegion", RequestBody.create(getRegionFromSystem.getCountry(), MediaType.parse("text/plane")));

        Call<GuestSignUpModel> call = retroAPI.guestSignUp(body);
        call.enqueue(new Callback<GuestSignUpModel>() {
            @Override
            public void onResponse(Call<GuestSignUpModel> call, Response<GuestSignUpModel> response) {
                Log.d("signUp", "1");
                if (!response.isSuccessful())
                    return;
                Log.d("signUp", "2");
                if (response.body() != null) {
                    GuestSignUpModel responseData = response.body();
                    if (responseData.isSuccess()) {
                        myInfoRepository.insertMyInfo("userSrl", responseData.getUserSrl());
                        myInfoRepository.insertMyInfo("token", responseData.getToken());
                        myInfoRepository.insertMyInfo("nickName", responseData.getNickname());
                        myInfoRepository.insertMyInfo("userType", "guest");
                    }
                }
            }

            @Override
            public void onFailure(Call<GuestSignUpModel> call, Throwable t) {

            }
        });

        return false;
    }
}
