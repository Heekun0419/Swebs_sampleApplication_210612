package com.example.swebs_sampleapplication_210612.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.PermissionDialog;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.databinding.ActivityPermissionCheckBinding;

public class PermissionCheckActivity extends AppCompatActivity {

    private PermissionDialog dialog;
    private ActivityPermissionCheckBinding binding;
    private boolean isChecked;
    public SPmanager sPmanager = new SPmanager(this);

    String[] permission_list = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CALL_PHONE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPermissionCheckBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        isChecked = sPmanager.getPermission();

        dialog = new PermissionDialog(PermissionCheckActivity.this, new DialogClickListener() {
            @Override
            public void onPositiveClick(int position) {
                if(!isChecked){
                    PermissionCheck();
                    sPmanager.setPermissionIsChecked(true);
                }
            }

            @Override
            public void onNegativeClick() {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();


        // 권한 허용 설정부분 버튼 클릭시 나옴
        binding.btnPermissionCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.checkBoxPermissionAll.isChecked() && binding.checkBoxPermissionService.isChecked() ){
                    if(!isChecked){
                        PermissionCheck();
                        sPmanager.setPermissionIsChecked(true);
                    }
                }else if(!binding.checkBoxPermissionService.isChecked()){
                    Toast.makeText(getApplicationContext(), "서비스 권한 허용이 필요합니다.", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "필수권한 허용이 필요합니다.", Toast.LENGTH_LONG).show();
                }
            }
        });

        binding.btnPermissionCheckBack.setOnClickListener(v -> onBackPressed());


        binding.checkBoxPermissionAll.setOnClickListener(v -> {
            if(binding.checkBoxPermissionAll.isChecked()){
                binding.checkBoxPermissionCamera.setChecked(true);
                binding.checkBoxPermissionLocation.setChecked(true);
                binding.checkBoxPermissionPhone.setChecked(true);
                binding.checkBoxPermissionStorage.setChecked(true);
            }else{
                binding.checkBoxPermissionCamera.setChecked(false);
                binding.checkBoxPermissionLocation.setChecked(false);
                binding.checkBoxPermissionPhone.setChecked(false);
                binding.checkBoxPermissionStorage.setChecked(false);
            }
        });

        binding.checkBoxPermissionCamera.setOnClickListener(v -> {
            termsCheck();
        });
        binding.checkBoxPermissionPhone.setOnClickListener(v -> {
            termsCheck();
        });
        binding.checkBoxPermissionLocation.setOnClickListener(v -> {
            termsCheck();
        });
        binding.checkBoxPermissionStorage.setOnClickListener(v -> {
            termsCheck();
        });
    }

    private void termsCheck(){
        if(binding.checkBoxPermissionAll.isChecked()) binding.checkBoxPermissionAll.setChecked(false);
        else if(binding.checkBoxPermissionPhone.isChecked() && binding.checkBoxPermissionLocation.isChecked()
                && binding.checkBoxPermissionCamera.isChecked() && binding.checkBoxPermissionStorage.isChecked()){
            binding.checkBoxPermissionAll.setChecked(true);
        }
    }

    private void PermissionCheck(){

        for (String permission : permission_list){
            int check = checkCallingOrSelfPermission(permission);

            if(check == PackageManager.PERMISSION_DENIED){
                requestPermissions(permission_list,0);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0)
        {
            for (int grantResult : grantResults) {
                //허용됬다면
                if (grantResult == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(getApplicationContext(), "필수 권한이 허용되지 않아 앱을 종료합니다.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isChecked = sPmanager.getPermission();
        if(isChecked) {
            Intent intent = new Intent(PermissionCheckActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
