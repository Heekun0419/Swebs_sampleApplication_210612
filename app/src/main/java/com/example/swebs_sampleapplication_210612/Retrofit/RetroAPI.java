package com.example.swebs_sampleapplication_210612.Retrofit;

import com.example.swebs_sampleapplication_210612.Retrofit.Model.LoginModel;
import com.example.swebs_sampleapplication_210612.Retrofit.Model.ScanHistoryAllDataModel;
import com.example.swebs_sampleapplication_210612.Retrofit.Model.SignUpModel;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface RetroAPI {

    @Multipart
    @POST("scan/scan_history_alldata.php")
    Call<ScanHistoryAllDataModel> pushScanHistoryAllData(
            @PartMap Map<String, RequestBody> prams
    );

    @Multipart
    @POST("login/sign_up.php")
    Call<SignUpModel> userSingUp(
            @PartMap Map<String, RequestBody> prams
    );

    @Multipart
    @POST("login/login.php")
    Call<LoginModel> userLogin(
            @PartMap Map<String, RequestBody> prams
    );

    @Multipart
    @POST("login/guest_sign_up.php")
    Call<SignUpModel> guest_signUp(
            @PartMap Map<String, RequestBody> prams
    );
}
