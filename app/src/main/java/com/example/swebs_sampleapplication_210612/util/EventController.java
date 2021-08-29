package com.example.swebs_sampleapplication_210612.util;

import android.app.Application;

import androidx.annotation.Nullable;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventListModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Listener.netEventListener;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventController {
    private final SwebsAPI retroAPI;
    private final Application application;
    private final netEventListener listener;


    public EventController(Application application, netEventListener listener) {
        this.application = application;
        this.listener = listener;
        this.retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);
    }

    public void getEventList() {
        HashMap<String, RequestBody> formData = new HashMap<>();
        formData.put("inputFirstIndex", RequestBody.create("0", MediaType.parse("text/plane")));
        formData.put("inputLastIndex", RequestBody.create("9999", MediaType.parse("text/plane")));

        Call<EventListModel> call = retroAPI.getEventList(formData);
        call.enqueue(new Callback<EventListModel>() {
            @Override
            public void onResponse(Call<EventListModel> call, Response<EventListModel> response) {
                if (response.isSuccessful()
                && response.body() != null
                && response.body().isSuccess()) {
                    listener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<EventListModel> call, Throwable t) {

            }
        });
    }
}
