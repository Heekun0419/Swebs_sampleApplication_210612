package com.example.swebs_sampleapplication_210612.Data.Repository;

import android.app.Application;
import android.util.Log;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventListModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.LikeApplyModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class EventRepository {
    private final SwebsAPI retroAPI;

    public EventRepository(Application application) {
        this.retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);
    }

    public Call<EventListModel> getEventList(String categorySrl) {
        HashMap<String, RequestBody> formData = new HashMap<>();
        formData.put("inputCategorySrl", RequestBody.create(categorySrl, MediaType.parse("text/plane")));
        //formData.put("inputLastIndex", RequestBody.create("9999", MediaType.parse("text/plane")));

        return retroAPI.getEventList(formData);
    }

    public Call<EventDetailModel> getEventDetail(String eventSrl, String userSrl) {
        HashMap<String, RequestBody> formData = new HashMap<>();
        formData.put("inputEventSrl", RequestBody.create(eventSrl, MediaType.parse("text/plane")));
        formData.put("inputUserSrl", RequestBody.create(userSrl, MediaType.parse("text/plane")));

        return retroAPI.getEventDetail(formData);
    }

    public Call<LikeApplyModel> pushLike(String eventSrl, String userSrl, String targetTable) {
        HashMap<String, RequestBody> formData = new HashMap<>();
        formData.put("inputTargetSrl", RequestBody.create(eventSrl, MediaType.parse("text/plane")));
        formData.put("inputUserSrl", RequestBody.create(userSrl, MediaType.parse("text/plane")));
        formData.put("inputTargetTable", RequestBody.create(targetTable, MediaType.parse("text/plane")));

        return retroAPI.pushLike(formData);
    }
}
