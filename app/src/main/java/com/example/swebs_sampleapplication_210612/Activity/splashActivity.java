package com.example.swebs_sampleapplication_210612.Activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Listener.netSignupListener;
import com.example.swebs_sampleapplication_210612.Data.Room.Swebs.SwebsDatabase;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.OneButtonBasicDialog;
import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.BasicDialogTextModel;
import com.example.swebs_sampleapplication_210612.databinding.ActivitySplashBinding;
import com.example.swebs_sampleapplication_210612.util.SimpleLoginController;
import com.example.swebs_sampleapplication_210612.util.UserLoginController;

public class splashActivity extends AppCompatActivity {
    long SPLASH_MIN_TIME = 0;

    private ActivitySplashBinding binding;
    CharSequence charSequence;

    int aniIndex;
    long aniDelay = 120;
    Handler handler = new Handler();
    private SPmanager sPmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sPmanager = new SPmanager(this);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                binding.imageView5,
                PropertyValuesHolder.ofFloat("scaleX",1.1f),
                PropertyValuesHolder.ofFloat("scaleY",1.1f)
        );

        objectAnimator.setDuration(1000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();

        dbInstanceCreate dbInstanceCreate = new dbInstanceCreate(getApplicationContext());
        dbInstanceCreate.execute();

        animateText("정품 인증 No.1 플랫폼");
    }

    private void loginCheck() {
        long NetworkTime = SystemClock.elapsedRealtime();
        Log.d("login", "getUserToken : " + sPmanager.getUserToken());
        Log.d("login", "getUserSrl : " + sPmanager.getUserSrl());

        new SimpleLoginController(splashActivity.this).isKakaoSession();
        new SimpleLoginController(splashActivity.this).isGoogleSession();

        if (!sPmanager.getUserToken().equals("notFound") && !sPmanager.getUserSrl().equals("notFound")) {
            // Exist Login Data - verify Token Check
            new UserLoginController(
                    getApplication(),
                    new netSignupListener() {
                        @Override
                        public void onSuccess() {
                            setStartIntent(SystemClock.elapsedRealtime() - NetworkTime);
                        }

                        @Override
                        public void onFailed() {
                            Toast.makeText(getApplicationContext(), "로그인 정보가 유효하지 않습니다.", Toast.LENGTH_SHORT).show();
                            loginCheck();
                        }

                        @Override
                        public void onServerError() {
                            dialogServerError();
                        }
                    }
            ).verifyToken();
        } else {
            // None Exist Login Data - Guest Login
            new UserLoginController(
                    getApplication(),
                    new netSignupListener() {
                        @Override
                        public void onSuccess() {
                            setStartIntent(SystemClock.elapsedRealtime() - NetworkTime);
                        }

                        @Override
                        public void onFailed() {
                            dialogServerError();
                        }

                        @Override
                        public void onServerError() {
                            dialogServerError();
                        }
                    }
            ).signUpForGuest();
        }
    }


    @Override
    public void onBackPressed() {

    }

    public void animateText(CharSequence cs){
        charSequence = cs;
        aniIndex = 0;

        binding.textViewSplash.setText("");
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, aniDelay);
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            binding.textViewSplash.setText(charSequence.subSequence(0, aniIndex++));
            if(aniIndex <= charSequence.length())
                handler.postDelayed(runnable, aniDelay);
        }
    };


    private void setStartIntent(long progressTime) {
        long delayTime = SPLASH_MIN_TIME - progressTime;

        Intent intent;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            intent = new Intent(getApplicationContext(), MainActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), PermissionCheckActivity.class);
        }

        startActivity(intent, delayTime);
    }

    private void startActivity(Intent intent, long delayTime) {
        if (delayTime > 0) {
            new Handler().postDelayed(() -> {
                startActivity(intent);
                finish();
            }, delayTime);
        } else {
            startActivity(intent);
            finish();
        }
    }

    private void dialogServerError() {
        OneButtonBasicDialog dialog = new OneButtonBasicDialog(this
                , new BasicDialogTextModel("스웹스 안내", "서버에 연결할 수 없습니다.\n잠시 후 다시 시도 해주세요.", "확인", "")
                , new DialogClickListener() {
            @Override
            public void onPositiveClick(int position) {
                finish();
            }

            @Override
            public void onNegativeClick() {
                finish();
            }

            @Override
            public void onCloseClick() {
                finish();
            }
        });
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
    }

    @SuppressLint("StaticFieldLeak")
    private class dbInstanceCreate extends AsyncTask<Void, Void, Void> {
        @SuppressLint("StaticFieldLeak")
        private final Context context;

        public dbInstanceCreate(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                SwebsDatabase db = SwebsDatabase.getDatabase(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            loginCheck();
        }
    }
}
