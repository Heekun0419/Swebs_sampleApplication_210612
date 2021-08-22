package com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.GuestSignUpModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.LoginModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.NormalSignUpModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.ScanHistoryAllDataModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.SignUpModel;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface SwebsAPI {

    @Multipart
    @POST("signup/guest_signup.php")
    Call<GuestSignUpModel> guestSignUp(
            @PartMap Map<String, RequestBody> prams
    );

    @Multipart
    @POST("signup/normal_signup.php")
    Call<NormalSignUpModel> userSingUp(
            @PartMap Map<String, RequestBody> prams
    );

    @Multipart
    @POST("signup/exist_referralcode.php")
    Call<Boolean> existReferralCode(
            @PartMap Map<String, RequestBody> prams
    );

    @Multipart
    @POST("scan/scan_history_alldata.php")
    Call<ScanHistoryAllDataModel> pushScanHistoryAllData(
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
