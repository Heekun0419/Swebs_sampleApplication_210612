package com.example.swebs_sampleapplication_210612.Data.Retrofit.SwebsCorp;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.GuestSignUpModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.SwebsCorp.Model.CodeCertifyModel;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface SwebsCorpAPI {
    @Multipart
    @POST("rmgcall/am_certify_info.php")
    Call<CodeCertifyModel> getCodeInfo(
            @PartMap Map<String, RequestBody> prams
    );
}
