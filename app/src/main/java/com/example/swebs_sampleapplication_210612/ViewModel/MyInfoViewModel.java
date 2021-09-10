package com.example.swebs_sampleapplication_210612.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.LoginModel;
import com.example.swebs_sampleapplication_210612.Data.Room.Swebs.Entity.MyInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyInfoViewModel extends AndroidViewModel {
    private final MutableLiveData<String> loginProgressResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public MyInfoRepository myInfoRepository;

    public MyInfoViewModel(@NonNull Application application) {
        super(application);

        myInfoRepository = new MyInfoRepository(application);

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

    public MutableLiveData<String> getLoginProgressResult() {
        return loginProgressResult;
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
                                loginProgressResult.setValue("success");
                            else
                                loginProgressResult.setValue("failed");
                        } else
                            loginProgressResult.setValue("serverError");

                        isLoading.setValue(false);
                    }

                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable t) {
                        loginProgressResult.setValue("serverError");
                        isLoading.setValue(false);
                    }
                });
    }
}
