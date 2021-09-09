package com.example.swebs_sampleapplication_210612.Fragment;

import static com.example.swebs_sampleapplication_210612.Activity.ModifyUserInfoActivity.PICK_IMAGE_REQUEST;
import static com.example.swebs_sampleapplication_210612.Activity.ModifyUserInfoActivity.TAKE_PHOTO_REQUEST;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Activity.ModifyUserInfoActivity;
import com.example.swebs_sampleapplication_210612.Activity.TermsActivity;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.ImagePickerDialog;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.FragmentCloneReportBinding;
import com.example.swebs_sampleapplication_210612.util.FilePathFinder;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.io.File;
import java.io.IOException;

public class CloneReportFragment extends Fragment {

    private FragmentCloneReportBinding binding;

    private ImagePickerDialog dialog;
    private File photoFile;
    private String realPath= null, photoPath =null;
    private Uri imageUri = null;
    FilePathFinder filePathFinder;
    MaterialDatePicker datePicker;

    String date ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filePathFinder = new FilePathFinder(requireActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment'
        binding = FragmentCloneReportBinding.inflate(inflater,container,false);

        binding.btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select Date")
                        .build();

                datePicker.addOnPositiveButtonClickListener(selection -> {
                    date =  datePicker.getHeaderText();
                    binding.textViewPurchaseDate.setText(date);
                });
                datePicker.show(getParentFragmentManager(),"datePicker");
            }
        });

        // 정보제공 약관 동의
        binding.termsShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), TermsActivity.class);
                intent.putExtra("url","http://3.35.249.81/ToS/ToS_I.html");
                startActivity(intent);
            }
        });


        binding.imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ImagePickerDialog(requireContext(), new DialogClickListener() {
                    @Override
                    public void onPositiveClick(int position) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        try {
                            photoFile = filePathFinder.createMakefile();
                            photoPath = photoFile.getAbsolutePath();
                            Uri uri = FileProvider.getUriForFile(requireContext(),
                                    "com.example.swebs_sampleapplication_210612.provider",
                                    photoFile);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                            if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
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

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    GlideImage(photoPath, binding.imageViewProfile);
                }
                break;

            case PICK_IMAGE_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    assert data != null;
                    imageUri = data.getData();
                    // You can update this bitmap to your server
                    realPath = filePathFinder.getPath(imageUri);
                    //  Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri)
                    GlideImage(realPath,binding.imageViewProfile);
                }
                break;
        }
    }

    private void GlideImage(String uri, ImageView view) {
        Glide.with(requireActivity()).load(uri)
                .override(900, 600)
                .fitCenter()
                .into(view);

        binding.imageViewProfileCamera.setVisibility(View.GONE);
        binding.textViewImageProfile.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}