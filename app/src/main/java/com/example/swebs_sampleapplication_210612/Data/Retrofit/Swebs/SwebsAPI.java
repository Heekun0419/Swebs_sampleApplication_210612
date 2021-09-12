package com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.CategoryModel;
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
import com.example.swebs_sampleapplication_210612.ViewModel.Model.CommentModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.MyEventListModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ReviewListModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ReviewModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.SurveyDetailModel;
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
    @POST("src1/login/login_social.php")
    Call<LoginModel> loginSocialUser(
            @PartMap Map<String, RequestBody> prams
    );

    // 일반회원 - 정보 수정
    @Multipart
    @POST("src1/login/user_config.php")
    Call<UserConfigModify> normalUserConfigModify(
            @Part MultipartBody.Part file,
            @PartMap Map<String, RequestBody> prams
    );

    // 게스트회원 - 정보 수정
    @Multipart
    @POST("src1/login/guest_config.php")
    Call<Boolean> guestUserConfigModify(
            @PartMap Map<String, RequestBody> prams
    );

    // 메인 페이지 API
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
    // END - Event API

    // START - Document, Comment API
    @Multipart
    @POST("src1/comment/comment_input.php")
    Call<CommentInputModel> pushComment(
            @PartMap Map<String, RequestBody> prams
    );

    // 댓글 API
    @Multipart
    @POST("src1/comment/comment_list.php")
    Call<List<CommentModel>> getCommentList(
            @PartMap Map<String,RequestBody> params
    );

    // 대댓글 API
    @Multipart
    @POST("src1/comment/recomment_list.php")
    Call<List<CommentModel>> getReCommentList(
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

    // START - 인증업체 API
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
    // END - 인증업체 API

    // Item Click 후 리뷰 가져오기
    @Multipart
    @POST("src1/product/review_list.php")
    Call<List<ReviewListModel>> getProductReview(
            @PartMap Map<String,RequestBody> params
    );

    // 제품 내 -> 리뷰만 가져오기
    @Multipart
    @POST("src1/product/review_only.php")
    Call<List<ReviewModel>> getReviewList(
            @PartMap Map<String,RequestBody> params
    );

    // 리뷰 자세히보기
    @Multipart
    @POST("src1/product/review_view.php")
    Call<ReviewModel> getReviewDetailList(
            @PartMap Map<String,RequestBody> params
    );

    // 내 이벤트
    @Multipart
    @POST("src1/event/event_history.php")
    Call<MyEventListModel> getMyEventList(
            @PartMap Map<String,RequestBody> params
    );

   // 내리뷰
   @Multipart
   @POST("src1/product/review_history.php")
   Call<MyReviewModel> getMyReviewList(
           @PartMap Map<String,RequestBody> params
   );

    // 내 서베이
    @Multipart
    @POST("src1/survey/survey_history.php")
    Call<List<SurveyDetailModel>> getMySurveyList(
            @PartMap Map<String,RequestBody> params
    );

}
