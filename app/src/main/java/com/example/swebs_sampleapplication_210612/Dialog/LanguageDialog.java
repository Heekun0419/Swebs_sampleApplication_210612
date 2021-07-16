package com.example.swebs_sampleapplication_210612.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;

import com.example.swebs_sampleapplication_210612.databinding.DialogLanguageBinding;

import java.util.Objects;

public class LanguageDialog extends Dialog {

    private DialogLanguageBinding binding;
    Context context;
    DialogClickListener listener;
    NumberPicker picker;

    public LanguageDialog(@NonNull Context context,DialogClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DialogLanguageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        picker = binding.numberPicker;
        picker.setMinValue(0);
        picker.setMaxValue(3);
        picker.setDisplayedValues(new String[]{
                "한국어", "ENGLISH", "中文", "日本語"});


        binding.btnLanguageOk.setOnClickListener(v -> {
            listener.onPositiveClick(picker.getValue());
            dismiss();
        });
        binding.btnLanguageBack.setOnClickListener(v -> {
            listener.onNegativeClick();
            dismiss();
        });
    }

}
