package com.example.swebs_sampleapplication_210612.Data.Repository;

import android.app.Application;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.MyEventListModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.SurveyDetailModel;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class SurveyRepository {
    private SwebsAPI retroAPI;

    public SurveyRepository(Application application){
        retroAPI  = SwebsClient.getRetrofitClient().create(SwebsAPI.class);
    }

    public Call<List<SurveyDetailModel>> getMySurveyList(String userSrl, String lastIndex, String loadCount) {
        HashMap<String, RequestBody> formBody = new HashMap<>();
        formBody.put("inputUserSrl", RequestBody.create(userSrl, MediaType.parse("text/plane")));
        if (lastIndex != null)
            formBody.put("inputLastIndex", RequestBody.create(lastIndex, MediaType.parse("text/plane")));
        if (loadCount != null)
            formBody.put("inputListCount", RequestBody.create(loadCount, MediaType.parse("text/plane")));

        return retroAPI.getMySurveyList(formBody);
    }
}
