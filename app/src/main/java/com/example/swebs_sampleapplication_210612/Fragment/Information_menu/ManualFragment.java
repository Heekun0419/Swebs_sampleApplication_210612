package com.example.swebs_sampleapplication_210612.Fragment.Information_menu;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.swebs_sampleapplication_210612.adapter.ManualAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentManual2Binding;


public class ManualFragment extends Fragment {

    private FragmentManual2Binding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewManual.setLayoutManager(linearLayoutManager);
        binding.recyclerViewManual.setAdapter(new ManualAdapter(requireContext()));
        */

        binding = FragmentManual2Binding.inflate(inflater,container,false);

        webViewInit();
        webViewLoadUrl("https://www.swebs.co.kr/apps/swebs_how.html");

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
        binding.webView.setWebChromeClient(new WebChromeClient());
    }

    private void webViewLoadUrl(String url) {
        binding.webView.loadUrl(url);
    }
}