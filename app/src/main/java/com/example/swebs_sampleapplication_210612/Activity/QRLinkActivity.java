package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.example.swebs_sampleapplication_210612.databinding.ActivityQrlinkBinding;

import java.net.URL;

public class QRLinkActivity extends AppCompatActivity {

    private ActivityQrlinkBinding binding;

    private String resultUrl;
    private int resultBarcodeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityQrlinkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        resultUrl = getIntent().getStringExtra("url");

        binding.textViewUrlLink.setText(resultUrl);
        if (!isURLType(resultUrl)) {
            binding.btnQrLink.setVisibility(View.GONE);
        }

        String content = binding.textViewQrLinkContent.getText().toString();
        SpannableString spannableString = new SpannableString(content);
        String word = "일반 QR/바코드";
        int start = content.indexOf(word);
        int end = start + word.length();
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#93E3BE")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.textViewQrLinkContent.setText(spannableString);
    }

    @Override
    protected void onResume() {
        super.onResume();

        binding.btnQrLink.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(resultUrl));
            startActivity(browserIntent);
        });
    }

    private boolean isURLType(String data) {
        try {
            new URL(data);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}