package com.example.swebs_sampleapplication_210612.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.CertifiedCompanyDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CertifiedCompanyViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<CertifiedCompanyDetailModel>> liveCompanyModelList = new MutableLiveData<>();
    private ArrayList<CertifiedCompanyDetailModel> arrayList = new ArrayList<>();

    public CertifiedCompanyViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ArrayList<CertifiedCompanyDetailModel>> getLiveCompanyModelList() {
        return liveCompanyModelList;
    }

    public void setLiveComapnyModelList(ArrayList<CertifiedCompanyDetailModel> liveComapnyModelList) {
        this.liveCompanyModelList.postValue(liveComapnyModelList);
    }

    public void getListFromServer(){
        SwebsAPI retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);
        Call<ArrayList<CertifiedCompanyDetailModel>> call = retroAPI.getCorpList();
        call.enqueue(new Callback<ArrayList<CertifiedCompanyDetailModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CertifiedCompanyDetailModel>> call,
                                   Response<ArrayList<CertifiedCompanyDetailModel>> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                       arrayList = response.body();
                       setLiveComapnyModelList(arrayList);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CertifiedCompanyDetailModel>> call, Throwable t) {

            }
        });
    }
}
