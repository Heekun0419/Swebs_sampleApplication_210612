package com.example.swebs_sampleapplication_210612.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.swebs_sampleapplication_210612.Activity.Login_Signup.LoginActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class SimpleLoginController {
    private Activity activity;
    private Context context;

    // Google Login
    private ActivityResultLauncher<Intent> mGoogleSignup;
    // Naver Login
    private OAuthLogin mNaverLoginInstance;

    // Kakao Co
    public SimpleLoginController(Context context) {
        this.context = context;
        // Kakao Init
        KakaoSdk.init(this.context, "9e9aaead2ab6303029c565df975aec9d");
    }

    // Naver Co
    public SimpleLoginController(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;

        // Naver Init
        mNaverLoginInstance = OAuthLogin.getInstance();
        mNaverLoginInstance.init(
                this.context,
                "6DME1WuTnH1zHhQscnHP",
                "1PrqdovSaW",
                "스웹스"
        );
    }

    // Google Co
    public SimpleLoginController(Context context, ActivityResultLauncher<Intent> mGoogleSignup) {
        this.context = context;
        this.mGoogleSignup = mGoogleSignup;
    }
    //

    public void loginForNaver() {
        mNaverLoginInstance.startOauthLoginActivity(activity, new NaverLoginHandler(mNaverLoginInstance, context));
    }

    private static class NaverLoginHandler extends OAuthLoginHandler {
        private final OAuthLogin mNaverLoginInstance;
        private final Context mContext;

        public NaverLoginHandler(OAuthLogin mNaverLoginInstance, Context context) {
            this.mNaverLoginInstance = mNaverLoginInstance;
            this.mContext = context;
        }

        @Override
        public void run(boolean success) {
            if (success) {
                /*
                String accessToken = mNaverLoginInstance.getAccessToken(mContext);
                String refreshToken = mNaverLoginInstance.getRefreshToken(mContext);
                long expiresAt = mNaverLoginInstance.getExpiresAt(mContext);
                String tokenType = mNaverLoginInstance.getTokenType(mContext);

                Log.d("snsLogin", "access3333Token : " + accessToken);
                Log.d("snsLogin", "refreshToken : " + refreshToken);
                Log.d("snsLogin", "tokenType : " + tokenType);
                Log.d("snsLogin", "expiresAt : " + expiresAt);
                */
                new NaverLoginHandler.RequestApiTask(mContext, mNaverLoginInstance).execute();
            } else {
                String errorCode = mNaverLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mNaverLoginInstance.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode:" + errorCode
                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        }

        private class RequestApiTask extends AsyncTask<Void, Void, String> {
            private final Context mContext;
            private final OAuthLogin mOAuthLoginModule;

            public RequestApiTask(Context mContext, OAuthLogin mOAuthLoginModule) {
                this.mContext = mContext;
                this.mOAuthLoginModule = mOAuthLoginModule;
            }

            @Override
            protected void onPreExecute() {}

            @Override
            protected String doInBackground(Void... params) {
                String url = "https://openapi.naver.com/v1/nid/me";
                String at = mOAuthLoginModule.getAccessToken(mContext);
                return mOAuthLoginModule.requestApi(mContext, at, url);
            }

            protected void onPostExecute(String content) {
                Log.d("snsLogin", content);
                try {
                    JSONObject loginResult = new JSONObject(content);
                    if (loginResult.getString("resultcode").equals("00")){
                        JSONObject response = loginResult.getJSONObject("response");

                        Log.d("snsLogin", "네이버 id : " + response.getString("id"));
                        Log.d("snsLogin", "네이버 email : " + response.getString("email"));
                        Log.d("snsLogin", "네이버 닉네임 : " + response.getString("nickname"));
                        mNaverLoginInstance.logout(mContext);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public void loginForGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient client = GoogleSignIn.getClient(context, gso);
        Intent signInIntent = client.getSignInIntent();
        mGoogleSignup.launch(signInIntent);
    }

    public void isGoogleSession() {
        GoogleSignInAccount gso = GoogleSignIn.getLastSignedInAccount(context);
        if (gso != null)
            Log.d("snsLogin", "Google 로그인 O");
        else
            Log.d("snsLogin", "Google 로그인 X");
    }

    public void logoutForGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient client = GoogleSignIn.getClient(context, gso);
        client.signOut();
    }


    public void loginForKakao() {
        if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(context))
            kakaoFromApplication();
        else
            kakaoFromBrowser();
    }

    public void isKakaoSession() {
        UserApiClient.getInstance().me((user, error) -> {
            if (error != null)
                Log.d("snsLogin", "kakao 로그인 X");
            else
                Log.d("snsLogin", "kakao 로그인 O");
            return null;
        });
    }

    public void logoutForKakao() {
        UserApiClient.getInstance().logout(throwable -> null);
    }

    public void unlinkForKakao() {
        UserApiClient.getInstance().unlink(throwable -> null);
    }

    private void kakaoFromApplication() {
        UserApiClient.getInstance().loginWithKakaoTalk(context, (oAuthToken, error) -> {
            if (error != null) {
                Log.d("snsLogin", "kakao app error : " + error);
                if (error.getMessage() != null
                        && error.getMessage().contains("installed"))
                    kakaoFromBrowser();
            } else if (oAuthToken != null) {
                kakaoLoginSuccess();
            }
            return null;
        });
    }

    private void kakaoFromBrowser() {
        UserApiClient.getInstance().loginWithKakaoAccount(context, (oAuthToken, error) -> {
            if (error != null) {
                Log.d("snsLogin", "kakao brow error : " + error);
            }
            if (oAuthToken != null) {
                kakaoLoginSuccess();
            }
            return null;
        });
    }

    private void kakaoLoginSuccess() {
        Log.d("snsLogin","kakao success");
        UserApiClient.getInstance().me((user, error) -> {
            if (error != null) {
                Log.d("snsLogin",error.getMessage());
            } else {
                Log.d("snsLogin", "카카오 id : " + user.getId());
                Log.d("snsLogin", "카카오 Email : " + user.getKakaoAccount().getEmail());
                Log.d("snsLogin", "카카오 닉네임 : " + user.getKakaoAccount().getProfile().getNickname());

                this.unlinkForKakao();
            }
            return null;
        });
    }

}
