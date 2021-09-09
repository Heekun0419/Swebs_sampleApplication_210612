package com.example.swebs_sampleapplication_210612.Fragment.MakeAccountFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.FragmentTermsBinding;

public class TermsFragment extends Fragment {

    private FragmentTermsBinding binding;
    private static final String ARG_PARAM1 = "url";
    private String html;

    public static TermsFragment newInstance(String html) {
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, html);
        TermsFragment fragment = new TermsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            html = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTermsBinding.inflate(inflater,container,false);
        webViewInit();
        binding.webView.loadUrl(html);
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
}