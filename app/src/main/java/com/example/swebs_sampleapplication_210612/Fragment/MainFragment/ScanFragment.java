package com.example.swebs_sampleapplication_210612.Fragment.MainFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraProvider;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.LifecycleCameraController;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.Activity.AuthenticScanActivity;
import com.example.swebs_sampleapplication_210612.Activity.MainActivity;
import com.example.swebs_sampleapplication_210612.Activity.ScanSettingActivity;
import com.example.swebs_sampleapplication_210612.databinding.FragmentScanBinding;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScanFragment extends Fragment {

    private ListenableFuture cameraProviderFuture;

    private ExecutorService cameraExecutor;
    private PreviewView previewView;

    private final int CAMERA_PERMISSION_CODE = 4655;
    private MyImageAnalyser imageAnalyser;
    private FragmentScanBinding binding;

    private boolean isScan = false;
    private boolean flash = false;

    public ScanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentScanBinding.inflate(getLayoutInflater());

        String content = binding.textVIewScanExplain.getText().toString();
        SpannableString spannableString = new SpannableString(content);
        String word = "QR코드를";
        int start = content.indexOf(word);
        int end = start + word.length();
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#93E3BE")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.textVIewScanExplain.setText(spannableString);

        // 카메라 컨트롤러 - Zoom Flash Focus 컨트롤 가능
        LifecycleCameraController lifecycleCameraController = new LifecycleCameraController(requireContext());
        lifecycleCameraController.bindToLifecycle(getViewLifecycleOwner());
        binding.cameraPreView.setController(lifecycleCameraController);

        // 플래쉬 켜고끄기
        binding.btnScanFlash.setOnClickListener(v -> {
            if (!flash){
                lifecycleCameraController.enableTorch(true);
                flash = true;
            }else{
                lifecycleCameraController.enableTorch(false);
                flash = false;
            }
        });

        // 앱바에 로고 안보이게 하기
        binding.includedAppbarScan.imageView19.setVisibility(View.INVISIBLE);
        binding.includedAppbarScan.imageButton2.setBackgroundColor(Color.TRANSPARENT);

        // 네비게이션 드로어 열기
        binding.includedAppbarScan.imageButton.setOnClickListener(v ->
                ((MainActivity)requireActivity()).drawer.openDrawer(GravityCompat.START));


        // 튜토리얼 페이지 닫기
        binding.tutorialScanPage.textViewScanTutorialClose.setOnClickListener(v ->
                binding.tutorialScanPage.getRoot().setVisibility(View.GONE));
        binding.tutorialScanPage.imageButton5.setOnClickListener(v -> {
            binding.tutorialScanPage.getRoot().setVisibility(View.GONE);
        });

        // Bottom Sheet 열기
        binding.includedAppbarScan.imageButton2.setOnClickListener(v ->
                ((MainActivity)requireActivity()).BottomSheetOpen());

        // scan 환경설정 페이지 이동
        binding.btnScanSetting.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ScanSettingActivity.class);
            startActivity(intent);
        });

        // 아래부터 스캔화면 ML Kit Barcode scanner
        //...
        previewView = binding.cameraPreView;
        requireActivity().getWindow().setFlags(1024, 1024);

        imageAnalyser = new MyImageAnalyser();

        cameraExecutor = Executors.newSingleThreadExecutor();
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());

        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                    }
                    ProcessCameraProvider processCameraProvider = (ProcessCameraProvider) cameraProviderFuture.get();
                    bindPreView(processCameraProvider);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(requireContext()));

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        isScan = false;
        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                    }
                    ProcessCameraProvider processCameraProvider = (ProcessCameraProvider) cameraProviderFuture.get();
                    bindPreView(processCameraProvider);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    private void bindPreView(ProcessCameraProvider processCameraProvider) {
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        ImageCapture imageCapture = new ImageCapture.Builder().build();
        processCameraProvider.unbindAll();
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetResolution(new Size(1280,720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_BLOCK_PRODUCER)
                .build();
        imageAnalysis.setAnalyzer(cameraExecutor,imageAnalyser);
        processCameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture, imageAnalysis);

    }

    class MyImageAnalyser implements ImageAnalysis.Analyzer {

        MyImageAnalyser() {
        }

        @Override
        public void analyze(@NonNull @NotNull ImageProxy image) {
            ScanBarcode(image);
        }


        private void ScanBarcode(ImageProxy imageProxy) {
            @SuppressLint("UnsafeOptInUsageError") Image image1 = imageProxy.getImage();

            assert image1 != null;
            InputImage inputImage = InputImage.fromMediaImage(image1, imageProxy.getImageInfo().getRotationDegrees());
            BarcodeScannerOptions options = new BarcodeScannerOptions.Builder()
                    .setBarcodeFormats(
                            Barcode.FORMAT_QR_CODE,
                            Barcode.FORMAT_AZTEC
                    ).build();

            BarcodeScanner scanner = BarcodeScanning.getClient(options);

            Task<List<Barcode>> result = scanner.process(inputImage)
                    .addOnSuccessListener(barcodes -> {
                        // Task completed successfully
                        // ...
                        readerBarCodeData(barcodes);
                        imageProxy.close();
                    })
                    .addOnFailureListener(e -> {
                        // Task failed with an exception
                        // ...
                    })
                    .addOnCompleteListener(task -> {
                    });
        }

        private void readerBarCodeData(List<Barcode> barcodes) {
            for (Barcode barcode : barcodes) {
                Rect bounds = barcode.getBoundingBox();
                Point[] corners = barcode.getCornerPoints();
                String rawValue = barcode.getRawValue();

                int valueType = barcode.getValueType();
                // See API reference for complete list of supported types
                switch (valueType) {
                    case Barcode.TYPE_WIFI:
                        String ssid = barcode.getWifi().getSsid();
                        String password = barcode.getWifi().getPassword();
                        int type = barcode.getWifi().getEncryptionType();
                        break;
                    case Barcode.TYPE_URL:
                            String Url = barcode.getUrl().getUrl();
                            String title = barcode.getUrl().getTitle();
                            if(!isScan){
                                isScan =true;
                            Intent intent = new Intent(requireContext(), AuthenticScanActivity.class);
                            intent.putExtra("URL",Url);
                            startActivity(intent);
                            }
                            break;
                }
            }
        }

    }

}