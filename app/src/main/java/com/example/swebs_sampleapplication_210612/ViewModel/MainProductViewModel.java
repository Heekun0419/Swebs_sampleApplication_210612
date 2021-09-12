package com.example.swebs_sampleapplication_210612.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.swebs_sampleapplication_210612.Data.Repository.EventRepository;
import com.example.swebs_sampleapplication_210612.Data.Repository.ProductRepository;
import com.example.swebs_sampleapplication_210612.Data.Repository.SurveyRepository;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventListDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventListModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.ProductListlModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.EventModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ReviewListModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.SurveyDetailModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.SurveyModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainProductViewModel extends AndroidViewModel {

    private ProductRepository productRepository;
    private EventRepository eventRepository;
    private SurveyRepository surveyRepository;
    private MutableLiveData<List<ProductListlModel>> LiveProductList = new MutableLiveData<>();
    private MutableLiveData<List<EventModel>> LiveEventList = new MutableLiveData<>();
    private MutableLiveData<List<SurveyModel>> liveDataSurveyList = new MutableLiveData<>();


    public MainProductViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
        eventRepository = new EventRepository(application);
        surveyRepository = new SurveyRepository(application);
    }

    public MutableLiveData<List<SurveyModel>> getLiveDataSurveyList() {
        return liveDataSurveyList;
    }

    public void setLiveDataSurveyList(List<SurveyModel> liveDataSurveyList) {
        this.liveDataSurveyList.setValue(liveDataSurveyList);
    }


    public MutableLiveData<List<EventModel>> getLiveEventList() {
        return LiveEventList;
    }

    public void setLiveEventList(MutableLiveData<List<EventModel>> liveEventList) {
        LiveEventList = liveEventList;
    }

    public MutableLiveData<List<ProductListlModel>> getLiveProductList() {
        return LiveProductList;
    }

    public void setLiveProductList(List<ProductListlModel> liveProductList) {
        LiveProductList.setValue(liveProductList);
    }
}
