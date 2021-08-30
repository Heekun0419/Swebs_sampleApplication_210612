package com.example.swebs_sampleapplication_210612.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.BasicDialogTextModel;
import com.example.swebs_sampleapplication_210612.databinding.DialogEdittextBinding;
import com.example.swebs_sampleapplication_210612.util.EditTextFilter;

public class EditTextDialog extends Dialog {

    private DialogEdittextBinding binding;
    private BasicDialogTextModel model;
    private Context context;
    private DialogClickStringListener listener;
    EditTextFilter filter;

    public EditTextDialog(Context context, BasicDialogTextModel model, DialogClickStringListener listener) {
        super(context);
        this.context = context;
        this.model =model;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogEdittextBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        filter = new EditTextFilter(context);
        binding.edittextContent.setFilters(filter.SetFilter());

        binding.textViewTitle.setText(model.getTitle());
        binding.edittextContent.setHint(model.getContent());
        binding.textViewPositiveBtn.setText(model.getPositiveButton());
        binding.textViewNegativeBtn.setText(model.getNegativeButton());

        binding.btnDialogOk.setOnClickListener(v -> {
            String s = binding.edittextContent.getText().toString();
            if(!s.isEmpty()) {
                listener.onPositiveClick(s);
            } else {
                Toast.makeText(context, "아무것도 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
            }
            dismiss();
        });
        binding.btnDialogBack.setOnClickListener(v -> {
            listener.onNegativeClick();
            dismiss();
        });
        binding.closeDialog.setOnClickListener(v -> {
            listener.onCloseClick();
            dismiss();
        });
    }

}

