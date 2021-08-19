package com.example.swebs_sampleapplication_210612.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class ReviewPhotoViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<String>> liveUrlList = new MutableLiveData<>();

    public ReviewPhotoViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ArrayList<String>> getLiveUrlList() {
        return liveUrlList;
    }

    public void setLiveUrlList(ArrayList<String> liveUrlList) {
        this.liveUrlList.postValue(liveUrlList);
    }
}
