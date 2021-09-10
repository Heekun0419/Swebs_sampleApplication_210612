package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityAddressSearchBinding;

public class AddressSearchActivity extends AppCompatActivity {
    private ActivityAddressSearchBinding binding;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddressSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.addJavascriptInterface(new MyJavaScriptInterface(), "Android");
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                binding.webView.loadUrl("javascript:sample2_execDaumPostcode();");
            }
        });

        binding.webView.loadUrl("http://dev.swebsapp.com/html/ss.php");
    }

    class MyJavaScriptInterface
    {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processDATA(String data) {
            Bundle extra = new Bundle();
            Intent intent = new Intent();
            extra.putString("data", data);
            intent.putExtras(extra);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}