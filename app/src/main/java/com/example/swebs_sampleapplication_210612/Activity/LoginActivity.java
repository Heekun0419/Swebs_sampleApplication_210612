package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.PermissionDialog;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        PermissionDialog dialog = new PermissionDialog(LoginActivity.this, new DialogClickListener() {
            @Override
            public void onPositiveClick(int position) {
                Intent intent = new Intent(LoginActivity.this, PermissionCheckActivity.class);
                startActivity(intent);
            }

            @Override
            public void onNegativeClick() {
                Intent intent = new Intent(LoginActivity.this, PermissionCheckActivity.class);
                startActivity(intent);
            }
        });
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();

        binding.btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        });

        binding.textViewMakeAccount.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),MakeAccountActivity.class);
            startActivity(intent);
        });
    }
}