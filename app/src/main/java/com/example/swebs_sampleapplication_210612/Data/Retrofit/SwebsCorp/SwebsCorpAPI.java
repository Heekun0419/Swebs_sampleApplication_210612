package com.example.swebs_sampleapplication_210612.Data.Retrofit.SwebsCorp;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.GuestSignUpModel;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface SwebsCorpAPI {
    @Multipart
    @POST("login/guest_sign_up.php")
    Call<GuestSignUpModel> guestSignUp(
            @PartMap Map<String, RequestBody> prams
    );
}
