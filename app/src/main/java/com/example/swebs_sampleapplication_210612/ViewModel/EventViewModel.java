package com.example.swebs_sampleapplication_210612.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventListDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventListModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.EventModel;
import com.example.swebs_sampleapplication_210612.util.EventController;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Listener.netEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventViewModel extends AndroidViewModel {
    private final MutableLiveData<ArrayList<EventModel>> liveEventList = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public EventViewModel(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<ArrayList<EventModel>> getLiveEventList() {
        return liveEventList;
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
                                            ,"https://images.otwojob.com/product/l/r/P/lrP1mUhYpnR780M.jpg"
                                            , "함소야"
                                            , detailModel.getEvent_title()
                                            , simpleDateFormat.format(startDate) + " ~ " + simpleDateFormat.format(endDate)
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
                                            ,"https://images.otwojob.com/product/l/r/P/lrP1mUhYpnR780M.jpg"
                                            , "함소야"
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
