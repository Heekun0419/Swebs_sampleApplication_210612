package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.example.swebs_sampleapplication_210612.Activity.Login_Signup.LoginActivity;
import com.example.swebs_sampleapplication_210612.Activity.PurchaseApply.PurchaseInfoActivity;
import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.SwebsCorp.Model.CodeCertifyModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.SwebsCorp.SwebsCorpAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.SwebsCorp.SwebsCorpClient;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.OneButtonBasicDialog;
import com.example.swebs_sampleapplication_210612.Dialog.TwoButtonBasicDialog;
import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.BasicDialogTextModel;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityAuthenticScan2Binding;
import com.google.android.material.snackbar.Snackbar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticScanActivity extends AppCompatActivity {

    private ActivityAuthenticScan2Binding binding;
    private SwebsAPI retroAPI;
    private SPmanager sPmanager;
    private MyInfoRepository myInfoRepository;

    private String resultUrl, resultCompany, resultCode, snCode;
    private String myBirthday, myGender;
    private String swebsResultCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthenticScan2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setLoadingVisibility(true);

        myInfoRepository = new MyInfoRepository(getApplication());
        sPmanager = new SPmanager(getApplicationContext());

        retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);

        resultUrl = getIntent().getStringExtra("url");
        resultCompany = getIntent().getStringExtra("company");
        resultCode = getIntent().getStringExtra("code");
        snCode = getNowDateToSnCode(getIntent().getStringExtra("nowDate"));

        String loadUrl = "https://www.swebs.co.kr/certchk/" + resultCompany + "/swebs_result.html?q=" + resultCode;
        webViewInit();
        getCodeInfoFromSwebs(resultUrl, resultCompany, resultCode);

        binding.btnAuthenticScanBack.setOnClickListener(v -> onBackPressed());

        binding.btnPurchaseInput.setOnClickListener(v -> {
            if (swebsResultCode.equals("S")) {
                Intent intent = new Intent(getApplicationContext(), PurchaseInfoActivity.class);
                startActivity(intent);
            } else if (swebsResultCode.equals("N")) {
                Intent intent = new Intent(getApplicationContext(),ScanHistoryActivity.class);
                intent.putExtra("resultCode","copy");
                startActivity(intent);
            }
        });

        myInfoRepository.getValueToLiveData("birthday").observe(this, s -> {
            if (s != null)
                myBirthday = s;
        });

        myInfoRepository.getValueToLiveData("gender").observe(this, s -> {
            if (s != null)
                myGender = s;
        });
    }

    private void getCodeInfoFromSwebs(String loadUrl, String company, String code) {
        HashMap<String, RequestBody> formData = new HashMap<>();
        formData.put("inputUserSrl", RequestBody.create(sPmanager.getUserSrl(), MediaType.parse("text/plane")));
        formData.put("inputScanSrl", RequestBody.create(getIntent().getStringExtra("scanSrl"), MediaType.parse("text/plane")));
        formData.put("inputSerialCode", RequestBody.create(snCode, MediaType.parse("text/plane")));
        formData.put("inputCompany", RequestBody.create(company, MediaType.parse("text/plane")));
        formData.put("inputCode", RequestBody.create(code, MediaType.parse("text/plane")));

        Call<String> call = retroAPI.pushScanAuth(formData);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()
                && response.body() != null) {
                    swebsResultCode = response.body();
                    try {
                        webViewLoadUrl(loadUrl);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private String getNowDateToSnCode(String nowDate) {
        String year1 = nowDate.substring(0, 2);
        String year2 = nowDate.substring(2, 4);
        String month = nowDate.substring(4, 6);
        String day = nowDate.substring(6, 8);
        String hour = nowDate.substring(8, 10);
        String min = nowDate.substring(10, 12);
        String sec = nowDate.substring(12, 14);

        String resultA = String.valueOf(Integer.parseInt(year1)+Integer.parseInt(min));
        String resultB = String.valueOf(Integer.parseInt(year2)+Integer.parseInt(hour));
        String resultC = String.valueOf(Integer.parseInt(month)+Integer.parseInt(day));
        String resultD = String.valueOf(Integer.parseInt(sec)+Integer.parseInt(sec));
        resultD = resultD.substring(resultD.length()-1);

        return resultB+resultD+resultA+resultC;
    }

    private void webViewInit() {
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setLoadWithOverviewMode(true);
        binding.webView.getSettings().setDomStorageEnabled(true);
        binding.webView.setWebViewClient(new WebViewClient(){
            int finishCount = 0;

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (finishCount < 2) {
                    return super.shouldOverrideUrlLoading(view, request);
                }
                return true; //super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                renderWebViewComplete(++finishCount);
                super.onPageCommitVisible(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                renderWebViewComplete(++finishCount);
                super.onPageFinished(view, url);
            }
        });
    }

    private void webViewLoadUrl(String url) throws UnsupportedEncodingException {
        String postData = "dn=" + URLEncoder.encode(getIntent().getStringExtra("nowDate"), "UTF-8") +
                        "&sn=" + URLEncoder.encode(snCode, "UTF-8") +
                        "&lat=" + URLEncoder.encode(getIntent().getStringExtra("gpsLatitude"), "UTF-8") +
                        "&lng=" + URLEncoder.encode(getIntent().getStringExtra("gpsLongitude"), "UTF-8") +
                        "&m_id=" + URLEncoder.encode(sPmanager.getUserSrl(), "UTF-8");

        if (myBirthday != null)
            postData += "&m_year=" + URLEncoder.encode(myBirthday, "UTF-8");
        if (myGender != null)
            postData += "&m_gender=" + URLEncoder.encode(myGender, "UTF-8");

        Log.d("test_", "data : " + postData);
        Log.d("test_", "url : " + url);
        binding.webView.postUrl(url, postData.getBytes(StandardCharsets.UTF_8));
    }

    private void renderWebViewComplete(int finishCount) {
        if (finishCount >= 2) {
            setLoadingVisibility(false);
            setWebViewVisibility(true);
            if (swebsResultCode.equals("S")) {
                binding.purchaseTextView.setText("구매 등록");
                binding.btnPurchaseInput.setBackground(ContextCompat.getDrawable(this, R.drawable.radious_button_swebscolor));
                setPurchaseVisibility(true);
                //showNoticeDialog();
            } else if (swebsResultCode.equals("N")) {
                binding.purchaseTextView.setText("신고 하기");
                binding.btnPurchaseInput.setBackground(ContextCompat.getDrawable(this, R.drawable.radious_rectangle_red));
                setPurchaseVisibility(true);
            }
        }
    }

    private void showNoticeDialog() {
        Log.d("ok", "compay : " + resultCompany);
        if (resultCompany.equals("rmgany")) {
            View view = binding.testset;
            Snackbar snackbar;
            if (sPmanager.getUserType().equals("guest")) {
                snackbar = Snackbar.make(view, "회원가입 후 정품스캔시에는 20P가 지급됩니다.\n회원가입 하시겠습니까?", 5000);
                snackbar.setAction("회원가입", v -> {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                });
            } else {
                snackbar = Snackbar.make(view, "정품스캔을 하시어\n포인트 20P 지급 됩니다.", 5000);
                snackbar.setAction("확인", v -> {
                    snackbar.dismiss();
                });
            }

            View snackView = snackbar.getView();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackView.getLayoutParams();
            params.gravity = Gravity.TOP;
            params.topMargin = binding.appBarLayout.getHeight() + 10;
            snackView.setLayoutParams(params);
            snackbar.show();
        } else {
            if (sPmanager.getUserType().equals("guest")) {
                TwoButtonBasicDialog twoButtonBasicDialog = new TwoButtonBasicDialog(this
                        , new BasicDialogTextModel("정품 스캔", "정품 스캔을 하시어 포인트 5P 지급됩니다. 회원가입 후 정품 스캔시에는 20P가 지급됩니다.\n회원가입 하시겠습니까?", "회원가입", "취소")
                        , new DialogClickListener() {
                    @Override
                    public void onPositiveClick(int position) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onNegativeClick() {

                    }

                    @Override
                    public void onCloseClick() {

                    }
                });
                twoButtonBasicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                twoButtonBasicDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                twoButtonBasicDialog.show();
            } else {
                OneButtonBasicDialog oneButtonBasicDialog = new OneButtonBasicDialog(this
                        , new BasicDialogTextModel("정품 스캔", "정품 스캔을 하시어\n포인트 20P 지급 됩니다.", "확인", "")
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
                oneButtonBasicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                oneButtonBasicDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                oneButtonBasicDialog.show();
            }
        }
    }

    private void setLoadingVisibility(boolean isVisible) {
        if (isVisible)
            binding.loadingView.getRoot().setVisibility(View.VISIBLE);
        else
            binding.loadingView.getRoot().setVisibility(View.GONE);
    }

    private void setWebViewVisibility(boolean isVisible) {
        if (isVisible)
            binding.webView.setVisibility(View.VISIBLE);
        else
            binding.webView.setVisibility(View.GONE);
    }

    private void setPurchaseVisibility(boolean isVisible) {
        if (isVisible)
            binding.purchaseView.setVisibility(View.VISIBLE);
        else
            binding.purchaseView.setVisibility(View.GONE);
    }

    private void ShowMyDialog(String URl){
        AlertDialog.Builder builder = new AlertDialog.Builder(AuthenticScanActivity.this);
        builder.setCancelable(false);
        builder.setTitle("스캔 정보").setMessage(URl);
        builder.setPositiveButton(R.string.link, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(URl));
                startActivity(intent);
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(AuthenticScanActivity.this, R.color.swebs_main_color1));
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(AuthenticScanActivity.this, R.color.swebs_main_color1));
    }
}