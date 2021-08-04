package com.example.swebs_sampleapplication_210612.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.NonNull;

import com.example.swebs_sampleapplication_210612.databinding.DialogFindPasswordBinding;
import com.example.swebs_sampleapplication_210612.databinding.DialogPermissionBinding;

import java.util.Objects;

public class FindPasswordDialog extends Dialog {

    private DialogFindPasswordBinding binding;
    Context context;
    DialogClickListener listener;

    public FindPasswordDialog(@NonNull Context context, DialogClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogFindPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String content = binding.textViewFindPass.getText().toString();
        SpannableString spannableString = new SpannableString(content);
        String word = "임시비밀번호";
        int start = content.indexOf(word);
        int end = start + word.length();
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#21CCB2")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.textViewFindPass.setText(spannableString);

        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding.btnFindPassOk.setOnClickListener(v -> {
            listener.onPositiveClick(0);
            dismiss();
        });
        binding.btnFindPassBack.setOnClickListener(v -> {
            listener.onNegativeClick();
            dismiss();
        });
    }
}
