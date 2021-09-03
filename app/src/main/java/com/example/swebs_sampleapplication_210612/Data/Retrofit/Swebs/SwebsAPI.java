package com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.CategoryModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.CertifiedCompanyDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventListModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.GuestSignUpModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.LoginModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.NormalSignUpModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.ScanDataPushModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.SignUpModel;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface SwebsAPI {

    @Multipart
    @POST("src1/signup/signup_guest.php")
    Call<GuestSignUpModel> guestSignUp(
            @PartMap Map<String, RequestBody> prams
    );

    @Multipart
    @POST("src1/signup/signup_normal.php")
    Call<NormalSignUpModel> userSingUp(
            @PartMap Map<String, RequestBody> prams
    );

    @Multipart
    @POST("src1/signup/overlap_email.php")
    Call<Boolean> overlapEmail(
            @PartMap Map<String, RequestBody> prams
    );

    @Multipart
    @POST("src1/signup/exist_referralcode.php")
    Call<Boolean> existReferralCode(
            @PartMap Map<String, RequestBody> prams
    );

    @Multipart
    @POST("src1/scan/scan_data_push.php")
    Call<ScanDataPushModel> pushScanHistoryAllData(
            @PartMap Map<String, RequestBody> prams
    );

    @Multipart
    @POST("src1/login/verify_token.php")
    Call<LoginModel> verifyToken(
            @PartMap Map<String, RequestBody> prams
    );

    @Multipart
    @POST("src1/login/login_normal.php")
    Call<LoginModel> loginNormalUser(
            @PartMap Map<String, RequestBody> prams
    );

    @Multipart
    @POST("src1/login/guest_sign_up.php")
    Call<SignUpModel> guest_signUp(
            @PartMap Map<String, RequestBody> prams
    );

    // Event API
    @Multipart
    @POST("KKW_TEST/event_endpoint/event_list.php")
    Call<EventListModel> getEventList(
            @PartMap Map<String, RequestBody> prams
    );

    @GET("src1/event/get_category.php")
    Call<CategoryModel> getCategory();

    // 인증업체 API
    @Multipart
    @POST("KKW_TEST/product/product_categorylist.php")
    Call<ArrayList<CertifiedCompanyDetailModel>> getCorpList(
            @PartMap Map<String,RequestBody> params
    );
}
