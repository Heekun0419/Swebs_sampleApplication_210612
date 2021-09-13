package com.example.swebs_sampleapplication_210612.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.ImagePickerDialog;

import com.example.swebs_sampleapplication_210612.ViewModel.ReviewPhotoViewModel;
import com.example.swebs_sampleapplication_210612.adapter.Listener.HistoryListClickListener;
import com.example.swebs_sampleapplication_210612.adapter.WriteReviewPhotoAdapter;

import com.example.swebs_sampleapplication_210612.databinding.ActivityReviewWriteBinding;
import com.example.swebs_sampleapplication_210612.util.FilePathFinder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ReviewWriteActivity extends AppCompatActivity implements HistoryListClickListener {
    private ActivityReviewWriteBinding binding;
    private ReviewPhotoViewModel viewModel;
    FilePathFinder filePathFinder = new FilePathFinder(this);
    ImagePickerDialog dialog;
    private ArrayList<String> list = new ArrayList<>();

    private File photoFile;
    private String realPath= null, photoPath =null;
    private Uri imageUri = null;

    public static final int TAKE_PHOTO_REQUEST = 11;
    public static final int PICK_IMAGE_REQUEST = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnReviewActivityBack.setOnClickListener(v -> onBackPressed());

        viewModel = new ReviewPhotoViewModel(getApplication());
        list.add("");
        viewModel.setLiveUrlList(list);

        // rating Bar 텍스트 리스너 달기
        binding.ratingBarReview.setOnRatingBarChangeListener(
                (ratingBar, rating, fromUser) -> binding.textViewRatingNum.setText(""+rating));

        viewModel.getLiveUrlList().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                recyclerInit(strings);
            }
        });

        binding.constraintLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void recyclerInit(ArrayList<String> list){
        //recycler View 추가
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        WriteReviewPhotoAdapter adapter = new WriteReviewPhotoAdapter(this,list,this);
        binding.recyclerViewReviewAddPicture.setLayoutManager(linearLayoutManager);
        binding.recyclerViewReviewAddPicture.setAdapter(adapter);

    }

    @Override
    public void positiveButtonClicked(ImageButton button, int position, @Nullable String code) {
        dialog = new ImagePickerDialog(this, new DialogClickListener() {
            @Override
            public void onPositiveClick(int position) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    photoFile = filePathFinder.createMakefile();
                    Uri uri = FileProvider.getUriForFile(getApplicationContext(),
                            "com.example.swebs_sampleapplication_210612.provider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, TAKE_PHOTO_REQUEST);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNegativeClick() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_PICK);
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

    @Override
    public void negativeButtonClicked(ImageButton button, int position, @Nullable String code) {
        list.remove(position);
        viewModel.setLiveUrlList(list);
    }

    @Override
    public void companyNameClicked(TextView textView, int position, @Nullable String code) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    list.add(photoPath);
                    viewModel.setLiveUrlList(list);
                }
                break;

            case PICK_IMAGE_REQUEST:
                if (resultCode == Activity.RESULT_OK && data!= null) {
                    if(data.getClipData() != null){
                        ClipData clipData = data.getClipData();
                        int count = clipData.getItemCount();
                        for(int i =0; i<count; i++){
                            imageUri = clipData.getItemAt(i).getUri();
                            // You can update this bitmap to your server
                            realPath = filePathFinder.getPath(imageUri);
                            //  Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
                            Log.d("photo", realPath);
                            list.add(realPath);
                            viewModel.setLiveUrlList(list);
                        }
                    }
                }
                break;
        }
    }
}