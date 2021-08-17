package com.example.swebs_sampleapplication_210612.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;

import com.example.swebs_sampleapplication_210612.databinding.DialogLanguageBinding;

import java.util.ArrayList;
import java.util.Objects;

public class NumberPickerDialog extends Dialog {

    private DialogLanguageBinding binding;
    private NumberPickerModel model;
    Context context;
    DialogClickListener listener;
    NumberPicker picker;

    public NumberPickerDialog(@NonNull Context context, NumberPickerModel model, DialogClickListener listener) {
        super(context);
        this.model = model;
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DialogLanguageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding.textViewTitle.setText(model.getTitle());
        binding.textViewNegativeBtn.setText(model.getNegative_btn());
        binding.textViewPositiveBtn.setText(model.getPositive_btn());


        String[] list = model.getList();
        //numberPicker 세팅
        picker = binding.numberPicker;
        picker.setMinValue(0);
        picker.setMaxValue(list.length-1);
        picker.setDisplayedValues(list);

        binding.btnLanguageOk.setOnClickListener(v -> {
            // picker.getValue 로 Position 받아올 수 있음.
            listener.onPositiveClick(picker.getValue());
            dismiss();
        });
        binding.btnLanguageBack.setOnClickListener(v -> {
            listener.onNegativeClick();
            dismiss();
        });
        binding.btnCloseDialog.setOnClickListener(v -> {
            listener.onCloseClick();
            dismiss();
        });
    }

}
