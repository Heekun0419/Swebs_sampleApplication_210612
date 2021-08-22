package com.example.swebs_sampleapplication_210612.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.NumberPickerDialog;
import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.NumberPickerModel;
import com.example.swebs_sampleapplication_210612.databinding.ActivityScanSettingBinding;

public class ScanSettingActivity extends AppCompatActivity {

    private ActivityScanSettingBinding binding;
    NumberPickerDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScanSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new NumberPickerDialog(ScanSettingActivity.this
                        , new NumberPickerModel("언어선택"
                        , new String[]{"한국어", "ENGLISH", "中文"}
                        , 1
                        , "확인", "취소")
                        , new DialogClickListener() {
                    @Override
                    public void onPositiveClick(int position) {
                        String pickValue = "";
                        if(position == 0) pickValue = "한국어";
                        else if(position == 1) pickValue = "영어";
                        else if(position == 2) pickValue = "중국어";
                        else pickValue ="한국어";

                        Toast.makeText(getApplicationContext(),"선택 언어 : "+ pickValue ,Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNegativeClick() {
                    }

                    @Override
                    public void onCloseClick() {
                    }
                });
                dialog.setCancelable(false);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog.show();
            }
        });
    }
}
