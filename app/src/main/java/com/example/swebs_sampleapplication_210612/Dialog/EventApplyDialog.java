package com.example.swebs_sampleapplication_210612.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.BasicDialogTextModel;
import com.example.swebs_sampleapplication_210612.databinding.Dialog2btnBasicBinding;
import com.example.swebs_sampleapplication_210612.databinding.DialogEventApplyBinding;

public class EventApplyDialog extends Dialog {
    private DialogEventApplyBinding binding;
    private DialogClickListener listener;
    private Context context;
    private BasicDialogTextModel model;

    public EventApplyDialog(@NonNull Context context, DialogClickListener listener, BasicDialogTextModel model) {
        super(context);
        this.context = context;
        this.listener = listener;
        this.model = model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogEventApplyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.textViewContent.setText(model.getContent());
        binding.textViewNegativeBtn.setText(model.getNegativeButton());
        binding.textViewTitle.setText(model.getTitle());
        binding.textViewPositiveBtn.setText(model.getPositiveButton());

        binding.btnDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPositiveClick(0);
                dismiss();
            }
        });

        binding.btnDialogBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNegativeClick();
                dismiss();
            }
        });

        binding.closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCloseClick();
                dismiss();
            }
        });
    }
}
