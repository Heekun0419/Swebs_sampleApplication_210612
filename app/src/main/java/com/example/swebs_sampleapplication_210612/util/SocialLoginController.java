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
import com.example.swebs_sampleapplication_210612.util.Listener.onSimpleLoginListener;
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
import com.nhn.android.naverlogin.data.OAuthLoginState;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class SocialLoginController {
    private Activity activity;
    private Context context;
    private onSimpleLoginListener listener;

    // Google Login
    private ActivityResultLauncher<Intent> mGoogleSignup;
    // Naver Login
    private OAuthLogin mNaverLoginInstance;

    // Kakao Co
    public SocialLoginController(Context context) {
        this.context = context;
        // Kakao Init
        KakaoSdk.init(this.context, "9e9aaead2ab6303029c565df975aec9d");
    }

    // Naver Co
    public SocialLoginController(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;

        // Naver Init
        mNaverLoginInstance = OAuthLogin.getInstance();
        mNaverLoginInstance.init(
                this.context,
                "6DME1WuTnH1zHhQscnHP",
                "1PrqdovSaW",
                "?????????"
        );
    }

    // Google Co
    public SocialLoginController(Context context, ActivityResultLauncher<Intent> mGoogleSignup) {
        this.context = context;
        this.mGoogleSignup = mGoogleSignup;
    }
    //

    public SocialLoginController setListener(onSimpleLoginListener listener) {
        this.listener = listener;
        return this;
    }

    public void loginForNaver() {
        mNaverLoginInstance.startOauthLoginActivity(activity, new NaverLoginHandler(mNaverLoginInstance, context, listener));
    }

    public void isNaverSession() {
        Log.d("snsLogin", "Naver State : " + mNaverLoginInstance.getState(context));
        Log.d("snsLogin", "Naver State1 : " + OAuthLoginState.NEED_LOGIN);
        Log.d("snsLogin", "Naver State2 : " + OAuthLoginState.NEED_INIT);
        if (OAuthLoginState.NEED_LOGIN.equals(mNaverLoginInstance.getState(context))
            || OAuthLoginState.NEED_INIT.equals(mNaverLoginInstance.getState(context))) {
            Log.d("snsLogin", "Naver ????????? X");
        } else {
            Log.d("snsLogin", "Naver ????????? O");
        }
    }

    public void logoutForNaver() {
        mNaverLoginInstance.logout(context);
    }

    private static class NaverLoginHandler extends OAuthLoginHandler {
        private final OAuthLogin mNaverLoginInstance;
        private final Context mContext;
        private final onSimpleLoginListener listener;

        public NaverLoginHandler(OAuthLogin mNaverLoginInstance, Context context, onSimpleLoginListener listener) {
            this.mNaverLoginInstance = mNaverLoginInstance;
            this.mContext = context;
            this.listener = listener;
        }

        @Override
        public void run(boolean success) {
            if (success) {
                new NaverLoginHandler.RequestApiTask(mContext, mNaverLoginInstance, listener).execute();
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
            private final onSimpleLoginListener listener;

            public RequestApiTask(Context mContext, OAuthLogin mOAuthLoginModule, onSimpleLoginListener listener) {
                this.mContext = mContext;
                this.mOAuthLoginModule = mOAuthLoginModule;
                this.listener = listener;
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

                        Log.d("snsLogin", "????????? id : " + response.getString("id"));
                        Log.d("snsLogin", "????????? email : " + response.getString("email"));
                        Log.d("snsLogin", "????????? ????????? : " + response.getString("nickname"));
                        listener.onSuccess(
                                "naver",
                                response.getString("id"),
                                response.getString("email"),
                                response.getString("nickname")
                        );
                        //mNaverLoginInstance.logout(mContext);
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
            Log.d("snsLogin", "Google ????????? O");
        else
            Log.d("snsLogin", "Google ????????? X");
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
                Log.d("snsLogin", "kakao ????????? X");
            else
                Log.d("snsLogin", "kakao ????????? O");
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
                Log.d("snsLogin", "????????? id : " + user.getId());
                Log.d("snsLogin", "????????? Email : " + user.getKakaoAccount().getEmail());
                Log.d("snsLogin", "????????? ????????? : " + user.getKakaoAccount().getProfile().getNickname());

                listener.onSuccess(
                        "kakao",
                        Long.toString(user.getId()),
                        user.getKakaoAccount().getEmail(),
                        user.getKakaoAccount().getProfile().getNickname()
                );

                //this.unlinkForKakao();
            }
            return null;
        });
    }

}
