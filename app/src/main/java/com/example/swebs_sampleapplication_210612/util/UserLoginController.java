package com.example.swebs_sampleapplication_210612.util;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Listener.netSignupListener;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.GuestSignUpModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.LoginModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginController {
    private final SwebsAPI retroAPI;
    private final Context context;
    private final MyInfoRepository myInfoRepository;
    private final SPmanager sPmanager;
    private final netSignupListener listener;

    public UserLoginController(Application application, netSignupListener listener) {
        this.retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);
        this.context = application.getApplicationContext();
        this.myInfoRepository = new MyInfoRepository(application);
        this.sPmanager = new SPmanager(application.getApplicationContext());
        this.listener = listener;
    }


    public void userLogout() {
        // 데이터 삭제하기.
        sPmanager.removeUserSrl();
        sPmanager.removeUserType();
        sPmanager.removeUserToken();
        sPmanager.removeUserReferralCode();
        myInfoRepository.deleteAllMyInfo();
    }

    public void verifyToken() {
        HashMap<String, RequestBody> formData = new HashMap<>();
        formData.put("inputUserSrl", RequestBody.create(sPmanager.getUserSrl(), MediaType.parse("text/plane")));
        formData.put("inputToken", RequestBody.create(sPmanager.getUserToken(), MediaType.parse("text/plane")));

        Call<LoginModel> call = retroAPI.verifyToken(formData);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().isSuccess()) {
                    LoginModel responseData = response.body();
                    myInfoRepository.insertMyInfo("userSrl", responseData.getUserSrl());
                    myInfoRepository.insertMyInfo("userType", responseData.getUserType());
                    myInfoRepository.insertMyInfo("nickName", responseData.getNickName());
                    myInfoRepository.insertMyInfo("name", responseData.getName());
                    myInfoRepository.insertMyInfo("birthday", responseData.getBirthday());
                    myInfoRepository.insertMyInfo("gender", responseData.getGender());
                    myInfoRepository.insertMyInfo("phoneNumber", responseData.getPhoneNumber());
                    myInfoRepository.insertMyInfo("country", responseData.getCountry());
                    myInfoRepository.insertMyInfo("region", responseData.getRegion());
                    myInfoRepository.insertMyInfo("referralCode", responseData.getReferralCode());
                    myInfoRepository.insertMyInfo("email", responseData.getEmail());
                    myInfoRepository.insertMyInfo("point", responseData.getPoint());

                    if (responseData.getUserSrl() != null)
                        sPmanager.setUserSrl(responseData.getUserSrl());
                    if (responseData.getUserType() != null)
                        sPmanager.setUserType(responseData.getUserType());
                    if (responseData.getToken() != null)
                        sPmanager.setUserToken(responseData.getToken());
                    if (responseData.getReferralCode() != null)
                        sPmanager.setUserReferralCode(responseData.getReferralCode());

                    listener.onSuccess();
                } else {
                    // 실패
                    userLogout();
                    listener.onFailed();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                listener.onServerError();
            }
        });
    }

    public void signUpForGuest() {
        HashMap<String, RequestBody> body = new HashMap<>();

        getRegionFromSystem getRegionFromSystem = new getRegionFromSystem(context);

        body.put("inputCountry", RequestBody.create(getRegionFromSystem.getCountry(), MediaType.parse("text/plane")));

        Call<GuestSignUpModel> call = retroAPI.guestSignUp(body);
        call.enqueue(new Callback<GuestSignUpModel>() {
            @Override
            public void onResponse(Call<GuestSignUpModel> call, Response<GuestSignUpModel> response) {
                if (!response.isSuccessful())
                    return;

                if (response.body() != null) {
                    GuestSignUpModel responseData = response.body();
                    if (responseData.isSuccess()) {
                        myInfoRepository.insertMyInfo("userType", "guest");
                        myInfoRepository.insertMyInfo("userSrl", responseData.getUserSrl());
                        myInfoRepository.insertMyInfo("token", responseData.getToken());
                        myInfoRepository.insertMyInfo("nickName", responseData.getNickname());
                        myInfoRepository.insertMyInfo("name", responseData.getName());
                        myInfoRepository.insertMyInfo("country", responseData.getCountry());
                        myInfoRepository.insertMyInfo("point", responseData.getPoint());

                        sPmanager.setUserType("guest");
                        sPmanager.setUserSrl(responseData.getUserSrl());
                        sPmanager.setUserToken(responseData.getToken());

                        listener.onSuccess();
                    } else {
                        listener.onFailed();
                    }
                }
            }

            @Override
            public void onFailure(Call<GuestSignUpModel> call, Throwable t) {
                listener.onServerError();
            }
        });
    }
}
