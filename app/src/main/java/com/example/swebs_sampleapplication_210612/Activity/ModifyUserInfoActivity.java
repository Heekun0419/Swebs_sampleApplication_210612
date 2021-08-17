package com.example.swebs_sampleapplication_210612.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

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

        //뒤로가기 버튼
        binding.btnInformationActivityBack.setOnClickListener(v -> onBackPressed());
        profileImage = binding.imageViewProfileModify;
        //디폴트 프로필 이미지 생성
        GlideImage("default",profileImage);

        binding.imageViewProfileModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ImagePickerDialog(ModifyUserInfoActivity.this, new DialogClickListener() {
                    @Override
                    public void onPositiveClick(int position) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        try {
                            photoFile = createMakefile();
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

    private File createMakefile() throws IOException {
        String imageFileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = File.createTempFile(
                imageFileName,".jpg",storageDir
        );
        photoPath = file.getAbsolutePath();
        return file;
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
                    imageUri = data.getData();
                    // You can update this bitmap to your server
                    realPath = getPath(imageUri);
                    //  Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
                    Log.d("photo", realPath);
                    GlideImage(realPath,profileImage);
                }
                break;
        }
    }

    private String getPath(Uri uri) {
        String result;
        Cursor cursor = getContentResolver().query(uri,null, null, null, null);
        if(cursor == null){
            result = uri.getPath();
        }else{
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(columnIndex);
            cursor.close();
        }
        return result;
    }

    private void GlideImage(String uri, ImageView view) {
        Glide.with(getApplicationContext()).load(uri)
                .placeholder(R.drawable.ic_profile_basic)
                .override(600, 600)
                .circleCrop()
                .into(view);
    }
}