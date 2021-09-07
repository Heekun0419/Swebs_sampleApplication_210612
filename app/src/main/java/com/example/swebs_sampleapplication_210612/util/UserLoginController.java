package com.example.swebs_sampleapplication_210612.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.Activity.splashActivity;
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
    private Activity activity;
    private final MyInfoRepository myInfoRepository;
    private final SPmanager sPmanager;
    private netSignupListener listener;

    public UserLoginController(Application application) {
        this.retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);
        this.context = application.getApplicationContext();
        this.myInfoRepository = new MyInfoRepository(application);
        this.sPmanager = new SPmanager(application.getApplicationContext());
    }

    public UserLoginController(Application application, netSignupListener listener) {
        this.retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);
        this.context = application.getApplicationContext();
        this.myInfoRepository = new MyInfoRepository(application);
        this.sPmanager = new SPmanager(application.getApplicationContext());
        this.listener = listener;
    }

    public UserLoginController setActivity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public void userDataSaveWhenLogin(LoginModel model) {
        if (model.getUserSrl() != null) {
            myInfoRepository.insertMyInfo("userSrl", model.getUserSrl());
            sPmanager.setUserSrl(model.getUserSrl());
        }
        if (model.getUserType() != null) {
            myInfoRepository.insertMyInfo("userType", model.getUserType());
            sPmanager.setUserType(model.getUserType());
        }
        if (model.getNickName() != null)
            myInfoRepository.insertMyInfo("nickName", model.getNickName());
        if (model.getName() != null)
            myInfoRepository.insertMyInfo("name", model.getName());
        if (model.getBirthday() != null)
            myInfoRepository.insertMyInfo("birthday", model.getBirthday());
        if (model.getGender() != null)
            myInfoRepository.insertMyInfo("gender", model.getGender());
        if (model.getPhoneNumber() != null)
            myInfoRepository.insertMyInfo("phoneNumber", model.getPhoneNumber());
        if (model.getCountry() != null)
            myInfoRepository.insertMyInfo("country", model.getCountry());
        if (model.getRegion() != null)
            myInfoRepository.insertMyInfo("region", model.getRegion());
        if (model.getEmail() != null)
            myInfoRepository.insertMyInfo("email", model.getEmail());
        if (model.getPoint() != null)
            myInfoRepository.insertMyInfo("point", model.getPoint());
        if (model.getToken() != null)
            sPmanager.setUserToken(model.getToken());
        if (model.getReferralCode() != null) {
            myInfoRepository.insertMyInfo("referralCode", model.getReferralCode());
            sPmanager.setUserReferralCode(model.getReferralCode());
        }
    }


    public void userLogout() {
        // 데이터 삭제하기.
        if (sPmanager.getUserType().equals("kakao")) {
            new SocialLoginController(activity).logoutForKakao();
        } else if (sPmanager.getUserType().equals("naver")) {
            new SocialLoginController(activity, context).logoutForNaver();
        } else if (sPmanager.getUserType().equals("google")) {
            new SocialLoginController(activity).logoutForGoogle();
        }

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
                    userDataSaveWhenLogin(response.body());
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

        Call<GuestSignUpModel> call = retroAPI.guestSignup(body);
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
