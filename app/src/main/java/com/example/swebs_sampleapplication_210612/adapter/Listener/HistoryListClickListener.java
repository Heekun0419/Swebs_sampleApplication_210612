package com.example.swebs_sampleapplication_210612.adapter.Listener;

import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

public interface HistoryListClickListener {
    void positiveButtonClicked(ImageButton button, int position,@Nullable String code);
    void negativeButtonClicked(ImageButton button, int position,@Nullable String code);
    void companyNameClicked(TextView textView, int position, @Nullable String code);
}
