package com.example.swebs_sampleapplication_210612.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.swebs_sampleapplication_210612.databinding.Dialog1btnBasicBinding;

public class OneButtonBasicDialog extends Dialog {

    private Dialog1btnBasicBinding binding;
    private BasicDialogTextModel model;
    private Context context;
    private DialogClickListener listener;

    public OneButtonBasicDialog(Context context,BasicDialogTextModel model, DialogClickListener listener ) {
        super(context);
        this.model = model;
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Dialog1btnBasicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.textViewTitle.setText(model.getTitle());
        binding.textViewContent.setText(model.getContent());
        binding.textViewPositiveBtn.setText(model.getPositiveButton());

        binding.btnDialogOk.setOnClickListener(v -> {
            listener.onPositiveClick(0);
            dismiss();
        });
        binding.closeDialog.setOnClickListener(v -> {
            listener.onNegativeClick();
            dismiss();
        });
    }
}
