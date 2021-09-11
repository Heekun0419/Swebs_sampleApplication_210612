package com.example.swebs_sampleapplication_210612.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.swebs_sampleapplication_210612.Data.Repository.ReviewRepository;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.CommentModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ReviewListModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ReviewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReViewViewModel extends AndroidViewModel {
    private MutableLiveData<List<ReviewListModel>> LiveReviewList = new MutableLiveData<>();
    private MutableLiveData<List<ReviewModel>> liveDataReviewOnly = new MutableLiveData<>();
    private MutableLiveData<ReviewModel> LiveDetailReviewModel = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLike = new MutableLiveData<>();
    private final ReviewRepository repository;
    private final SPmanager sPmanager;
    public ReViewViewModel(@NonNull Application application) {
        super(application);
        repository = new ReviewRepository(application);
        sPmanager = new SPmanager(application.getApplicationContext());
    }

    public void getReviewList(String inputCategorySrl, String loadCount, String lastIndex) {
        repository.getReviewList(inputCategorySrl, lastIndex, loadCount).enqueue(new Callback<List<ReviewListModel>>() {
            @Override
            public void onResponse(Call<List<ReviewListModel>> call, Response<List<ReviewListModel>> response) {
                if(response.isSuccessful()){
                    if (response.body() != null)
                        setLiveReviewList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ReviewListModel>> call, Throwable t) {
            }
        });
    }

    //
    public void getReviewOnlyList(String inputCategorySrl, String loadCount, String lastIndex) {
        repository.getReviewOnlyList(sPmanager.getUserSrl(), inputCategorySrl, lastIndex, loadCount)
            .enqueue(new Callback<List<ReviewModel>>() {
                @Override
                public void onResponse(Call<List<ReviewModel>> call, Response<List<ReviewModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            setLiveDataReviewOnly(response.body());
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<ReviewModel>> call, Throwable t) {
                }
            }
        );
    }

    public void getReviewDetailList(String userSrl, String inputReviewSrl, String lastIndex, String listCount){
        repository.getReviewDetailList(userSrl, inputReviewSrl,lastIndex,listCount)
                .enqueue(new Callback<ReviewModel>() {
                    @Override
                    public void onResponse(Call<ReviewModel> call, Response<ReviewModel> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null)
                                setLiveDetailReviewModel(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ReviewModel> call, Throwable t) {

                    }
                });
    }

    public MutableLiveData<ReviewModel> getLiveDetailReviewModel() {
        return LiveDetailReviewModel;
    }

    public void setLiveDetailReviewModel(ReviewModel liveDetailReviewModel) {
        LiveDetailReviewModel.setValue(liveDetailReviewModel);
    }

    public MutableLiveData<List<ReviewListModel>> getLiveReviewList() {
        return LiveReviewList;
    }

    public void setLiveReviewList(List<ReviewListModel> liveReviewList) {
        LiveReviewList.setValue(liveReviewList);
    }

    public MutableLiveData<List<ReviewModel>> getLiveDataReviewOnly() {
        return liveDataReviewOnly;
    }

    public void setLiveDataReviewOnly(List<ReviewModel> liveDataReviewOnly) {
        this.liveDataReviewOnly.setValue(liveDataReviewOnly);
    }

    public MutableLiveData<Boolean> getIsLike() {
        return isLike;
    }

    public void setIsLike(Boolean isLike) {
        this.isLike.setValue(isLike);
    }
}
