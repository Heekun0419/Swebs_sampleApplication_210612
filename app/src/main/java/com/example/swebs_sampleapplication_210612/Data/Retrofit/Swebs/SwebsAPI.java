package com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.CategoryModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventAddressModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventApplyInfoModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventApplyModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventOptionModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.MainItemModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.MyReviewModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.ProductDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.ProductListModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.CommentInputModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventListModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.GuestSignUpModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.LikeApplyModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.LoginModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.NormalSignUpModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.ScanDataPushModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.SurveyDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.SurveyListModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.SurveyParticipateModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.CommentModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.MyEventListModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ReviewListModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ReviewModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.UserConfigModify;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface SwebsAPI {
    @Multipart
    @POST("src1/signup/signup_guest.php")
    Call<GuestSignUpModel> guestSignup(
            @PartMap Map<String, RequestBody> prams
    );

    @Multipart
    @POST("src1/signup/signup_normal.php")
    Call<NormalSignUpModel> normalSignup(
            @PartMap Map<String, RequestBody> prams
    );

    @Multipart
    @POST("src1/signup/signup_social.php")
    Call<LoginModel> socialSignup(
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

    // START - SCAN
    @Multipart
    @POST("src1/scan/scan_data_push.php")
    Call<ScanDataPushModel> pushScanHistoryAllData(
            @PartMap Map<String, RequestBody> prams
    );

    @Multipart
    @POST("src1/scan/scan_auth_push.php")
    Call<String> pushScanAuth(
            @PartMap Map<String, RequestBody> prams
    );

    // END - SCAN

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
    @POST("src1/login/login_social.php")
    Call<LoginModel> loginSocialUser(
            @PartMap Map<String, RequestBody> prams
    );

    // ???????????? - ?????? ??????
    @Multipart
    @POST("src1/login/user_config.php")
    Call<UserConfigModify> normalUserConfigModify(
            @Part MultipartBody.Part file,
            @PartMap Map<String, RequestBody> prams
    );

    // ??????????????? - ?????? ??????
    @Multipart
    @POST("src1/login/guest_config.php")
    Call<Boolean> guestUserConfigModify(
            @PartMap Map<String, RequestBody> prams
    );

    // ????????? ????????? ??????
    @Multipart
    @POST("src1/login/set_event_address_info.php")
    Call<Boolean> pushAddressModify(
            @PartMap Map<String, RequestBody> prams
    );

    // ????????? ????????? ????????????
    @Multipart
    @POST("src1/login/get_event_address_info.php")
    Call<EventAddressModel> getAddressModify(
            @PartMap Map<String, RequestBody> prams
    );

    // ?????? ????????? API
    @GET("src1/main/main_list.php")
    Call<MainItemModel> getMainItemList();

    // START - Event API
    @Multipart
    @POST("src1/event/event_list.php")
    Call<EventListModel> getEventList(
            @PartMap Map<String, RequestBody> prams
    );

    @Multipart
    @POST("src1/event/event_detail.php")
    Call<EventDetailModel> getEventDetail(
            @PartMap Map<String, RequestBody> prams
    );

    @Multipart
    @POST("src1/common/get_category.php")
    Call<CategoryModel> getCategory(
            @PartMap Map<String, RequestBody> prams
    );

    // ??? ?????????
    @Multipart
    @POST("src1/event/event_history.php")
    Call<MyEventListModel> getMyEventList(
            @PartMap Map<String,RequestBody> params
    );

    // ????????? ?????? ????????????
    @Multipart
    @POST("src1/event/get_event_option.php")
    Call<List<EventOptionModel>> getEventOption(
            @PartMap Map<String,RequestBody> params
    );

    // ????????? ?????? ??????.
    @Multipart
    @POST("src1/event/event_apply.php")
    Call<EventApplyModel> pushEventApply(
            @PartMap Map<String,RequestBody> params
    );

    // ????????? ?????? ?????? ??????
    @Multipart
    @POST("src1/event/get_apply_info.php")
    Call<EventApplyInfoModel> getEventApplyInfo(
            @PartMap Map<String,RequestBody> params
    );

    // ????????? ?????? ??????
    @Multipart
    @POST("src1/event/event_apply_delete.php")
    Call<Boolean> pushEventApplyDelete(
            @PartMap Map<String,RequestBody> params
    );
    // END - Event API

    // START - Document, Comment API
    @Multipart
    @POST("src1/comment/comment_input.php")
    Call<CommentInputModel> pushComment(
            @PartMap Map<String, RequestBody> prams
    );

    // ?????? API
    @Multipart
    @POST("src1/comment/comment_list.php")
    Call<List<CommentModel>> getCommentList(
            @PartMap Map<String,RequestBody> params
    );

    // ????????? API
    @Multipart
    @POST("src1/comment/recomment_list.php")
    Call<List<CommentModel>> getReCommentList(
            @PartMap Map<String,RequestBody> params
    );

    // ?????? ?????? API
    @Multipart
    @POST("src1/comment/comment_delete.php")
    Call<Boolean> pushCommentDelete(
            @PartMap Map<String,RequestBody> params
    );
    // END - Document, Comment API

    // START - Like API
    @Multipart
    @POST("src1/like/like_apply.php")
    Call<LikeApplyModel> pushLike(
            @PartMap Map<String, RequestBody> prams
    );
    // END - Like API

    // START - ???????????? API
    @Multipart
    @POST("src1/product/product_categorylist.php")
    Call<List<ProductListModel>> getCertifiedList(
            @PartMap Map<String,RequestBody> params
    );

    @Multipart
    @POST("src1/product/product_detail.php")
    Call<ProductDetailModel> getCertifiedDetail(
            @PartMap Map<String,RequestBody> params
    );
    // END - ???????????? API

    // Item Click ??? ?????? ????????????
    @Multipart
    @POST("src1/product/review_list.php")
    Call<List<ReviewListModel>> getProductReview(
            @PartMap Map<String,RequestBody> params
    );

    // ?????? ??? -> ????????? ????????????
    @Multipart
    @POST("src1/product/review_only.php")
    Call<List<ReviewModel>> getReviewList(
            @PartMap Map<String,RequestBody> params
    );

    // ?????? ???????????????
    @Multipart
    @POST("src1/product/review_view.php")
    Call<ReviewModel> getReviewDetailList(
            @PartMap Map<String,RequestBody> params
    );

   // ?????????
   @Multipart
   @POST("src1/product/review_history.php")
   Call<MyReviewModel> getMyReviewList(
           @PartMap Map<String,RequestBody> params
   );

    // ??? ?????????
    @Multipart
    @POST("src1/survey/survey_list.php")
    Call<SurveyListModel> getSurveyList(
            @PartMap Map<String,RequestBody> params
    );

    // ????????? ????????? ??????
    @Multipart
    @POST("src1/survey/survey_detail.php")
    Call<SurveyDetailModel> getSurveyDetailList(
            @PartMap Map<String,RequestBody> params
    );

    // ????????? ?????? ??????
    @Multipart
    @POST("src1/survey/survey_apply.php")
    Call<SurveyParticipateModel> getParticipateSurvey(
            @PartMap Map<String,RequestBody> params
    );

    // ????????? ??? ?????? ??????
    @Multipart
    @POST("src1/survey/survey_reapply.php")
    Call<SurveyParticipateModel> getReParticipateSurvey(
            @PartMap Map<String,RequestBody> params
    );


}
