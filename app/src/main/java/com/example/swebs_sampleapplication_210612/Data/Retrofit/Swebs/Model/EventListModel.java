package com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model;

import java.util.List;

public class EventListModel {
    private boolean success;
    private List<EventListDetailModel> event;

    public boolean isSuccess() {
        return success;
    }

    public List<EventListDetailModel> getEvent() {
        return event;
    }
}
