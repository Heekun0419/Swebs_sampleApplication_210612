package com.example.swebs_sampleapplication_210612.Data.Retrofit.Listener;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventListModel;

public interface netEventListener {
    void onSuccess(EventListModel data);
    void onFailed();
    void onServerError();
}
