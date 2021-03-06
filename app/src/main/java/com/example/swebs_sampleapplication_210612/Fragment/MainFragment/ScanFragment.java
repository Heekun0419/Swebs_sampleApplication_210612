package com.example.swebs_sampleapplication_210612.Fragment.MainFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.Size;
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
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.BasicDialogTextModel;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.OneButtonBasicDialog;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.ScanDataPushModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.databinding.FragmentScanBinding;
import com.example.swebs_sampleapplication_210612.util.GpsTracker;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanFragment extends Fragment {

    private ListenableFuture cameraProviderFuture;

    private ExecutorService cameraExecutor;
    private PreviewView previewView;

    private final int CAMERA_PERMISSION_CODE = 4655;
    private MyImageAnalyser imageAnalyser;
    private FragmentScanBinding binding;

    private boolean isScan = false;
    private boolean flash = false;

    private final int barcodeTypeUrl = 1;
    private final int barcodeTypeText = 2;

    private SwebsAPI retroAPI;
    private SPmanager sPmanager;
    private final Animation fadeIn = new AlphaAnimation(0,1);
    public ScanFragment() {
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
        binding = FragmentScanBinding.inflate(getLayoutInflater());
        sPmanager = new SPmanager(requireContext());

        String content = binding.textVIewScanExplain.getText().toString();
        SpannableString spannableString = new SpannableString(content);
        String word = "QR?????????";
        int start = content.indexOf(word);
        int end = start + word.length();
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#93E3BE")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.textVIewScanExplain.setText(spannableString);


        // ????????? ???????????? - Zoom Flash Focus ????????? ??????
        LifecycleCameraController lifecycleCameraController = new LifecycleCameraController(requireContext());
        lifecycleCameraController.bindToLifecycle(getViewLifecycleOwner());
        binding.cameraPreView.setController(lifecycleCameraController);

        // ????????? ????????????
        binding.btnScanFlash.setOnClickListener(v -> {
            if (!flash){
                lifecycleCameraController.enableTorch(true);
                flash = true;
            }else{
                lifecycleCameraController.enableTorch(false);
                flash = false;
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

        // scan ???????????? ????????? ??????
        binding.btnScanSetting.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ScanSettingActivity.class);
            startActivity(intent);
        });

        // ???????????? ???????????? ML Kit Barcode scanner
        //...
        previewView = binding.cameraPreView;
        requireActivity().getWindow().setFlags(1024, 1024);

        imageAnalyser = new MyImageAnalyser();

        cameraExecutor = Executors.newSingleThreadExecutor();
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());

        cameraProviderFuture.addListener(() -> {
            try {
                if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                }
                ProcessCameraProvider processCameraProvider = (ProcessCameraProvider) cameraProviderFuture.get();
                bindPreView(processCameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(requireContext()));

        return binding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();

        isScan = false;
        cameraProviderFuture.addListener(() -> {
            try {
                if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                }
                ProcessCameraProvider processCameraProvider = (ProcessCameraProvider) cameraProviderFuture.get();
                bindPreView(processCameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(requireContext()));

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
        imageAnalysis.setAnalyzer(cameraExecutor, imageAnalyser);
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
                if (!isScan) {
                    isScan = true;
                    String scanData = null;
                    if (valueType == Barcode.TYPE_URL) { // int 8
                        if (barcode.getUrl() != null)
                            scanData = barcode.getUrl().getUrl();
                    } else if (valueType == Barcode.TYPE_TEXT) { // int 7
                        if (barcode.getRawValue() != null)
                            scanData = barcode.getRawValue();
                    }

                    if (scanData != null) {
                        openScanResult(scanData, barcode.getValueType());

                    } else {
                        showQrException("???????????? ?????? ????????? QR CODE");
                    }
                }
            }
        }

    }

    void openScanResult(String url, int barcodeType) {
        String company = null, code = null;
        HashMap<String, String> location;

        if (barcodeType == 8 && authSwebsFromUrl(url)) {
            company = getCompanyFromUrl(url);
            code = getCodeFromUrl(url);
        }

        // get Gps
        location = getGpsLocation();

        // Scan Log Server Upload
        Log.d("scanLog", "?????? : " + url);
        Log.d("scanLog", "?????? : " + location.get("latitude") + " | ?????? : " + location.get("longitude"));
        Log.d("scanLog", "?????? ?????? : " + company + " | ?????? : " + code);
        HashMap<String, String> pushData = new HashMap<String, String>();

        pushData.put("qrData", url);
        pushData.put("company", company);
        pushData.put("code", code);
        pushData.put("locationLatitude", location.get("latitude"));
        pushData.put("locationLongitude", location.get("longitude"));
        pushScanAllData(pushData);


        Intent intent;
        if (company != null && code != null) {
            intent = new Intent(requireContext(), AuthenticScanActivity.class);
            intent.putExtra("url", url)
                  .putExtra("company", company)
                  .putExtra("code", code);
        } else {
            intent = new Intent(requireContext(), QRLinkActivity.class);
            intent.putExtra("url", url)
                  .putExtra("barcodeType", barcodeType);
        }
        startActivity(intent);
    }

    HashMap<String, RequestBody> setPushScanAllDataBody(HashMap<String, String> data) {
        HashMap<String, RequestBody> body = new HashMap<>();

        body.put("inputUserSrl", RequestBody.create(sPmanager.getUserSrl(), MediaType.parse("text/plane")));
        body.put("inputOsType", RequestBody.create("Android", MediaType.parse("text/plane")));
        if (data.get("qrData") != null)
            body.put("inputQrData", RequestBody.create(Objects.requireNonNull(data.get("qrData")), MediaType.parse("text/plane")));

        if (data.get("company") != null)
            body.put("inputCompany", RequestBody.create(Objects.requireNonNull(data.get("company")), MediaType.parse("text/plane")));

        if (data.get("code") != null)
            body.put("inputCode", RequestBody.create(Objects.requireNonNull(data.get("code")), MediaType.parse("text/plane")));

        if (data.get("locationLatitude") != null)
            body.put("inputLocationLatitude", RequestBody.create(Objects.requireNonNull(data.get("locationLatitude")), MediaType.parse("text/plane")));

        if (data.get("locationLongitude") != null)
            body.put("inputLocationLongitude", RequestBody.create(Objects.requireNonNull(data.get("locationLongitude")), MediaType.parse("text/plane")));

        return body;
    }

    HashMap<String, String> getGpsLocation() {
        HashMap<String, String> resultMap = new HashMap<String, String>();

        GpsTracker gpsTracker = new GpsTracker(requireContext());

        resultMap.put("latitude", Double.toString(gpsTracker.getLatitude()));
        resultMap.put("longitude", Double.toString(gpsTracker.getLongitude()));

        return resultMap;
    }

    void pushScanAllData(HashMap<String, String> data) {
        HashMap<String, RequestBody> body;

        body = setPushScanAllDataBody(data);

        try {
            Call<ScanDataPushModel> call = retroAPI.pushScanHistoryAllData(body);
            call.enqueue(new Callback<ScanDataPushModel>() {
                @Override
                public void onResponse(Call<ScanDataPushModel> call, Response<ScanDataPushModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            ScanDataPushModel responseData = response.body();
                            if (responseData.isSuccess()) {
                                Log.d("scanLog", "?????? ?????? ??????");
                            } else {
                                Log.d("scanLog", "?????? ?????? ??????");
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ScanDataPushModel> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.d("scanLog", "" + e);
        }
    }

    boolean authSwebsFromUrl(String url) {
        ArrayList<String> swebsUrlList = new ArrayList<>();
        swebsUrlList.add("swebs.co.kr");

        for (int i=0; i<swebsUrlList.size(); i++)
            if (url.contains(swebsUrlList.get(i)))
                if (certifySwebsForm(url))
                    return true;
        return false;
    }

    boolean certifySwebsForm(String url) {
        if (url.contains("certchk"))
            if (url.contains("q="))
                return true;
        return false;
    }

    String getCompanyFromUrl(String url) {
        String resultString;
        Pattern pattern = Pattern.compile("certchk/(([^/])*)?");
        Matcher matcher = pattern.matcher(url);

        if (matcher.find())
            resultString = matcher.group(1);
        else
            resultString = "fail";

        return resultString;
    }

    String getCodeFromUrl(String url) {
        String resultString;
        Pattern pattern = Pattern.compile("\\?q=((([^/])?)*)");
        Matcher matcher = pattern.matcher(url);

        if (matcher.find())
            resultString = matcher.group(1);
        else
            resultString = "fail";

        return resultString;
    }

    void showQrException(String content) {
        OneButtonBasicDialog oneButtonBasicDialog = new OneButtonBasicDialog(requireContext()
                , new BasicDialogTextModel("?????? ??????", content, "??????", "")
                , new DialogClickListener() {
            @Override
            public void onPositiveClick(int position) {
                isScan = false;
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

}