package com.example.swebs_sampleapplication_210612.Fragment.Information_menu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.swebs_sampleapplication_210612.databinding.FragmentPurchaseQuestion2Binding;


public class PurchaseQuestionFragment  extends Fragment {

    private FragmentPurchaseQuestion2Binding binding;
    private ValueCallback<Uri[]> mFileCallback;

    private ActivityResultLauncher<Intent> mGetPicture = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            if (mFileCallback != null)
                mFileCallback.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(result.getResultCode(), result.getData()));
        } else {
            mFileCallback.onReceiveValue(null);
        }
    });

    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPurchaseQuestion2Binding.inflate(inflater,container,false);


        webViewInit();
        webViewLoadUrl("https://www.swebs.co.kr/apps/swebs_inquiry.html");

        return binding.getRoot();
    }



    @SuppressLint("SetJavaScriptEnabled")
    private void webViewInit() {
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setLoadWithOverviewMode(true);
        binding.webView.getSettings().setDomStorageEnabled(true);

        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);
            }
        });

        binding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                intent.setType("*/*");  //모든 contentType 파일 표시
                //intent.setType("image/*");  //contentType 이 image 인 파일만 표시

                if (mFileCallback != null)
                    mFileCallback = null;

                mFileCallback = filePathCallback;
                mGetPicture.launch(intent);

                return true;
            }
        });
    }

    private void webViewLoadUrl(String url) {
        binding.webView.loadUrl(url);
    }
}