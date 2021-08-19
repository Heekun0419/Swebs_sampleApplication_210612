package com.example.swebs_sampleapplication_210612.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.swebs_sampleapplication_210612.databinding.DialogRecommendCodeBinding;

public class RecommendCodeDialog extends Dialog {

    private DialogRecommendCodeBinding binding;
    private Context context;
    private DialogClickListener listener;

    public RecommendCodeDialog(@NonNull Context context, DialogClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogRecommendCodeBinding.inflate(getLayoutInflater());
        setContentView( binding.getRoot());

        binding.layoutShareCode.setOnClickListener(v -> {
            listener.onPositiveClick(0);
            dismiss();
        });
        binding.layoutCodeCopy.setOnClickListener(v -> {
            listener.onNegativeClick();
            dismiss();
        });
        binding.btnCloseDialog.setOnClickListener(v -> {
            listener.onCloseClick();
            dismiss();
        });

    }
}
