package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityAuthenticScan2Binding;
import com.example.swebs_sampleapplication_210612.databinding.ActivityAuthenticScanBinding;

public class AuthenticScanActivity extends AppCompatActivity {

    private ActivityAuthenticScan2Binding binding;

    private String resultUrl, resultCompany, resultCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding = ActivityAuthenticScanBinding.inflate(getLayoutInflater());
        binding = ActivityAuthenticScan2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setLoadingVisibility(true);

        resultUrl = getIntent().getStringExtra("url");
        resultCompany = getIntent().getStringExtra("company");
        resultCode = getIntent().getStringExtra("code");

        String loadUrl = "https://www.swebs.co.kr/certchk/" + resultCompany + "/swebs_result.html?q=" + resultCode;
        webViewInit();
        webViewLoadUrl(loadUrl);

        binding.btnAuthenticScanBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.btnPurchaseInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PurchaseInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void webViewInit() {
        binding.webView.setWebViewClient(new WebViewClient(){
            int finishCount = 0;
            @Override
            public void onPageCommitVisible(WebView view, String url) {
                renderWebViewComplete(++finishCount);
                super.onPageCommitVisible(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                renderWebViewComplete(++finishCount);
                super.onPageFinished(view, url);
            }
        });
    }

    private void webViewLoadUrl(String url) {
        binding.webView.loadUrl(url);
    }

    private void renderWebViewComplete(int finishCount) {
        if (finishCount >= 2) {
            setLoadingVisibility(false);
            setWebViewVisibility(true);
            setPurchaseVisibility(true);
        }
    }

    private void setLoadingVisibility(boolean isVisible) {
        if (isVisible)
            binding.loadingView.getRoot().setVisibility(View.VISIBLE);
        else
            binding.loadingView.getRoot().setVisibility(View.GONE);
    }

    private void setWebViewVisibility(boolean isVisible) {
        if (isVisible)
            binding.webView.setVisibility(View.VISIBLE);
        else
            binding.webView.setVisibility(View.GONE);
    }

    private void setPurchaseVisibility(boolean isVisible) {
        if (isVisible)
            binding.purchaseView.setVisibility(View.VISIBLE);
        else
            binding.purchaseView.setVisibility(View.GONE);
    }

    private void ShowMyDialog(String URl){
        AlertDialog.Builder builder = new AlertDialog.Builder(AuthenticScanActivity.this);
        builder.setCancelable(false);
        builder.setTitle("스캔 정보").setMessage(URl);
        builder.setPositiveButton(R.string.link, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(URl));
                startActivity(intent);
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(AuthenticScanActivity.this, R.color.swebs_main_color1));
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(AuthenticScanActivity.this, R.color.swebs_main_color1));
    }
}