package com.example.swebs_sampleapplication_210612.Fragment.MakeAccountFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        binding.webView.loadUrl(html);

        return binding.getRoot();
    }
}