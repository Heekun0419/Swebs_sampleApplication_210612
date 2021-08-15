package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.Dialog.BasicDialogTextModel;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.FindPasswordDialog;
import com.example.swebs_sampleapplication_210612.Dialog.OneButtonBasicDialog;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Model.LoginModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.RetroAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.RetroClient;
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
    private RetroAPI retroAPI;
    private SPmanager sPmanager = new SPmanager(this);

    private boolean isLogin = false;
    private String reason;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        //retrofit Api 설정
        retroAPI = RetroClient.getRetrofitClient().create(RetroAPI.class);

        binding.btnLogin.setOnClickListener(v -> {
            if(!binding.edtLoginId.getText().toString().equals("") && !binding.edtLoginPass.getText().toString().equals("")){
                // 서버와 로그인 체크
                loginCheck();

            }else if(binding.edtLoginId.getText().toString().equals("")){
               Toast("아이디를 입력해주세요.");
            }else if(binding.edtLoginPass.getText().toString().equals(""))
                Toast("비밀번호를 입력해주세요.");

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

    private void loginCheck(){
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("inputEmail", RequestBody.create(MediaType.parse("text/plane"), binding.edtLoginId.getText().toString()));
        map.put("inputPassword", RequestBody.create(MediaType.parse("text/plane"), binding.edtLoginPass.getText().toString()));

        Call<LoginModel> call = retroAPI.userLogin(map);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if(response.isSuccessful()){
                    LoginModel responseData = response.body();
                    if (responseData != null) {
                        if (responseData.isSuccess()) {
                            sPmanager.setUserBirth(responseData.getDateofbirth());
                            sPmanager.setUserType(responseData.getUser_type());
                            sPmanager.setUserEmail(responseData.getUser_email());
                            sPmanager.setUserName(responseData.getName());
                            sPmanager.setUserGender(responseData.getGender());
                            sPmanager.setUserPoint(responseData.getPoints());

                                finishAffinity();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);

                        }else {
                            reason = responseData.getReason();
                            if(reason!=null){
                                if(reason.equals("NotFoundEmail")){
                                    OneButtonBasicDialog dialog = new OneButtonBasicDialog(LoginActivity.this, new BasicDialogTextModel(
                                            "오류", "아이디 혹은 비밀번호가 일치하지 않습니다.", "확인", ""), new DialogClickListener() {
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
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                    dialog.show();
                                }else{
                                    Toast("이메일 형식에 맞지 않습니다.");
                                }
                            }

                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "로그인에 실패 하였습니다.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void Toast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}