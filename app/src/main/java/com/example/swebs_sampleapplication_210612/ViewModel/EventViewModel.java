package com.example.swebs_sampleapplication_210612.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.swebs_sampleapplication_210612.ViewModel.Model.EventModel;

import java.util.ArrayList;
import java.util.Collection;

public class EventViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<EventModel>> liveEventList = new MutableLiveData<>();
    private ArrayList<EventModel> EventList = new ArrayList<>();

    public EventViewModel(@NonNull Application application) {
        super(application);
        setDefaultValue();
    }

    private void setDefaultValue(){

        for(int i =0; i<3;i++){
            EventList.add(new EventModel(
                    "https://images.otwojob.com/product/l/r/P/lrP1mUhYpnR780M.jpg",
                    "함소아",
                    "오늘은 어디로 떠나볼까요?",
                    "2021.08.11 ~ 2021.09.21"));
        }
        setLiveEventList(EventList);
    }

    public MutableLiveData<ArrayList<EventModel>> getLiveEventList() {
        return liveEventList;
    }

    public void setLiveEventList(ArrayList<EventModel> liveEventList) {
        this.liveEventList.postValue(liveEventList);
    }

    public void setListNum(int num, String url){
        EventList.removeAll(EventList);

        for(int i =0; i<num; i++){
            EventList.add(new EventModel(url,
                    "스웹스",
                    "추가된 이벤트입니다!",
                    "2021.09.21 ~ 2021.10.20"));
        }
        liveEventList.postValue(EventList);
    }

}
