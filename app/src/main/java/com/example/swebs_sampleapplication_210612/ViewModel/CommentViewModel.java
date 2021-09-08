package com.example.swebs_sampleapplication_210612.ViewModel;

import android.app.Application;
import android.text.Editable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.swebs_sampleapplication_210612.Data.Repository.CommentRepository;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.CommentInputModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.CommentModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentViewModel extends AndroidViewModel {

    private MutableLiveData<List<CommentModel>> CommentLiveData = new MutableLiveData<>();
    private MutableLiveData<List<CommentModel>> ReCommentLiveData = new MutableLiveData<>();
    private CommentRepository commentRepository;
    private SPmanager sPmanager;

    public CommentViewModel(@NonNull Application application) {
        super(application);
        commentRepository = new CommentRepository(application);
        sPmanager = new SPmanager(application);
    }

    public void getCommentList(String documentSrl, String loadCount, String lastIndex) {
        commentRepository.getComment(documentSrl, lastIndex, loadCount).enqueue(new Callback<ArrayList<CommentModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CommentModel>> call, Response<ArrayList<CommentModel>> response) {
                if(response.isSuccessful()) {
                    if(response.body()!=null) {
                        setCommentLiveData(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CommentModel>> call, Throwable t) {

            }
        });
    }

    public void getReCommentList(String documentSrl, String commentSrl, String lastIndex, String listCount){
        commentRepository.getReComment(documentSrl, commentSrl, lastIndex, listCount).enqueue(new Callback<List<CommentModel>>() {
            @Override
            public void onResponse(Call<List<CommentModel>> call, Response<List<CommentModel>> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        setCommentLiveData(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CommentModel>> call, Throwable t) {

            }
        });
    }

    public void pushComment(String documentSrl, Editable content, String nestedSrl) {
        commentRepository.pushComment(
                sPmanager.getUserSrl()
                , documentSrl
                , stringToHtml(content)
                , nestedSrl
        ).enqueue(new Callback<CommentInputModel>() {
            @Override
            public void onResponse(Call<CommentInputModel> call, Response<CommentInputModel> response) {
                List<CommentModel> tempCommentListModel = new ArrayList<>();
                tempCommentListModel.add(new CommentModel(
                        response.body().getComment_srl()
                        , content.toString()
                        , sPmanager.getUserSrl()
                        , new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                        , new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                        , response.body().getNickname()
                        , response.body().getProfile_srl()
                        , "0"
                ));
                CommentLiveData.setValue(tempCommentListModel);

            }

            @Override
            public void onFailure(Call<CommentInputModel> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<List<CommentModel>> getCommentLiveData() {
        return CommentLiveData;
    }

    public void setCommentLiveData(List<CommentModel> commentLiveData) {
        CommentLiveData.setValue(commentLiveData);
    }

    public MutableLiveData<List<CommentModel>> getReCommentLiveData() {
        return ReCommentLiveData;
    }

    public void setReCommentLiveData(List<CommentModel> reCommentLiveData) {
        ReCommentLiveData.setValue(reCommentLiveData);
    }

    private String stringToHtml(Editable string) {
        return HtmlCompat.toHtml(string, HtmlCompat.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL);
    }
}
