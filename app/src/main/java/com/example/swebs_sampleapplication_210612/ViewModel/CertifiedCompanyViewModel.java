package com.example.swebs_sampleapplication_210612.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.CertifiedCompanyDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CertifiedCompanyViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<CertifiedCompanyDetailModel>> liveCompanyModelList = new MutableLiveData<>();
    private ArrayList<CertifiedCompanyDetailModel> arrayList = new ArrayList<>();
    // 서버에서 한번에 해당 값 만큼만 불러오기
    private final int LIST_LOAD_COUNT =9999;
    public CertifiedCompanyViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ArrayList<CertifiedCompanyDetailModel>> getLiveCompanyModelList() {
        return liveCompanyModelList;
    }

    public void setLiveComapnyModelList(ArrayList<CertifiedCompanyDetailModel> liveComapnyModelList) {
        this.liveCompanyModelList.postValue(liveComapnyModelList);
    }

    public void getListFromServer(String categorySrl, String LastIndex){
        SwebsAPI retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("inputLastIndex", RequestBody.create(LastIndex, MediaType.parse("text/plane")));
        map.put("inputListCount", RequestBody.create(Integer.toString(LIST_LOAD_COUNT), MediaType.parse("text/plane")));
        map.put("inputCategorySrl", RequestBody.create(categorySrl, MediaType.parse("text/plane")));

        Call<ArrayList<CertifiedCompanyDetailModel>> call = retroAPI.getCorpList(map);
        call.enqueue(new Callback<ArrayList<CertifiedCompanyDetailModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CertifiedCompanyDetailModel>> call,
                                   Response<ArrayList<CertifiedCompanyDetailModel>> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        arrayList.addAll(response.body());
                        Log.d("array",arrayList.get(arrayList.size()-1).getTag());
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
