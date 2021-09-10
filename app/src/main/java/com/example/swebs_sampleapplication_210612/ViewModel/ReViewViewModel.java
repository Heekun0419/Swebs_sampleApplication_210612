package com.example.swebs_sampleapplication_210612.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.swebs_sampleapplication_210612.Data.Repository.ReviewRepository;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.CommentModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ReviewListModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReViewViewModel extends AndroidViewModel {
    private MutableLiveData<List<ReviewListModel>> LiveReviewList = new MutableLiveData<>();
    private ReviewRepository repository;
    public ReViewViewModel(@NonNull Application application) {
        super(application);
        repository = new ReviewRepository(application);
    }

    public void getReviewList(String inputCategorySrl, String loadCount, String lastIndex) {
        repository.getReviewList(inputCategorySrl, lastIndex, loadCount).enqueue(new Callback<List<ReviewListModel>>() {
            @Override
            public void onResponse(Call<List<ReviewListModel>> call, Response<List<ReviewListModel>> response) {
                if(response.isSuccessful()){
                    if(response.body() != null)
                        setLiveReviewList(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<ReviewListModel>> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<List<ReviewListModel>> getLiveReviewList() {
        return LiveReviewList;
    }

    public void setLiveReviewList(List<ReviewListModel> liveReviewList) {
        LiveReviewList.setValue(liveReviewList);
    }
}
