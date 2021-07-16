package com.example.swebs_sampleapplication_210612.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.DialogPermissionBinding;

import java.util.Objects;

public class PermissionDialog extends Dialog {

    private DialogPermissionBinding binding;
    Context context;
    DialogClickListener listener;

    public PermissionDialog(@NonNull Context context, DialogClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogPermissionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding.btnAllPermission.setOnClickListener(v -> {
            listener.onPositiveClick(0);
            dismiss();
        });
        binding.textViewMoreInfoOfPermission.setOnClickListener(v -> {
            listener.onNegativeClick();
            dismiss();
        });
    }
}
