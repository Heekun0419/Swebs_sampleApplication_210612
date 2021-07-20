package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityAuthenticScanBinding;

public class AuthenticScanActivity extends AppCompatActivity {

    private ActivityAuthenticScanBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthenticScanBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        String URL = getIntent().getStringExtra("URL");
        ShowMyDialog(URL);

        binding.btnAuthenticScanBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.btnPurchaseInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PurchaseInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void ShowMyDialog(String URl){
        AlertDialog.Builder builder = new AlertDialog.Builder(AuthenticScanActivity.this);
        builder.setCancelable(false);
        builder.setTitle("스캔 정보").setMessage(URl);
        builder.setPositiveButton(R.string.link, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(URl));
                startActivity(intent);
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(AuthenticScanActivity.this, R.color.swebs_main_color1));
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(AuthenticScanActivity.this, R.color.swebs_main_color1));
    }
}