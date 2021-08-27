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

import com.example.swebs_sampleapplication_210612.Dialog.TwoButtonBasicDialog;
import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.BasicDialogTextModel;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.OneButtonBasicDialog;
import com.example.swebs_sampleapplication_210612.Dialog.PermissionDialog;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.databinding.ActivityPermissionCheck2Binding;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class PermissionCheckActivity extends AppCompatActivity {

    private ActivityPermissionCheck2Binding binding;
    public SPmanager sPmanager = new SPmanager(this);

    private final String DIALOG_TITLE = "권한 허용 안내";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPermissionCheck2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        PermissionDialog dialog = new PermissionDialog(this, new DialogClickListener() {
            @Override
            public void onPositiveClick(int position) {
                binding.checkBoxPermissionAll.setChecked(true);
                binding.checkBoxPermissionService.setChecked(true);
                allTermsCheck();
                startPermission();
            }

            @Override
            public void onNegativeClick() {
                binding.checkBoxPermissionAll.setChecked(true);
                allTermsCheck();
            }

            @Override
            public void onCloseClick() {

            }
        });
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();


        binding.btnPermissionCheck.setOnClickListener(v -> {
            if (!binding.checkBoxPermissionAll.isChecked())
                dialogTermsNoneCheck(DIALOG_TITLE, "필수 권한 허용이 필요합니다.");
            else if (!binding.checkBoxPermissionService.isChecked())
                dialogTermsNoneCheck(DIALOG_TITLE, "서비스 권한을 동의 해주세요.");
            else
                startPermission();
        });

        binding.checkBoxPermissionPhone.setOnClickListener(v -> termsCheck());
        binding.permissionForPhone.setOnClickListener(v -> {
            binding.checkBoxPermissionPhone.setChecked(!binding.checkBoxPermissionPhone.isChecked());
            termsCheck();
        });

        binding.checkBoxPermissionCamera.setOnClickListener(v -> termsCheck());
        binding.permissionForCamera.setOnClickListener(v -> {
            binding.checkBoxPermissionCamera.setChecked(!binding.checkBoxPermissionCamera.isChecked());
            termsCheck();
        });

        binding.checkBoxPermissionLocation.setOnClickListener(v -> termsCheck());
        binding.permissionForLocation.setOnClickListener(v -> {
            binding.checkBoxPermissionLocation.setChecked(!binding.checkBoxPermissionLocation.isChecked());
            termsCheck();
        });

        binding.checkBoxPermissionStorage.setOnClickListener(v -> termsCheck());
        binding.permissionForStorage.setOnClickListener(v -> {
            binding.checkBoxPermissionStorage.setChecked(!binding.checkBoxPermissionStorage.isChecked());
            termsCheck();
        });

        binding.checkBoxPermissionAll.setOnClickListener(v -> allTermsCheck());
        binding.layoutPermissionAll.setOnClickListener(v -> {
            binding.checkBoxPermissionAll.setChecked(!binding.checkBoxPermissionAll.isChecked());
            allTermsCheck();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        renderPermissionList();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            StartMainActivity();
        }
    }

    private void renderPermissionList() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            binding.permissionForCamera.setVisibility(View.GONE);
        else
            binding.permissionForCamera.setVisibility(View.VISIBLE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            binding.permissionForStorage.setVisibility(View.GONE);
        else
            binding.permissionForStorage.setVisibility(View.VISIBLE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            binding.permissionForLocation.setVisibility(View.GONE);
        else
            binding.permissionForLocation.setVisibility(View.VISIBLE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
            binding.permissionForPhone.setVisibility(View.GONE);
        else
            binding.permissionForPhone.setVisibility(View.VISIBLE);
    }

    private void allTermsCheck() {
        if (binding.checkBoxPermissionAll.isChecked()) {
            binding.checkBoxPermissionCamera.setChecked(true);
            binding.checkBoxPermissionLocation.setChecked(true);
            binding.checkBoxPermissionPhone.setChecked(true);
            binding.checkBoxPermissionStorage.setChecked(true);
        } else {
            binding.checkBoxPermissionCamera.setChecked(false);
            binding.checkBoxPermissionLocation.setChecked(false);
            binding.checkBoxPermissionPhone.setChecked(false);
            binding.checkBoxPermissionStorage.setChecked(false);
        }
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

    private void startPermission() {
        TedPermission.with(this)
                    .setPermissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.CALL_PHONE
                    )
                    .setPermissionListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            StartMainActivity();
                        }

                        @Override
                        public void onPermissionDenied(List<String> deniedPermissions) {
                            dialogPermissionDenied(DIALOG_TITLE, "권한 허용이 되지 않았습니다.\n[설정] 에서 직접 권한 허용할 수 있습니다.\n\n(2회 이상 거부시 [설정] 에서 허용)");
                        }
                    })
                    .check();
    }

    private void StartMainActivity() {
        Intent intent = new Intent(PermissionCheckActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void dialogPermissionDenied(String title, String content) {
        TwoButtonBasicDialog dialog = new TwoButtonBasicDialog(this
                , new BasicDialogTextModel(title, content, "설정", "확인")
                , new DialogClickListener() {
            @Override
            public void onPositiveClick(int position) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onNegativeClick() {
                renderPermissionList();;
            }

            @Override
            public void onCloseClick() {

            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
    }

    private void dialogTermsNoneCheck(String title, String content) {
        OneButtonBasicDialog dialog = new OneButtonBasicDialog(this
                , new BasicDialogTextModel(title, content, "확인", "")
                , new DialogClickListener() {
            @Override
            public void onPositiveClick(int position) {
            }

            @Override
            public void onNegativeClick() {
            }

            @Override
            public void onCloseClick() {

            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
    }
}
