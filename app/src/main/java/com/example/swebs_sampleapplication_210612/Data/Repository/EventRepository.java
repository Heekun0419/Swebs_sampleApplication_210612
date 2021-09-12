package com.example.swebs_sampleapplication_210612.Data.Repository;

import android.app.Application;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventListModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.LikeApplyModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.MyEventListModel;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class EventRepository {
    private final SwebsAPI retroAPI;

    public EventRepository(Application application) {
        this.retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);
    }

    public Call<EventListModel> getEventList(String categorySrl, String userSrl) {
        HashMap<String, RequestBody> formData = new HashMap<>();
        formData.put("inputCategorySrl", RequestBody.create(categorySrl, MediaType.parse("text/plane")));
        if (userSrl != null)
            formData.put("inputUserSrl", RequestBody.create(userSrl, MediaType.parse("text/plane")));

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

    public Call<MyEventListModel> getMyEventList(String userSrl, String lastIndex, String loadCount) {
        HashMap<String, RequestBody> formBody = new HashMap<>();
        formBody.put("inputUserSrl", RequestBody.create(userSrl, MediaType.parse("text/plane")));
        if (lastIndex != null)
            formBody.put("inputLastIndex", RequestBody.create(lastIndex, MediaType.parse("text/plane")));
        if (loadCount != null)
            formBody.put("inputListCount", RequestBody.create(loadCount, MediaType.parse("text/plane")));

        return retroAPI.getMyEventList(formBody);
    }
}
