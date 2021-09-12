package com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model;

import com.example.swebs_sampleapplication_210612.ViewModel.Model.SurveyListDetailModel;

import java.util.List;

public class SurveyListModel {
    private boolean success;
    private String reason;
    private String now_date;
    private List<SurveyListDetailModel> survey;

    public boolean isSuccess() {
        return success;
    }

    public String getReason() {
        return reason;
    }

    public String getNow_date() {
        return now_date;
    }

    public List<SurveyListDetailModel> getSurvey() {
        return survey;
    }
}
