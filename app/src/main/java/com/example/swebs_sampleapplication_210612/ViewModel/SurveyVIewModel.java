package com.example.swebs_sampleapplication_210612.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.swebs_sampleapplication_210612.Data.Repository.ReviewRepository;
import com.example.swebs_sampleapplication_210612.Data.Repository.SurveyRepository;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventListDetailModel;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.EventModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.MyEventListModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.SurveyDetailModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.SurveyModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyVIewModel extends AndroidViewModel {

    private MutableLiveData<List<SurveyModel>> liveDataSurveyList = new MutableLiveData<>();

    private SurveyRepository surveyRepository;
    private SPmanager sPmanager;


    public SurveyVIewModel(@NonNull Application application) {
        super(application);
        surveyRepository = new SurveyRepository(application);
        sPmanager = new SPmanager(application.getApplicationContext());
    }

    public MutableLiveData<List<SurveyModel>> getLiveDataSurveyList() {
        return liveDataSurveyList;
    }

    public void setLiveDataSurveyList(List<SurveyModel> liveDataSurveyList) {
        this.liveDataSurveyList.setValue(liveDataSurveyList);
    }

    public void getMySurveyListFromServer(String userSrl) {
        surveyRepository.getMySurveyList(userSrl, null, null )
                .enqueue(new Callback<List<SurveyDetailModel>>() {
                    @Override
                    public void onResponse(Call<List<SurveyDetailModel>> call, Response<List<SurveyDetailModel>> response) {
                        if (response.isSuccessful()
                                && response.body() != null) {

                            List<SurveyModel> tempModel = new ArrayList<>();

                            int enableCount = 0;
                            int disableCount = 0;
                            for(int i =0; i<response.body().size();i++) {
                                SurveyDetailModel model = response.body().get(i);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date startDate = null, endDate = null, nowDate = null;
                                try {
                                    startDate = simpleDateFormat.parse(model.getStart_date());
                                    endDate = simpleDateFormat.parse(model.getEnd_date());
                                    nowDate = simpleDateFormat.parse(model.getNow_date());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (startDate != null && endDate != null && nowDate != null) {
                                    if (nowDate.compareTo(startDate) >= 0
                                            && nowDate.compareTo(endDate) <= 0) {
                                        //
                                        tempModel.add(enableCount++,
                                                new SurveyModel(
                                                        1
                                                        , "진행중"
                                                        , model.getSurvey_srl()
                                                        , model.getFile_srl()
                                                        , model.getCategory_title()
                                                        , model.getSurvey_title()
                                                        , (endDate.getTime() - nowDate.getTime())/86400000 + "일 남음"
                                                        , model.getPoint()
                                                        , model.getJoin_count()
                                                )
                                        );
                                    } else {
                                        int statusType;
                                        String statusText;
                                        if (nowDate.compareTo(endDate) <= 0) {
                                            statusType = 2;
                                            statusText = "미 진행";
                                        } else {
                                            statusType = 3;
                                            statusText = "마감";
                                        }
                                        tempModel.add(enableCount+(disableCount++),
                                                new SurveyModel(
                                                        statusType
                                                        , statusText
                                                        , model.getSurvey_srl()
                                                        , model.getFile_srl()
                                                        , model.getCategory_title()
                                                        , model.getSurvey_title()
                                                        , simpleDateFormat.format(startDate) + " ~ " + simpleDateFormat.format(endDate)
                                                        , model.getPoint()
                                                        , model.getJoin_count()
                                                )
                                        );
                                    }
                                }
                            }
                            setLiveDataSurveyList(tempModel);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SurveyDetailModel>> call, Throwable t) {

                    }
                });

    }

}
