package com.example.swebs_sampleapplication_210612.Data.Repository;

import android.app.Application;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.CommentInputModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;

import java.util.HashMap;

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
}
