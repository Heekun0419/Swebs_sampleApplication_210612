package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    
    private ActivityLoginBinding binding;
    private FindPasswordDialog dialog_findPass;
    private SwebsAPI retroAPI;
    private SPmanager sPmanager = new SPmanager(this);
    private MyInfoRepository myInfoRepository;

    private final String DIALOG_TITLE = "로그인 안내";

    private boolean isLogin = false;
    private String reason;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        myInfoRepository = new MyInfoRepository(getApplication());

        //retrofit Api 설정
        retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);

        binding.btnLogin.setOnClickListener(v -> {
            // 이메일, 비밀번호 로그인 시작.
            loginNormalUser();
        });

        binding.textViewMakeAccount.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),MakeAccountActivity.class);
            intent.putExtra("resultCode","makeAccount");
            startActivity(intent);
            finish();
        });

        binding.textViewFindPass.setOnClickListener(v -> {
            dialog_findPass = new FindPasswordDialog(LoginActivity.this, new DialogClickListener() {
                @Override
                public void onPositiveClick(int position) {
                    Intent intent = new Intent(LoginActivity.this, MakeAccountActivity.class);
                    intent.putExtra("resultCode","findPass");
                    startActivity(intent);
                }
                @Override
                public void onNegativeClick() {
                    dialog_findPass.dismiss();
                }

                @Override
                public void onCloseClick() {
                    dialog_findPass.dismiss();
                }
            });
            dialog_findPass.setCancelable(false);
            dialog_findPass.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog_findPass.show();
        });
        
    }


    private void loginNormalUser() {
        if (binding.edtLoginId.getText().toString().equals("")) {
            showOneButtonDialog(DIALOG_TITLE, "이메일을 입력 해주세요.");
            return;
        } else if(binding.edtLoginPass.getText().toString().equals("")) {
            showOneButtonDialog(DIALOG_TITLE, "비밀번호를 입력 해주세요.");
            return;
        }
        renderLoading(true);

        HashMap<String, RequestBody> formData = new HashMap<>();
        formData.put("inputEmail", RequestBody.create(binding.edtLoginId.getText().toString(), MediaType.parse("text/plane")));
        formData.put("inputPassword", RequestBody.create(binding.edtLoginPass.getText().toString(), MediaType.parse("text/plane")));

        Call<LoginModel> call = retroAPI.loginNormalUser(formData);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()
                && response.body() != null
                && response.body().isSuccess()) {
                    LoginModel responseData = response.body();
                    myInfoRepository.insertMyInfo("userSrl", responseData.getUserSrl());
                    myInfoRepository.insertMyInfo("userType", responseData.getUserType());
                    myInfoRepository.insertMyInfo("nickName", responseData.getNickName());
                    myInfoRepository.insertMyInfo("name", responseData.getName());
                    myInfoRepository.insertMyInfo("birthday", responseData.getBirthday());
                    myInfoRepository.insertMyInfo("gender", responseData.getGender());
                    myInfoRepository.insertMyInfo("phoneNumber", responseData.getPhoneNumber());
                    myInfoRepository.insertMyInfo("country", responseData.getCountry());
                    myInfoRepository.insertMyInfo("region", responseData.getRegion());
                    myInfoRepository.insertMyInfo("referralCode", responseData.getReferralCode());
                    myInfoRepository.insertMyInfo("email", responseData.getEmail());
                    myInfoRepository.insertMyInfo("point", responseData.getPoint());

                    sPmanager.setUserSrl(responseData.getUserSrl());
                    sPmanager.setUserType(responseData.getUserType());
                    sPmanager.setUserToken(responseData.getToken());
                    sPmanager.setUserReferralCode(responseData.getReferralCode());
                    Log.d("login", "token : " + responseData.getToken());

                    renderLoading(false);
                    finish();
                } else {
                    // 실패
                    renderLoading(false);
                    showOneButtonDialog(DIALOG_TITLE, "아이디 혹은 비밀번호를 확인 해주세요.");
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                renderLoading(false);
                showOneButtonDialog(DIALOG_TITLE, "서버 연결이 원활하지 않습니다.\n잠시 후 다시 시도 해주세요.");
            }
        });
    }

    private void showOneButtonDialog(String title, String content) {
        OneButtonBasicDialog oneButtonBasicDialog = new OneButtonBasicDialog(this
                , new BasicDialogTextModel(title, content, "확인", "")
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

    private void Toast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
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