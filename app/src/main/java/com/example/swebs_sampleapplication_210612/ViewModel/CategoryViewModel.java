package com.example.swebs_sampleapplication_210612.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.CategoryDetailModel;

import java.util.ArrayList;

public class CategoryViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<CategoryDetailModel>> categoryDetailModelLiveData = new MutableLiveData<>();

    public CategoryViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ArrayList<CategoryDetailModel>> getCategoryDetailModelLiveData() {
        return categoryDetailModelLiveData;
    }

    public void setCategoryDetailModelLiveData(ArrayList<CategoryDetailModel> categoryDetailModelLiveData) {
        this.categoryDetailModelLiveData.postValue(categoryDetailModelLiveData);
    }
}
