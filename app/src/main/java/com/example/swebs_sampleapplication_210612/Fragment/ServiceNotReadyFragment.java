package com.example.swebs_sampleapplication_210612.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.swebs_sampleapplication_210612.Activity.ScanHistoryActivity;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.FragmentServiceNotReadyBinding;


public class ServiceNotReadyFragment extends Fragment {
    private FragmentServiceNotReadyBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentServiceNotReadyBinding.inflate(inflater,container,false);

        String content = getString(R.string.as_not_ready);
        SpannableString spannableString = new SpannableString(content);
        String word = "AS관련";
        int start = content.indexOf(word);
        int end = start + word.length();
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#93E3BE")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.textViewServiceNotReady.setText(spannableString);

        binding.btnServiceNotReadyBack.setOnClickListener(v ->
                ((ScanHistoryActivity)requireActivity()).onBackPressed());

        return binding.getRoot();

    }
}
