package com.example.swebs_sampleapplication_210612.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.LoginModel;
import com.example.swebs_sampleapplication_210612.Data.Room.Swebs.Entity.MyInfo;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.UserConfigModify;
import com.example.swebs_sampleapplication_210612.util.UserLoginController;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyInfoViewModel extends AndroidViewModel {
    private final SPmanager sPmanager;

    private final MutableLiveData<String> progressResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public MyInfoRepository myInfoRepository;

    public MyInfoViewModel(@NonNull Application application) {
        super(application);

        myInfoRepository = new MyInfoRepository(application);
        sPmanager = new SPmanager(application.getApplicationContext());
    }

    public LiveData<List<MyInfo>> getUserInfoAll() {
        return myInfoRepository.getAllToLiveData();
    }

    public LiveData<String> getUserInfoFromKey(String key) {
        return myInfoRepository.getValueToLiveData(key);
    }

    public void insertUserInfo(String key, String value) {
        myInfoRepository.insertMyInfo(key, value);
    }

    public MutableLiveData<String> getProgressResult() {
        return progressResult;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void loginForNormal(String email, String password) {
        isLoading.setValue(true);
        myInfoRepository.getLoginForNormal(email, password)
                .enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        if (response.isSuccessful()
                        && response.body() != null) {
                            if (response.body().isSuccess())
                                progressResult.setValue("loginSuccess");
                            else
                                progressResult.setValue("loginFailed");
                        } else
                            progressResult.setValue("serverError");

                        isLoading.setValue(false);
                    }

                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable t) {
                        progressResult.setValue("serverError");
                        isLoading.setValue(false);
                    }
                });
    }

    // 일반 유저 회원정보 수정
    public void normalUserConfigModify(String password, String phoneNumber, String name, String nickname, String birthday, String gender, String country, String region, String profilePath) {
        isLoading.setValue(true);
        myInfoRepository.pushUserConfigModify(
                sPmanager.getUserSrl(),
                password,
                phoneNumber,
                name,
                nickname,
                birthday,
                gender,
                country,
                region,
                profilePath
        ).enqueue(new Callback<UserConfigModify>() {
            @Override
            public void onResponse(Call<UserConfigModify> call, Response<UserConfigModify> response) {
                isLoading.setValue(false);
                if (response.isSuccessful()
                && response.body() != null) {
                    if (response.body().isSuccess()) {
                        new UserLoginController(getApplication()).userDataSaveWhenModify(
                                phoneNumber,
                                name,
                                nickname,
                                birthday,
                                gender,
                                country,
                                region,
                                response.body().getPoint(),
                                response.body().getProfile_srl()
                        );
                        progressResult.setValue("modifySuccess");
                    } else
                        progressResult.setValue("modifyFailed");
                }
            }

            @Override
            public void onFailure(Call<UserConfigModify> call, Throwable t) {
                isLoading.setValue(false);
                progressResult.setValue("serverError");
            }
        });
    }
}
