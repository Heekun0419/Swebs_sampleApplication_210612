package com.example.swebs_sampleapplication_210612.Data.Repository;

import android.app.Application;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.CommentModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ReviewListModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ReviewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class ReviewRepository {

    private final SwebsAPI retroAPI;

    public ReviewRepository(Application application) {
        retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);
    }

    public Call<List<ReviewListModel>> getReviewList(String inputCategory, String lastIndex, String listCount){

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("inputCategorySrl", RequestBody.create(inputCategory, MediaType.parse("text/plane")));
        if(lastIndex != null)
            map.put("inputLastIndex", RequestBody.create(lastIndex, MediaType.parse("text/plane")));
        if(listCount != null)
            map.put("inputListCount", RequestBody.create(listCount, MediaType.parse("text/plane")));

        return retroAPI.getProductReview(map);
    }

    public Call<List<ReviewModel>> getReviewOnlyList(String userSrl, String inputProdSrl, String lastIndex, String listCount){
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("inputUserSrl", RequestBody.create(userSrl, MediaType.parse("text/plane")));
        map.put("inputProductSrl", RequestBody.create(inputProdSrl, MediaType.parse("text/plane")));
        if(lastIndex != null)
            map.put("inputLastIndex", RequestBody.create(lastIndex, MediaType.parse("text/plane")));
        if(listCount != null)
            map.put("inputListCount", RequestBody.create(listCount, MediaType.parse("text/plane")));

        return retroAPI.getReviewList(map);
    }

    public Call<ReviewModel> getReviewDetailList(String userSrl, String inputReviewSrl, String lastIndex, String listCount){
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("inputUserSrl", RequestBody.create(userSrl, MediaType.parse("text/plane")));
        map.put("inputReviewSrl", RequestBody.create(inputReviewSrl, MediaType.parse("text/plane")));
        if(lastIndex != null)
            map.put("inputLastIndex", RequestBody.create(lastIndex, MediaType.parse("text/plane")));
        if(listCount != null)
            map.put("inputListCount", RequestBody.create(listCount, MediaType.parse("text/plane")));

        return retroAPI.getReviewDetailList(map);
    }


}
