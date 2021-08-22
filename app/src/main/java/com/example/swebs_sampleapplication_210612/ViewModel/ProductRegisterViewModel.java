package com.example.swebs_sampleapplication_210612.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class ProductRegisterViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<String>> TagList = new MutableLiveData<>();

    public ProductRegisterViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ArrayList<String>> getTagList() {
        return TagList;
    }

    public void setTagList(ArrayList<String> tagList) {
        TagList.postValue(tagList);
    }
}
