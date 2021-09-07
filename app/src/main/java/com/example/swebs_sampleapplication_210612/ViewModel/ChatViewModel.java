package com.example.swebs_sampleapplication_210612.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.swebs_sampleapplication_210612.ViewModel.Model.CommentModel;

import java.util.ArrayList;

public class ChatViewModel extends ViewModel {

    private MutableLiveData<ArrayList<CommentModel>> chattingLiveData = new MutableLiveData<>();


    public ChatViewModel() {

    }

    public MutableLiveData<ArrayList<CommentModel>> getChattingLiveData() {
        return chattingLiveData;
    }

    public void setChattingLiveData(ArrayList<CommentModel> chattingLiveData) {
        this.chattingLiveData.postValue(chattingLiveData);
    }
}

