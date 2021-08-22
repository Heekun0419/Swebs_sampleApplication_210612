package com.example.swebs_sampleapplication_210612.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;

import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.NumberPickerModel2;
import com.example.swebs_sampleapplication_210612.databinding.DialogLanguageBinding;

import java.util.Calendar;
import java.util.Objects;

public class NumberPickerDialog2 extends Dialog {

    private DialogLanguageBinding binding;
    private NumberPickerModel2 model;
    Context context;
    DialogClickListener listener;
    NumberPicker picker;
    int defaultValue;

    private Calendar cal = Calendar.getInstance();

    public NumberPickerDialog2(@NonNull Context context, NumberPickerModel2 model, DialogClickListener listener) {
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
        picker = binding.numberPicker;

        String[] viewList = model.getList().toArray(new String[model.getList().size()]);

        picker.setMinValue(0);
        picker.setMaxValue(viewList.length-1);
        picker.setDisplayedValues(viewList);
        picker.setValue(model.getDefaultValue());

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
