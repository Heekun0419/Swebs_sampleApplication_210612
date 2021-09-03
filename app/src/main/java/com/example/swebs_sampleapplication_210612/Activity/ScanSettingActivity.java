package com.example.swebs_sampleapplication_210612.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.NumberPickerDialog;
import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.NumberPickerModel;
import com.example.swebs_sampleapplication_210612.databinding.ActivityScanSettingBinding;

public class ScanSettingActivity extends AppCompatActivity {

    private ActivityScanSettingBinding binding;
    NumberPickerDialog dialog;
    SPmanager sPmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScanSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sPmanager = new SPmanager(this);

        // 뒤로가기 버튼 클릭시
        binding.btnTopMenuBack.setOnClickListener(v ->
                onBackPressed());

        // 스위치 렌더링
        FirstSwitchSettings();
        RenderSwitch();

        binding.btnChangeLanguage.setOnClickListener(v -> {
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

                    binding.textViewLanguage.setText(pickValue);
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
        });
    }

    // 초기에 전부다 Null 값일 경우
    private boolean isAllNonChecked(){
        return sPmanager.getScanAnimation() == null
                && sPmanager.getScanSound() == null
                && sPmanager.getScanAutoFocus() == null
                && sPmanager.getScanHistoryOnOff() == null;
    }

    // 처음엔 전체 다 켜짐으로 설정한다.
    private void FirstSwitchSettings(){
        if(isAllNonChecked()){
            binding.switchAnimation.setChecked(true);
            binding.switchAutoFocus.setChecked(true);
            binding.switchScanHistory.setChecked(true);
            binding.switchSound.setChecked(true);
        }
    }

    private void RenderSwitch(){
        // 오토포커스
        binding.switchAutoFocus.setChecked(!sPmanager.getScanAutoFocus().equals("nonCheck"));
        // 스캔히스토리
        binding.switchScanHistory.setChecked(!sPmanager.getScanHistoryOnOff().equals("nonCheck"));
        // 사운드
        binding.switchSound.setChecked(!sPmanager.getScanSound().equals("nonCheck"));
        // 애니메이션
        binding.switchAnimation.setChecked(!sPmanager.getScanAnimation().equals("nonCheck"));
    }


    @Override
    protected void onResume() {
        super.onResume();

        //사운드 스위치 리스너
        binding.switchSound.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.switchSound.setChecked(isChecked);
            if(isChecked) sPmanager.setScanSound("check");
            else sPmanager.setScanSound("nonCheck");
        });

        //스캔 히스토리 리스너
        binding.switchScanHistory.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.switchScanHistory.setChecked(isChecked);
            if(isChecked) sPmanager.setScanHistoryOnOff("check");
            else sPmanager.setScanHistoryOnOff("nonCheck");
        });

        //스캔 애니메이션 리스너
        binding.switchAnimation.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.switchAnimation.setChecked(isChecked);
            if(isChecked) sPmanager.setScanAnimation("check");
            else sPmanager.setScanAnimation("nonCheck");
        });

        //스캔 오토포커스 리스너
        binding.switchAutoFocus.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.switchAutoFocus.setChecked(isChecked);
            if(isChecked) sPmanager.setScanAutoFocus("check");
            else sPmanager.setScanAutoFocus("nonCheck");
        });

    }
}
