package com.example.swebs_sampleapplication_210612.Activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.Data.Room.Swebs.SwebsDatabase;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.databinding.ActivitySplashBinding;
import com.example.swebs_sampleapplication_210612.util.UserLoginController;

public class splashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    CharSequence charSequence;
    int Index;
    long delay = 120;
    Handler handler = new Handler();
    private SPmanager sPmanager = new SPmanager(this);

    private MyInfoRepository myInfoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myInfoRepository = new MyInfoRepository(getApplication());


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                ,WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
        loginCheck();
    }

    private void loginCheck() {
        if (!sPmanager.getUserToken().equals("notFound") && !sPmanager.getUserSrl().equals("notFound")) {
            Log.d("login", "이미 로그인 되어있다. : " + sPmanager.getUserSrl() + " / " + sPmanager.getUserToken());
            new UserLoginController(getApplication()).verifyToken();
        } else {
            Log.d("login", "이미 로그인 되어있지 않다. : " + sPmanager.getUserSrl() + " / " + sPmanager.getUserToken());
            new UserLoginController(getApplication()).signUpForGuest();
        }
    }


    @Override
    public void onBackPressed() {

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            binding.textViewSplash.setText(charSequence.subSequence(0,Index++));
            if(Index <= charSequence.length()){
                handler.postDelayed(runnable,delay);
            }
        }
    };

    public void animateText(CharSequence cs){
        charSequence = cs;
        Index = 0;
        binding.textViewSplash.setText("");
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,delay);
    }


    private void startMainActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2400);
    }
    private void startPermissionActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), PermissionCheckActivity.class);
                startActivity(intent);
                finish();
            }
        },2400);
    }

    private void startActivity(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startMainActivity();
        } else {
            startPermissionActivity();
        }
    }

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
            startActivity();
        }
    }
}
