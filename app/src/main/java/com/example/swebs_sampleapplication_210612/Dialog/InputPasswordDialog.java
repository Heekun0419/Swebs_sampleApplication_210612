package com.example.swebs_sampleapplication_210612.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.swebs_sampleapplication_210612.databinding.DialogInputPasswordBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputPasswordDialog extends Dialog {
    private boolean checkPasswordForm;
    private DialogInputPasswordBinding binding;
    private DialogClickStringListener listener;

    public InputPasswordDialog(@NonNull Context context, DialogClickStringListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DialogInputPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       binding.editTextInputPass.setFilters(new InputFilter[] {filterPassword});

        // EditText Watcher - 비밀번호 체크
        binding.editTextInputPass.addTextChangedListener(watcherPassword);

        binding.btnDialogOk.setOnClickListener(v -> {
            listener.onPositiveClick(binding.editTextInputPass.getText().toString());
            dismiss();
        });

        binding.closeDialog.setOnClickListener(v -> {
            listener.onCloseClick();
            dismiss();
        });
    }

    private final TextWatcher watcherPassword = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            renderPasswordForm();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void renderPasswordForm() {
        if (binding.editTextInputPass.getText().toString().length() > 0) {
            Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{6,}$");
            Matcher matcher = pattern.matcher(binding.editTextInputPass.getText().toString());
            if (matcher.find()) {
                binding.textViewDialogEditExplain.setVisibility(View.GONE);
                checkPasswordForm = true;
            } else {
                binding.textViewDialogEditExplain.setVisibility(View.GONE);
                checkPasswordForm = false;
            }
        } else {
            binding.textViewDialogEditExplain.setVisibility(View.GONE);
            checkPasswordForm = false;
        }
    }

    protected InputFilter filterPassword = (source, start, end, dest, dstart, dend) -> {
        Pattern ps = Pattern.compile("^[가-힣ㄱ-ㅎㅏ-ㅣ]+$");
        if (ps.matcher(source).matches()) {
            Toast.makeText(getContext(), "비밀번호에 입력할 수 없는 형식입니다.", Toast.LENGTH_SHORT).show();
            return "";
        }
        return null;
    };
}
