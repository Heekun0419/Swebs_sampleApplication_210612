package com.example.swebs_sampleapplication_210612.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.LanguageDialog;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityScanSettingBinding;

public class ScanSettingActivity extends AppCompatActivity {

    private ActivityScanSettingBinding binding;
    LanguageDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScanSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new LanguageDialog(ScanSettingActivity.this , new DialogClickListener() {
                    @Override
                    public void onPositiveClick(int position) {
                        String pickValue = "";
                        if(position == 0) pickValue = "한국어";
                        else if(position == 1) pickValue = "영어";
                        else if(position == 2) pickValue = "중국어";
                        else if(position == 3) pickValue = "일본어";
                        else pickValue ="한국어";

                        Toast.makeText(getApplicationContext(),"선택 언어 : "+ pickValue ,Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onCloseClick() {
                        dialog.dismiss();
                    }
                });
                dialog.setCancelable(false);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog.show();
            }
        });
    }
}
