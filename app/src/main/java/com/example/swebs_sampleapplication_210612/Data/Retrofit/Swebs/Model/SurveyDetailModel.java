package com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model;

import java.util.List;

public class SurveyDetailModel {
    private boolean success;
    private String now_date;
    private SurveyDetailInfoModel survey_info;
    private String selected_option;

    public boolean isSuccess() {
        return success;
    }

    public String getNow_date() {
        return now_date;
    }

    public SurveyDetailInfoModel getSurvey_info() {
        return survey_info;
    }

    public String getSelected_option_srl() {
        return selected_option;
    }
}
