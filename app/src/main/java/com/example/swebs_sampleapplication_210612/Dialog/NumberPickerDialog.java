package com.example.swebs_sampleapplication_210612.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;

import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.NumberPickerModel;
import com.example.swebs_sampleapplication_210612.databinding.DialogLanguageBinding;

import java.util.Calendar;
import java.util.Objects;

public class NumberPickerDialog extends Dialog {

    private DialogLanguageBinding binding;
    private NumberPickerModel model;
    Context context;
    DialogClickListener listener;
    NumberPicker picker;
    int defaultValue;

    private Calendar cal = Calendar.getInstance();

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
        picker = binding.numberPicker;

        if(model.getTitle().equals("출생년도")) {
            int year = cal.get(Calendar.YEAR);
            picker.setMinValue(1900);
            picker.setMaxValue(year);
            picker.setValue(1990);
        }else{
            String[] list = model.getList();
            //numberPicker 세팅
            picker.setMinValue(0);
            picker.setMaxValue(list.length-1);
            picker.setDisplayedValues(list);
        }


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
