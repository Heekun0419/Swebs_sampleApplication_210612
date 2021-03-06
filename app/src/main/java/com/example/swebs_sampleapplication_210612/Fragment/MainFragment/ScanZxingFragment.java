package com.example.swebs_sampleapplication_210612.Fragment.MainFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.example.swebs_sampleapplication_210612.Activity.AuthenticScanActivity;
import com.example.swebs_sampleapplication_210612.Activity.MainActivity;
import com.example.swebs_sampleapplication_210612.Activity.QRLinkActivity;
import com.example.swebs_sampleapplication_210612.Activity.ScanSettingActivity;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.ScanDataPushModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.OneButtonBasicDialog;
import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.BasicDialogTextModel;
import com.example.swebs_sampleapplication_210612.databinding.FragmentScanZxingBinding;
import com.example.swebs_sampleapplication_210612.util.GpsTracker;
import com.example.swebs_sampleapplication_210612.util.Listener.onScanListener;
import com.example.swebs_sampleapplication_210612.util.ScanController;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.ViewfinderView;
import com.journeyapps.barcodescanner.camera.CameraSettings;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanZxingFragment extends Fragment {
    private FragmentScanZxingBinding binding;

    private SwebsAPI retroAPI;
    private SPmanager sPmanager;

    private CaptureManager captureManager;
    private CameraSettings cameraSettings;
    private boolean firstResume = false;
    private boolean isFlashOn;
    private boolean isScanning;

    private final Animation fadeIn = new AlphaAnimation(0,1);

    public ScanZxingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);
        sPmanager = new SPmanager(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentScanZxingBinding.inflate(getLayoutInflater());

        binding.tutorialScanPage.getRoot().setVisibility(View.GONE);

        String content = binding.textVIewScanExplain.getText().toString();
        SpannableString spannableString = new SpannableString(content);
        String word = "QR?????????";
        int start = content.indexOf(word);
        int end = start + word.length();
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#93E3BE")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.textVIewScanExplain.setText(spannableString);

        ViewfinderView viewfinderView = binding.zxingBarcodeScanner.getViewFinder();
        try {
            Field scannerAlphaField = viewfinderView.getClass().getDeclaredField("SCANNER_ALPHA");
            scannerAlphaField.setAccessible(true);
            scannerAlphaField.set(viewfinderView, new int[2]);
        } catch (Exception e) {

        }

        binding.zxingBarcodeScanner.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                if (isScanning) {
                    isScanning = false;
                    progressScanData(result);
                }
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });

        // ????????? ?????? ???????????? ??????
        binding.includedAppbarScan.imageView19.setVisibility(View.INVISIBLE);
        binding.includedAppbarScan.imageButton2.setBackgroundColor(Color.TRANSPARENT);

        // ??????????????? ????????? ??????
        binding.includedAppbarScan.imageButton.setOnClickListener(v -> ((MainActivity)requireActivity()).openDrawer());

        // ???????????? ????????? ??????
        binding.tutorialScanPage.textViewScanTutorialClose.setOnClickListener(v -> {
            binding.tutorialScanPage.getRoot().setVisibility(View.GONE);
            sPmanager.setScanTutorialExit(true);
        });

        binding.tutorialScanPage.imageButton5.setOnClickListener(v -> {
            binding.tutorialScanPage.getRoot().setVisibility(View.GONE);
            sPmanager.setScanTutorialExit(true);
        });

        // Bottom Sheet ??????
        binding.includedAppbarScan.imageButton2.setOnClickListener(v ->
                ((MainActivity)requireActivity()).BottomSheetOpen());


        // Setting Button
        binding.btnScanSetting.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ScanSettingActivity.class);
            startActivity(intent);
        });

        // Flash Button
        if (!hasFlash()) binding.btnScanFlash.setVisibility(View.GONE);
        binding.btnScanFlash.setOnClickListener(v -> {
            if (isFlashOn) {
                binding.zxingBarcodeScanner.setTorchOff();
                isFlashOn = false;
            } else {
                binding.zxingBarcodeScanner.setTorchOn();
                isFlashOn = true;
            }
        });

        //binding.zxingBarcodeScanner.getBarcodeView().getCameraSettings().setAutoFocusEnabled(false);
        captureManager = new CaptureManager(requireActivity(), binding.zxingBarcodeScanner);
        captureManager.initializeFromIntent(getActivity().getIntent(), savedInstanceState);
        //captureManager.decode();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        captureManager.onResume();

        binding.zxingBarcodeScanner.setTorchOff();
        isFlashOn = false;

        isScanning = true;

        renderTutorial();
    }


    @SuppressLint("ClickableViewAccessibility")
    private void renderTutorial() {
        Log.d("render", "tutorial is gone");
        if(binding.tutorialScanPage.getRoot().getVisibility() == View.GONE
                && !sPmanager.getScanTutorialExit()){

            Log.d("render", "tutorial start  SP date : " + sPmanager.getScanTutorialExit());
            fadeIn.setDuration(700);
            binding.tutorialScanPage.getRoot().setOnTouchListener((v, event) -> true);
            binding.tutorialScanPage.getRoot().setVisibility(View.VISIBLE);
            binding.tutorialScanPage.getRoot().setAnimation(fadeIn);
        }


    }

    @Override
    public void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        captureManager.onSaveInstanceState(outState);
    }

    private boolean hasFlash() {
        return requireActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    private void progressScanData(BarcodeResult result) {
        String scanData = result.getText();
        BarcodeFormat scanFormat = result.getBarcodeFormat();
        ScanController.scanDataBuilder(requireActivity().getApplication())
                .setScanData(scanData)
                .setListener(new onScanListener() {
                    @Override
                    public void onSwebs(boolean isSwebsUrl, String scanSrl, String nowDate, String gpsLatitude, String gpsLongitude, String company, String code) {
                        Intent intent = new Intent(requireContext(), AuthenticScanActivity.class);
                        intent.putExtra("url", scanData)
                              .putExtra("scanSrl", scanSrl)
                              .putExtra("nowDate", nowDate)
                              .putExtra("gpsLatitude", gpsLatitude)
                              .putExtra("gpsLongitude", gpsLongitude)
                              .putExtra("company", company)
                              .putExtra("code", code);

                        startActivity(intent);
                    }

                    @Override
                    public void onOther() {
                        Intent intent = new Intent(requireContext(), QRLinkActivity.class);
                        intent.putExtra("url", scanData);

                        startActivity(intent);
                    }

                    @Override
                    public void onFailed() {
                        showQrException("????????? ?????? ???????????????.\n\n?????? ??? ?????? ?????? ????????????.");
                    }
                })
                .progressScanAnalysis();
    }


    boolean certifySwebsForm(String url) {
        if (url.contains("certchk"))
            if (url.contains("q="))
                return true;
        return false;
    }

    void showQrException(String content) {
        OneButtonBasicDialog oneButtonBasicDialog = new OneButtonBasicDialog(requireContext()
                , new BasicDialogTextModel("?????? ??????", content, "??????", "")
                , new DialogClickListener() {
            @Override
            public void onPositiveClick(int position) {
                isScanning = true;
            }

            @Override
            public void onNegativeClick() {

            }

            @Override
            public void onCloseClick() {
                isScanning = true;
            }
        });
        oneButtonBasicDialog.setCancelable(false);
        oneButtonBasicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        oneButtonBasicDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        oneButtonBasicDialog.show();
    }
}