package com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model;

import com.example.swebs_sampleapplication_210612.ViewModel.Model.SurveyListDetailModel;

import java.util.List;

public class MainItemModel {

    private boolean success;
    private String reason;
    private List<ProductListModel> product;
    private List<EventListDetailModel> event;
    private List<MainReviewModel> review;
    private List<SurveyListDetailModel> survey;

    public boolean isSuccess() {
        return success;
    }

    public String getReason() {
        return reason;
    }

    public List<ProductListModel> getProduct() {
        return product;
    }

    public List<EventListDetailModel> getEvent() {
        return event;
    }

    public List<MainReviewModel> getReview() {
        return review;
    }

    public List<SurveyListDetailModel> getSurvey() {
        return survey;
    }
}
