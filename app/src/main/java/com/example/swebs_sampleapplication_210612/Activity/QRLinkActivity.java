package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.example.swebs_sampleapplication_210612.databinding.ActivityQrlinkBinding;

public class QRLinkActivity extends AppCompatActivity {

    private ActivityQrlinkBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityQrlinkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String content = binding.textViewQrLinkContent.getText().toString();
        SpannableString spannableString = new SpannableString(content);
        String word = "일반 QR/바코드";
        int start = content.indexOf(word);
        int end = start + word.length();
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#93E3BE")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.textViewQrLinkContent.setText(spannableString);


    }
}