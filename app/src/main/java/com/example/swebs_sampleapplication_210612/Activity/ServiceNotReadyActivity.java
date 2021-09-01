package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityServiceNotReadyBinding;

public class ServiceNotReadyActivity extends AppCompatActivity {

    private ActivityServiceNotReadyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityServiceNotReadyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String content = getString(R.string.as_not_ready);
        SpannableString spannableString = new SpannableString(content);
        String word = "AS관련";
        int start = content.indexOf(word);
        int end = start + word.length();
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#93E3BE")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.textViewServiceNotReady.setText(spannableString);

        binding.btnServiceNotReadyBack.setOnClickListener(v -> onBackPressed());
        binding.btnTopBack.setOnClickListener(v -> onBackPressed());

    }
}