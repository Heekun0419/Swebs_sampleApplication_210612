package com.example.swebs_sampleapplication_210612.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ChatViewModel extends ViewModel {

    private MutableLiveData<ArrayList<ChattingItem>> chattingLiveData = new MutableLiveData<>();


    public ChatViewModel() {

    }

    public MutableLiveData<ArrayList<ChattingItem>> getChattingLiveData() {
        return chattingLiveData;
    }

    public void setChattingLiveData(ArrayList<ChattingItem> chattingLiveData) {
        this.chattingLiveData.postValue(chattingLiveData);
    }
}

