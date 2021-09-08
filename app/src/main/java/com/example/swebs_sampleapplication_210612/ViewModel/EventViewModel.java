package com.example.swebs_sampleapplication_210612.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.swebs_sampleapplication_210612.Data.Repository.EventRepository;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventDetailInfoModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventListDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventListModel;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.EventModel;
import com.example.swebs_sampleapplication_210612.util.EventController;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Listener.netEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventViewModel extends AndroidViewModel {
    private SPmanager sPmanager;
    private EventRepository eventRepository;

    private final MutableLiveData<ArrayList<EventModel>> liveEventList = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<EventDetailInfoModel> liveEventDetailInfo = new MutableLiveData<>();

    public EventViewModel(@NonNull Application application) {
        super(application);
        eventRepository = new EventRepository(application);
        sPmanager = new SPmanager(application.getApplicationContext());
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    public MutableLiveData<ArrayList<EventModel>> getLiveEventList() {
        return liveEventList;
    }
    public MutableLiveData<EventDetailInfoModel> getLiveEventDetailInfo() {
        return liveEventDetailInfo;
    }

    public void getEventDetailFromServer(String eventSrl) {
        isLoading.setValue(true);

        eventRepository.getEventDetail(eventSrl, sPmanager.getUserSrl())
                .enqueue(new Callback<EventDetailModel>() {
                    @Override
                    public void onResponse(Call<EventDetailModel> call, Response<EventDetailModel> response) {
                        if (response.isSuccessful()
                        && response.body() != null) {
                            liveEventDetailInfo.setValue(response.body().getEvent_info());
                        }
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onFailure(Call<EventDetailModel> call, Throwable t) {
                        isLoading.setValue(false);
                    }
                });
    }

    public void getEventListFromServer() {
        isLoading.setValue(true);
        new EventController(getApplication(), new netEventListener() {
            @Override
            public void onSuccess(EventListModel data) {
                ArrayList<EventModel> tempModel = new ArrayList<>();

                int enableCount = 0;
                int disableCount = 0;
                for(EventListDetailModel detailModel : data.getEvent()) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date startDate = null, endDate = null, nowDate = null;
                    try {
                        startDate = simpleDateFormat.parse(detailModel.getStart_date());
                        endDate = simpleDateFormat.parse(detailModel.getEnd_date());
                        nowDate = simpleDateFormat.parse(data.getNow_date());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (startDate != null && endDate != null && nowDate != null) {
                        if (nowDate.compareTo(startDate) >= 0
                        && nowDate.compareTo(endDate) <= 0) {
                            //
                            tempModel.add(enableCount++,
                                    new EventModel(
                                            1
                                            , detailModel.getCategory_title()
                                            , detailModel.getEvent_srl()
                                            , detailModel.getFile_srl()
                                            , detailModel.getCorp_name()
                                            , detailModel.getEvent_title()
                                            , (endDate.getTime() - nowDate.getTime())/86400000 + "일 남음"
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
                                statusText = "모집 종료";
                            }
                            tempModel.add(enableCount+(disableCount++),
                                    new EventModel(
                                            statusType
                                            , statusText
                                            , detailModel.getEvent_srl()
                                            , detailModel.getFile_srl()
                                            , detailModel.getCorp_name()
                                            , detailModel.getEvent_title()
                                            , simpleDateFormat.format(startDate) + " ~ " + simpleDateFormat.format(endDate)
                                    )
                            );
                        }
                    }
                }

                liveEventList.setValue(tempModel);
                isLoading.setValue(false);
            }

            @Override
            public void onFailed() {

            }

            @Override
            public void onServerError() {

            }
        }).getEventList();
    }

}
