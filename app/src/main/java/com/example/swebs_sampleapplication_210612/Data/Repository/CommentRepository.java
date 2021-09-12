package com.example.swebs_sampleapplication_210612.Data.Repository;

import android.app.Application;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.CommentInputModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.CommentModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class CommentRepository {
    private final SwebsAPI retroAPI;

    public CommentRepository(Application application) {
        this.retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);
    }

    public Call<CommentInputModel> pushComment(String userSrl, String documentSrl, String content, String nestedSrl) {
        HashMap<String, RequestBody> formData = new HashMap<>();
        formData.put("inputUserSrl", RequestBody.create(userSrl, MediaType.parse("text/plane")));
        formData.put("inputDocumentSrl", RequestBody.create(documentSrl, MediaType.parse("text/plane")));
        formData.put("inputContent", RequestBody.create(content, MediaType.parse("text/plane")));
        if (nestedSrl != null)
            formData.put("inputNestedSrl", RequestBody.create(nestedSrl, MediaType.parse("text/plane")));

        return retroAPI.pushComment(formData);
    }

    public Call<List<CommentModel>> getComment(String documentSrl, String lastIndex, String listCount){

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("inputDocumentSrl", RequestBody.create(documentSrl, MediaType.parse("text/plane")));
        if(lastIndex != null)
            map.put("inputLastIndex", RequestBody.create(lastIndex, MediaType.parse("text/plane")));
        if(listCount != null)
            map.put("inputListCount", RequestBody.create(listCount, MediaType.parse("text/plane")));

       return retroAPI.getCommentList(map);
    }

    public Call<List<CommentModel>> getReComment(String documentSrl, String commentSrl, String lastIndex, String listCount){
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("inputDocumentSrl", RequestBody.create(documentSrl, MediaType.parse("text/plane")));
        map.put("inputCommentSrl", RequestBody.create(commentSrl, MediaType.parse("text/plane")));
        if(lastIndex != null)
            map.put("inputLastIndex", RequestBody.create(lastIndex, MediaType.parse("text/plane")));
        if(listCount != null)
            map.put("inputListCount", RequestBody.create(listCount, MediaType.parse("text/plane")));

        return retroAPI.getReCommentList(map);
    }

    // 댓글 삭제.
    public Call<Boolean> pushCommentDelete(String userSrl, String commentSrl) {
        HashMap<String, RequestBody> formData = new HashMap<>();
        formData.put("inputUserSrl", RequestBody.create(userSrl, MediaType.parse("text/plane")));
        formData.put("inputCommentSrl", RequestBody.create(commentSrl, MediaType.parse("text/plane")));

        return retroAPI.pushCommentDelete(formData);
    }
}
