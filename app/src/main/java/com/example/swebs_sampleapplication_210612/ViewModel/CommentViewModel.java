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
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<List<CommentModel>> CommentLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<CommentModel>> ReCommentLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> liveDeleteCommentPosition = new MutableLiveData<>();
    private final CommentRepository commentRepository;
    private final SPmanager sPmanager;
    private Call<List<CommentModel>> callGetComment;

    public CommentViewModel(@NonNull Application application) {
        super(application);
        commentRepository = new CommentRepository(application);
        sPmanager = new SPmanager(application);
    }

    public void getCommentList(String documentSrl, String loadCount, String lastIndex) {
        isLoading.setValue(true);
        callGetComment = commentRepository.getComment(documentSrl, lastIndex, loadCount);
        callGetComment.enqueue(new Callback<List<CommentModel>>() {
            @Override
            public void onResponse(Call<List<CommentModel>> call, Response<List<CommentModel>> response) {
                if(response.isSuccessful()) {
                    if(response.body()!=null) {
                        setCommentLiveData(response.body());
                    }
                }

                isLoading.setValue(false);
                callGetComment = null;
            }

            @Override
            public void onFailure(Call<List<CommentModel>> call, Throwable t) {
                isLoading.setValue(false);
                callGetComment = null;
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
                if (response.isSuccessful()
                && response.body() != null) {
                    if (response.body().isSuccess()) {
                        List<CommentModel> tempCommentListModel = new ArrayList<>();
                        tempCommentListModel.add(new CommentModel(
                                response.body().getComment_srl()
                                , content.toString()
                                , sPmanager.getUserSrl()
                                , new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                                , null
                                , response.body().getNickname()
                                , response.body().getProfile_srl()
                                , "0"
                        ));
                        CommentLiveData.setValue(tempCommentListModel);
                    }
                }
            }

            @Override
            public void onFailure(Call<CommentInputModel> call, Throwable t) {

            }
        });
    }

    // 댓글 삭제
    public void pushCommentDelete(String commentSrl, int position) {
        commentRepository.pushCommentDelete(
                sPmanager.getUserSrl(),
                commentSrl
        ).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()
                && response.body() != null) {
                    if (response.body())
                        liveDeleteCommentPosition.setValue(position);

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    public void allNetworkCancel() {
        if (callGetComment != null)
            callGetComment.cancel();
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

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    private String stringToHtml(Editable string) {
        return HtmlCompat.toHtml(string, HtmlCompat.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL);
    }

    public MutableLiveData<Integer> getLiveDeleteCommentPosition() {
        return liveDeleteCommentPosition;
    }
}
