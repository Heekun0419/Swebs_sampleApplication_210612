package com.example.swebs_sampleapplication_210612.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.Dialog2btnBasicBinding;

public class TwoButtonBasicDialog extends Dialog {

    private Dialog2btnBasicBinding binding;
    private DialogClickListener listener;
    private Context context;
    private BasicDialogTextModel model;

    public TwoButtonBasicDialog(Context context,BasicDialogTextModel model ,DialogClickListener listener) {
        super(context);
        this.listener = listener;
        this.context = context;
        this.model = model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Dialog2btnBasicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.textViewTitle.setText(model.getTitle());
        binding.textViewContent.setText(model.getContent());
        binding.textViewPositiveBtn.setText(model.getPositiveButton());
        binding.textViewNegativeBtn.setText(model.getNegativeButton());

        binding.btnDialogOk.setOnClickListener(v -> {
            listener.onPositiveClick(0);
            dismiss();
        });
        binding.btnDialogBack.setOnClickListener(v -> {
            listener.onNegativeClick();
            dismiss();
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
