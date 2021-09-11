package com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model;

import com.example.swebs_sampleapplication_210612.ViewModel.Model.ReviewModel;

import java.util.List;

public class MyReviewModel {
    private boolean success;
    private List<ReviewModel> review;

    public boolean isSuccess() {
        return success;
    }

    public List<ReviewModel> getReview() {
        return review;
    }
}
