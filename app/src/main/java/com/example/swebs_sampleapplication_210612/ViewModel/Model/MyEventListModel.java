package com.example.swebs_sampleapplication_210612.ViewModel.Model;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventListDetailModel;

import java.util.List;

public class MyEventListModel {
    private boolean success;
    private String now_date;
    private List<EventListDetailModel> event_history;

    public boolean isSuccess() {
        return success;
    }

    public String getNow_date() {
        return now_date;
    }

    public List<EventListDetailModel> getEvent_history() {
        return event_history;
    }
}
