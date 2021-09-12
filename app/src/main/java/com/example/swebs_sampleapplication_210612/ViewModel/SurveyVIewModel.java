package com.example.swebs_sampleapplication_210612.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.swebs_sampleapplication_210612.Data.Repository.SurveyRepository;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.SurveyDetailInfoModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.SurveyDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.SurveyOptionModel;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.SurveyListDetailModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.SurveyListModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyVIewModel extends AndroidViewModel {

    private MutableLiveData<List<SurveyListModel>> liveDataSurveyList = new MutableLiveData<>();
    private MutableLiveData<SurveyDetailInfoModel> LiveDataSurveyDetail = new MutableLiveData<>();
    private MutableLiveData<List<SurveyOptionModel>> LiveDataSurveyOptionList = new MutableLiveData<>();
    private MutableLiveData<String> optionSrl = new MutableLiveData<>();
    private SurveyRepository surveyRepository;
    private final SPmanager sPmanager;


    public SurveyVIewModel(@NonNull Application application) {
        super(application);
        surveyRepository = new SurveyRepository(application);
        sPmanager = new SPmanager(application.getApplicationContext());
    }

    public MutableLiveData<List<SurveyListModel>> getLiveDataSurveyList() {
        return liveDataSurveyList;
    }

    public void setLiveDataSurveyList(List<SurveyListModel> liveDataSurveyList) {
        this.liveDataSurveyList.setValue(liveDataSurveyList);
    }

    public MutableLiveData<SurveyDetailInfoModel> getLiveDataSurveyDetail() {
        return LiveDataSurveyDetail;
    }

    public MutableLiveData<List<SurveyOptionModel>> getLiveDataSurveyOptionList() {
        return LiveDataSurveyOptionList;
    }

    public MutableLiveData<String> getOptionSrl() {
        return optionSrl;
    }

    public void getSurveyDetailInfo(String inputSurveySrl){
        surveyRepository.getSurveyDetailInfo(inputSurveySrl, sPmanager.getUserSrl())
                .enqueue(new Callback<SurveyDetailModel>() {
                    @Override
                    public void onResponse(Call<SurveyDetailModel> call, Response<SurveyDetailModel> response) {
                        if(response.isSuccessful() && response.body() != null){
                            if(response.body().isSuccess()){
                                optionSrl.setValue(response.body().getSelected_option_srl());
                                LiveDataSurveyDetail.setValue(response.body().getSurvey_info());
                                LiveDataSurveyOptionList.setValue(response.body().getSurvey_info().getOption());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SurveyDetailModel> call, Throwable t) {

                    }
                });
    }

    public void getSurveyListFromServer(String categoryType) {
        surveyRepository.getSurveyList(sPmanager.getUserSrl(), categoryType, null, null)
                .enqueue(new Callback<com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.SurveyListModel>() {
                    @Override
                    public void onResponse(Call<com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.SurveyListModel> call, Response<com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.SurveyListModel> response) {
                        if (response.isSuccessful()
                        && response.body() != null) {
                            if (response.body().isSuccess()) {
                                List<SurveyListModel> tempModel = new ArrayList<>();

                                int enableCount = 0;
                                int disableCount = 0;
                                for(int i =0; i<response.body().getSurvey().size();i++) {
                                    SurveyListDetailModel model = response.body().getSurvey().get(i);
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    Date startDate = null, endDate = null, nowDate = null;
                                    try {
                                        startDate = simpleDateFormat.parse(model.getStart_date());
                                        endDate = simpleDateFormat.parse(model.getEnd_date());
                                        nowDate = simpleDateFormat.parse(response.body().getNow_date());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (startDate != null && endDate != null && nowDate != null) {
                                        if (nowDate.compareTo(startDate) >= 0
                                                && nowDate.compareTo(endDate) <= 0) {
                                            //
                                            tempModel.add(enableCount++,
                                                    new SurveyListModel(
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
                                                    new SurveyListModel(
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
                    }

                    @Override
                    public void onFailure(Call<com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.SurveyListModel> call, Throwable t) {

                    }
                });

    }

}
