package com.example.swebs_sampleapplication_210612.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.example.swebs_sampleapplication_210612.databinding.DialogImagePickerBinding;

public class ImagePickerDialog extends Dialog {

    private DialogImagePickerBinding binding;
    private Context context;
    private DialogClickListener listener;

    public ImagePickerDialog(Context context, DialogClickListener listener ) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogImagePickerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 카메라 촬영
        binding.linearLayout22.setOnClickListener(v -> {
            listener.onPositiveClick(0);
            dismiss();
        });

        // 이미지 가져오기
        binding.linearLayout23.setOnClickListener(v -> {
            listener.onNegativeClick();
            dismiss();
        });

        // x 버튼
        binding.closeDialog.setOnClickListener(v -> {
            listener.onCloseClick();
            dismiss();
        });
    }
}
