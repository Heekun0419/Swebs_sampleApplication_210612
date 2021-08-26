package com.example.swebs_sampleapplication_210612.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.Data.Room.Swebs.Entity.MyInfo;

import java.util.List;

public class UserInfoViewModel extends AndroidViewModel {
    public MyInfoRepository myInfoRepository;

    public UserInfoViewModel(@NonNull Application application) {
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
}
