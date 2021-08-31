package com.example.swebs_sampleapplication_210612.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.ImagePickerDialog;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityModifyUserInfoBinding;
import com.example.swebs_sampleapplication_210612.util.FilePathFinder;
import com.example.swebs_sampleapplication_210612.util.Listener.onSingleClickListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModifyUserInfoActivity extends AppCompatActivity {

    private ActivityModifyUserInfoBinding binding;
    private ImagePickerDialog dialog;
    private File photoFile;
    private String realPath= null, photoPath =null;
    private Uri imageUri = null;
    FilePathFinder filePathFinder;

    private String selectGender;

    private ImageView profileImage;
    public static final int TAKE_PHOTO_REQUEST = 11;
    public static final int PICK_IMAGE_REQUEST = 12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityModifyUserInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 퍼미션 허용 한번 더 검사
        requestStoragePermission();

        filePathFinder = new FilePathFinder(getApplicationContext());
        //뒤로가기 버튼
        binding.btnInformationActivityBack.setOnClickListener(v -> onBackPressed());
        profileImage = binding.imageViewProfileModify;
        //디폴트 프로필 이미지 생성
        GlideImage("default",profileImage);

        // 성별 설정 버튼 이벤트
        binding.btnGenderFemale.setOnClickListener(v -> {
            selectGender = "female";
            renderGenderButton();
        });

        binding.btnGenderMale.setOnClickListener(v -> {
            selectGender = "male";
            renderGenderButton();
        });

        binding.imageViewProfileModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ImagePickerDialog(ModifyUserInfoActivity.this, new DialogClickListener() {
                    @Override
                    public void onPositiveClick(int position) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        try {
                            photoFile = filePathFinder.createMakefile();
                            photoPath = photoFile.getAbsolutePath();
                            Uri uri = FileProvider.getUriForFile(ModifyUserInfoActivity.this,
                                    "com.example.swebs_sampleapplication_210612.provider",
                                    photoFile);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                            if (takePictureIntent.resolveActivity(ModifyUserInfoActivity.this.getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, TAKE_PHOTO_REQUEST);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNegativeClick() {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    }

                    @Override
                    public void onCloseClick() {

                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog.show();
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(getApplicationContext(), "취소됨", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void requestStoragePermission() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(ModifyUserInfoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 457);
        }
        else if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(ModifyUserInfoActivity.this, new String[]{Manifest.permission.CAMERA}, 456);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    Log.d("photo", photoPath);
                    GlideImage(photoPath, profileImage);
                }
                break;

            case PICK_IMAGE_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    assert data != null;
                    imageUri = data.getData();
                    // You can update this bitmap to your server

                    realPath = filePathFinder.getPath(imageUri);
                    //  Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
                    Log.d("photo", realPath);
                    GlideImage(realPath,profileImage);
                }
                break;
        }
    }


    private void GlideImage(String uri, ImageView view) {
        Glide.with(getApplicationContext()).load(uri)
                .placeholder(R.drawable.ic_profile_basic)
                .override(600, 600)
                .circleCrop()
                .into(view);
    }


    private void renderGenderButton() {
        if (selectGender.equals("female")) {
            binding.btnGenderFemale.setSelected(true);
            binding.textViewMakeAccountGenderFemale.setTextColor(Color.parseColor("#21CCB2"));
            binding.btnGenderMale.setSelected(false);
            binding.textViewMakeAccountGenderMale.setTextColor(Color.parseColor("#C2C3C7"));
        }

        if (selectGender.equals("male")) {
            binding.btnGenderMale.setSelected(true);
            binding.textViewMakeAccountGenderMale.setTextColor(Color.parseColor("#21CCB2"));
            binding.btnGenderFemale.setSelected(false);
            binding.textViewMakeAccountGenderFemale.setTextColor(Color.parseColor("#C2C3C7"));
        }
    }

}