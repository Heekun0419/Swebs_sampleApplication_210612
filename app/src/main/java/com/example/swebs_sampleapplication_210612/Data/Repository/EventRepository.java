package com.example.swebs_sampleapplication_210612.Data.Repository;

import android.app.Application;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventDetailModel;
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

    public Call<EventDetailModel> getEventDetail(String eventSrl, String userSrl) {
        HashMap<String, RequestBody> formData = new HashMap<>();
        formData.put("inputEventSrl", RequestBody.create(eventSrl, MediaType.parse("text/plane")));
        formData.put("inputUserSrl", RequestBody.create(userSrl, MediaType.parse("text/plane")));

        return retroAPI.getEventDetail(formData);
    }
}
