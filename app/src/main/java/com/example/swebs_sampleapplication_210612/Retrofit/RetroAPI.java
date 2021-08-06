package com.example.swebs_sampleapplication_210612.Retrofit;

import com.example.swebs_sampleapplication_210612.Retrofit.Model.LoginModel;
import com.example.swebs_sampleapplication_210612.Retrofit.Model.SignUpModel;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface RetroAPI {

    @Multipart
    @POST("login/test.php")
    Call<SignUpModel> userSingUp(
            @PartMap Map<String, RequestBody> prams
    );

    @Multipart
    @POST("login/logintest.php")
    Call<LoginModel> userLogin(
            @PartMap Map<String, RequestBody> prams
    );
}
