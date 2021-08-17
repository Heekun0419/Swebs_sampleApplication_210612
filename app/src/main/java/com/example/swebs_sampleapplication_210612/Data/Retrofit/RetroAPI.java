package com.example.swebs_sampleapplication_210612.Data.Retrofit;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Model.GuestSignUpModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Model.LoginModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Model.NormalSignUpModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Model.ScanHistoryAllDataModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Model.SignUpModel;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface RetroAPI {

    @Multipart
    @POST("login/guest_sign_up.php")
    Call<GuestSignUpModel> guestSignUp(
            @PartMap Map<String, RequestBody> prams
    );

    @Multipart
    @POST("scan/scan_history_alldata.php")
    Call<ScanHistoryAllDataModel> pushScanHistoryAllData(
            @PartMap Map<String, RequestBody> prams
    );

    @Multipart
    @POST("login/sign_up.php")
    Call<NormalSignUpModel> userSingUp(
            @PartMap Map<String, RequestBody> prams
    );

    @Multipart
    @POST("login/login_email.php")
    Call<LoginModel> userLogin(
            @PartMap Map<String, RequestBody> prams
    );

    @Multipart
    @POST("login/guest_sign_up.php")
    Call<SignUpModel> guest_signUp(
            @PartMap Map<String, RequestBody> prams
    );
}
