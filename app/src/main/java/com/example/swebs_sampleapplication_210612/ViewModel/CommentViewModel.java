package com.example.swebs_sampleapplication_210612.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.swebs_sampleapplication_210612.Data.Repository.CommentRepository;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.CertifiedCompanyDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.CommentModel;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<CommentModel>> CommentLiveData = new MutableLiveData<>();
    private ArrayList<CommentModel> commentList = new ArrayList<>();
    private CommentRepository repository;

    public CommentViewModel(@NonNull Application application) {
        super(application);
        repository = new CommentRepository(application);
    }

    public void getListFromServer(String documentSrl){

        repository.getComment(documentSrl,"","").enqueue(new Callback<ArrayList<CommentModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CommentModel>> call, Response<ArrayList<CommentModel>> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        commentList.addAll(response.body());
                        setCommentLiveData(commentList);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CommentModel>> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<ArrayList<CommentModel>> getCommentLiveData() {
        return CommentLiveData;
    }

    public void setCommentLiveData(ArrayList<CommentModel> commentLiveData) {
        CommentLiveData.postValue(commentLiveData);
    }

    public ArrayList<CommentModel> getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList<CommentModel> commentList) {
        this.commentList = commentList;
    }
}
