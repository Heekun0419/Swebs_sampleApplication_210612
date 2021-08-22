package com.example.swebs_sampleapplication_210612.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.BasicDialogTextModel;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.OneButtonBasicDialog;
import com.example.swebs_sampleapplication_210612.Dialog.PermissionDialog;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.databinding.ActivityPermissionCheck2Binding;

public class PermissionCheckActivity extends AppCompatActivity {

    private PermissionDialog dialog;
    private ActivityPermissionCheck2Binding binding;
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
        binding = ActivityPermissionCheck2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        isChecked = sPmanager.getPermission();

        dialog = new PermissionDialog(PermissionCheckActivity.this, new DialogClickListener() {
            @Override
            public void onPositiveClick(int position) {
                progressPermission();
            }

            @Override
            public void onNegativeClick() {
                binding.checkBoxPermissionCamera.setChecked(true);
                binding.checkBoxPermissionLocation.setChecked(true);
                binding.checkBoxPermissionPhone.setChecked(true);
                binding.checkBoxPermissionStorage.setChecked(true);
                binding.checkBoxPermissionAll.setChecked(true);

                dialog.dismiss();
            }

            @Override
            public void onCloseClick() {

            }
        });
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            binding.permissionForCamera.setVisibility(View.GONE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            binding.permissionForStorage.setVisibility(View.GONE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            binding.permissionForLocation.setVisibility(View.GONE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
            binding.permissionForPhone.setVisibility(View.GONE);


        // 권한 허용 설정부분 버튼 클릭시 나옴
        binding.btnPermissionCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.checkBoxPermissionAll.isChecked() && binding.checkBoxPermissionService.isChecked() ){
                    progressPermission();
                } else if(!binding.checkBoxPermissionService.isChecked()){
                    Toast.makeText(getApplicationContext(), "서비스 권한 동의를 해주세요.", Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(getApplicationContext(), "필수권한 허용이 필요합니다.", Toast.LENGTH_LONG).show();
                }
            }
        });

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
        if (binding.checkBoxPermissionAll.isChecked()) {
            binding.checkBoxPermissionAll.setChecked(false);
        } else if (binding.checkBoxPermissionPhone.isChecked()
                && binding.checkBoxPermissionLocation.isChecked()
                && binding.checkBoxPermissionCamera.isChecked()
                && binding.checkBoxPermissionStorage.isChecked()){
            binding.checkBoxPermissionAll.setChecked(true);
        }
    }

    private void progressPermission(){
        for (String permission : permission_list){
            int check = checkCallingOrSelfPermission(permission);

            if(check == PackageManager.PERMISSION_DENIED){
                requestPermissions(permission_list,0);
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 0) {
            if (grantResults.length > 0) {
                int permissionIndex = 0;
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED
                        && !shouldShowRequestPermissionRationale(permissions[permissionIndex++])) {
                        openApplicationSetting();
                        return;
                    }
                }

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    StartMainActivity();
                } else {
                    Toast.makeText(getApplicationContext(), "필수 권한이 허용되지 않아 앱을 종료합니다.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

        }
    }

    private void openApplicationSetting() {
        OneButtonBasicDialog oneButtonBasicDialog = new OneButtonBasicDialog(this,
                new BasicDialogTextModel("권한 설정", "두번 이상 권한을 거부 하셨습니다.\n시스템 설정에서 권한 허용을 해주세요.", "확인", ""),
                new DialogClickListener() {
                    @Override
                    public void onPositiveClick(int position) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onNegativeClick() {

                    }

                    @Override
                    public void onCloseClick() {

                    }
                });
        oneButtonBasicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        oneButtonBasicDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        oneButtonBasicDialog.show();
    }

    private void StartMainActivity(){
        Intent intent = new Intent(PermissionCheckActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sPmanager.getPermission()){
            StartMainActivity();
        }
    }
}
