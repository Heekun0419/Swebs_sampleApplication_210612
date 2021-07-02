package com.example.swebs_sampleapplication_210612.Activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.swebs_sampleapplication_210612.Activity.MainActivity;
import com.example.swebs_sampleapplication_210612.databinding.ActivitySplashBinding;

public class splashActivity extends AppCompatActivity {

        private ActivitySplashBinding binding;
        CharSequence charSequence;
        int Index;
        long delay = 150;
        Handler handler = new Handler();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            binding = ActivitySplashBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

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

            animateText("정품인증 NO.1 플랫폼");
            startMainActivity();
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
            },3000);
        }

    }

