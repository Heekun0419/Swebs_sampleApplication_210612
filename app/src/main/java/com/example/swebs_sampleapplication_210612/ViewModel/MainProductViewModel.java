package com.example.swebs_sampleapplication_210612.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventListDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.MainItemModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.MainReviewModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.ProductListModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.SurveyListDetailModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainProductViewModel extends AndroidViewModel {
    private final SwebsAPI retroAPI;

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    // 인증업체 관련...
    private final MutableLiveData<List<ProductListModel>> productList = new MutableLiveData<>();
    // 이벤트 관련..
    private final MutableLiveData<List<EventListDetailModel>> eventList = new MutableLiveData<>();
    // 리뷰 관련..
    private final MutableLiveData<List<MainReviewModel>> reviewList = new MutableLiveData<>();
    // 서베이 관련
    private final MutableLiveData<List<SurveyListDetailModel>> surveyList = new MutableLiveData<>();

    public MainProductViewModel(@NonNull Application application) {
        super(application);
        retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<List<ProductListModel>> getProductList() {
        return productList;
    }

    public MutableLiveData<List<EventListDetailModel>> getEventList() {
        return eventList;
    }

    public MutableLiveData<List<MainReviewModel>> getReviewList() {
        return reviewList;
    }

    public MutableLiveData<List<SurveyListDetailModel>> getSurveyList() {
        return surveyList;
    }

    public void getListFromServer() {
        isLoading.setValue(true);
        Call<MainItemModel> call = retroAPI.getMainItemList();
        call.enqueue(new Callback<MainItemModel>() {
            @Override
            public void onResponse(Call<MainItemModel> call, Response<MainItemModel> response) {
                if (response.isSuccessful()
                && response.body() != null) {
                    if (response.body().isSuccess()) {
                        // 인증업체 데이터 넣기..
                        productList.setValue(response.body().getProduct());
                        // 이벤트 데이터 넣기.
                        eventList.setValue(response.body().getEvent());
                        // 리뷰 관련
                        reviewList.setValue(response.body().getReview());
                        // 서베이 관련
                        surveyList.setValue(response.body().getSurvey());
                    }
                }

                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Call<MainItemModel> call, Throwable t) {
                isLoading.setValue(false);
            }
        });
    }
}
