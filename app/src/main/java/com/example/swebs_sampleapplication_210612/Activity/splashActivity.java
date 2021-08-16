package com.example.swebs_sampleapplication_210612.Activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.swebs_sampleapplication_210612.Activity.MainActivity;
import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.Data.Room.Swebs.Entity.MyInfo;
import com.example.swebs_sampleapplication_210612.Data.Room.Swebs.SwebsDatabase;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.databinding.ActivitySplashBinding;
import com.example.swebs_sampleapplication_210612.util.UserSignUp;

import java.util.List;

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

        loginCheck();
    }

    private void loginCheck() {
        if (myInfoRepository.getValue("userSrl") != null) {
            Log.d("guest", "이미 로그인 되어있다.");
        } else {
            // 로그인 데이터가 없다.
            Log.d("guest", "로그인 안되어있으니 게스트 생성");
            new UserSignUp(getApplication()).signUpForGuest();
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
        },1200);
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

    private void permissionCheck(){
        boolean isPermissionChecked = sPmanager.getPermission();
        if(isPermissionChecked){
            startMainActivity();
        }else{
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
            permissionCheck();
        }
    }
}
