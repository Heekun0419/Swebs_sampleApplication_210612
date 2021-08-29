package com.example.swebs_sampleapplication_210612.Fragment.MainFragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.Activity.ScanSettingActivity;
import com.example.swebs_sampleapplication_210612.databinding.FragmentScanZxingBinding;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.ViewfinderView;
import com.journeyapps.barcodescanner.camera.CameraSettings;

import java.lang.reflect.Field;
import java.util.List;

public class ScanZxingFragment extends Fragment {
    private FragmentScanZxingBinding binding;

    private CaptureManager captureManager;
    private CameraSettings cameraSettings;
    private boolean firstResume = false;
    private boolean isFlashOn;
    private boolean isScanning;

    public ScanZxingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentScanZxingBinding.inflate(getLayoutInflater());


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
                if (isScanning)

                Log.d("zxing", "result 2: " + result.getText());
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });

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

        binding.zxingBarcodeScanner.getBarcodeView().getCameraSettings().setAutoFocusEnabled(false);
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

}