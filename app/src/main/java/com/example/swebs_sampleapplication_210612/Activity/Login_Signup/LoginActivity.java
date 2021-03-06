package com.example.swebs_sampleapplication_210612.Activity.Login_Signup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.BasicDialogTextModel;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.FindPasswordDialog;
import com.example.swebs_sampleapplication_210612.Dialog.OneButtonBasicDialog;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.LoginModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.databinding.ActivityLoginBinding;
import com.example.swebs_sampleapplication_210612.util.Listener.onSimpleLoginListener;
import com.example.swebs_sampleapplication_210612.util.SocialLoginController;
import com.example.swebs_sampleapplication_210612.util.UserLoginController;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    
    private ActivityLoginBinding binding;
    private FindPasswordDialog dialog_findPass;
    private final SPmanager sPmanager = new SPmanager(this);
    private MyInfoRepository myInfoRepository;
    private UserLoginController userLoginController;

    private final String DIALOG_TITLE = "????????? ??????";

    private boolean isLogin = false;
    private String reason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myInfoRepository = new MyInfoRepository(getApplication());
        userLoginController = new UserLoginController(getApplication());

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnLogin.setOnClickListener(v -> {
            // ?????????, ???????????? ????????? ??????.
            loginNormalUser();
        });

        // ???????????? ?????? ?????????
        binding.textViewMakeAccount.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),MakeAccountActivity.class);
            startActivity(intent);
            finish();
        });

        // ????????? ????????? ?????? ?????????
        binding.btnKakaoLogin.setOnClickListener(v -> {
            loginForKakao();
        });
        // ?????? ????????? ?????? ?????????
        binding.btnGoogleLogin.setOnClickListener(v -> {
            loginForGoogle();
        });
        // ????????? ????????? ?????? ?????????
        binding.btnNaverLogin.setOnClickListener(v -> {
            loginForNaver();
            //IntentSnsLogin("naver")
        });

        // ???????????? ?????? ??????
        binding.textViewFindPass.setOnClickListener(v -> {
            // ????????? ?????? ??????????????? ??????
            dialog_findPass = new FindPasswordDialog(LoginActivity.this, new DialogClickListener() {
                @Override
                public void onPositiveClick(int position) {
                    Intent intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
                    startActivity(intent);
                }
                @Override
                public void onNegativeClick() {
                }

                @Override
                public void onCloseClick() {
                }
            });
            dialog_findPass.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog_findPass.show();
        });

    }

    private void loginForNaver() {
        new SocialLoginController(LoginActivity.this, LoginActivity.this)
            .setListener(new onSimpleLoginListener() {
                @Override
                public void onSuccess(String userType, String uid, String email, String nickname) {
                    loginSocialUser(userType, uid, email, nickname);
                }

                @Override
                public void onFailed() {

                }
            })
            .loginForNaver();
    }

    private void loginForKakao() {
        new SocialLoginController(LoginActivity.this)
            .setListener(new onSimpleLoginListener() {
                @Override
                public void onSuccess(String userType, String uid, String email, String nickname) {
                    loginSocialUser(userType, uid, email, nickname);
                }

                @Override
                public void onFailed() {

                }
            })
            .loginForKakao();
    }

    private void loginForGoogle() {
        new SocialLoginController(LoginActivity.this, mGoogleSignup)
            .loginForGoogle();
    }

    private final ActivityResultLauncher<Intent> mGoogleSignup = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("snsLogin", "google uid : " + account.getId());
                Log.d("snsLogin", "google email : " + account.getEmail());
                Log.d("snsLogin", "google displayname : " + account.getDisplayName());
                loginSocialUser("google", account.getId(), account.getEmail(), account.getDisplayName());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("snsLogin", "Google sign in failed", e);
            }

            //new SocialLoginController(LoginActivity.this).logoutForGoogle();
        }
    });

    private void loginSocialUser(String userType, String uid, String email, String nickname) {
        if (userType == null || uid == null) {
            showOneButtonDialog(DIALOG_TITLE, "???????????? ????????? ????????????.");
            return;
        }

        renderLoading(true);
        myInfoRepository.getLoginForSocial(userType, uid)
                .enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        if (response.isSuccessful()
                        && response.body() != null) {
                            if (response.body().isSuccess()) {
                                userLoginController.userDataSaveWhenLogin(response.body());
                                renderLoading(false);
                                finish();
                            } else {
                                renderLoading(false);
                                socialSignUpIntent(userType, uid, email, nickname);
                            }
                        } else {
                            renderLoading(false);
                            showOneButtonDialog(DIALOG_TITLE, "?????? ????????? ???????????? ????????????.\n?????? ??? ?????? ?????? ????????????.");
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable t) {
                        renderLoading(false);
                        showOneButtonDialog(DIALOG_TITLE, "?????? ????????? ???????????? ????????????.\n?????? ??? ?????? ?????? ????????????.");
                    }
                });
    }

    private void socialSignUpIntent(String userType, String uid, String email, String nickname) {
        Intent intent = new Intent(getApplicationContext(),MakeSNSAccountActivity.class);
        intent.putExtra("userType", userType);
        intent.putExtra("uid", uid);
        intent.putExtra("email", email);
        intent.putExtra("nickname", nickname);
        startActivity(intent);
    }

    private void loginNormalUser() {
        if (binding.edtLoginId.getText().toString().equals("")) {
            showOneButtonDialog(DIALOG_TITLE, "???????????? ?????? ????????????.");
            return;
        } else if(binding.edtLoginPass.getText().toString().equals("")) {
            showOneButtonDialog(DIALOG_TITLE, "??????????????? ?????? ????????????.");
            return;
        }

        renderLoading(true);
        myInfoRepository.getLoginForNormal(binding.edtLoginId.getText().toString(), binding.edtLoginPass.getText().toString())
                .enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        if (response.isSuccessful()
                        && response.body() != null) {
                            if (response.body().isSuccess()) {
                                userLoginController.userDataSaveWhenLogin(response.body());

                                renderLoading(false);
                                finish();
                            } else {
                                renderLoading(false);
                                showOneButtonDialog(DIALOG_TITLE, "????????? ?????? ??????????????? ?????? ????????????.");
                            }
                        } else {
                            renderLoading(false);
                            showOneButtonDialog(DIALOG_TITLE, "?????? ????????? ???????????? ????????????.\n?????? ??? ?????? ?????? ????????????.");
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable t) {
                        renderLoading(false);
                        showOneButtonDialog(DIALOG_TITLE, "?????? ????????? ???????????? ????????????.\n?????? ??? ?????? ?????? ????????????.");
                    }
                });
    }

    private void showOneButtonDialog(String title, String content) {
        OneButtonBasicDialog oneButtonBasicDialog = new OneButtonBasicDialog(this
                , new BasicDialogTextModel(title, content, "??????", "")
                , new DialogClickListener() {
            @Override
            public void onPositiveClick(int position) {

            }

            @Override
            public void onNegativeClick() {

            }

            @Override
            public void onCloseClick() {

            }
        });

        oneButtonBasicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        oneButtonBasicDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        oneButtonBasicDialog.show();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void renderLoading(boolean isView) {
        if (isView) {
            binding.loadingView.getRoot().setOnTouchListener((v, event) -> true);
            binding.loadingView.getRoot().setVisibility(View.VISIBLE);
        } else {
            binding.loadingView.getRoot().setVisibility(View.GONE);
        }
    }
}