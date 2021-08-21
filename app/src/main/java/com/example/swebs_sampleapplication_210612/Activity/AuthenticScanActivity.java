package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.SwebsCorp.Model.CodeCertifyModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.SwebsCorp.SwebsCorpAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.SwebsCorp.SwebsCorpClient;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityAuthenticScan2Binding;
import com.example.swebs_sampleapplication_210612.databinding.ActivityAuthenticScanBinding;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticScanActivity extends AppCompatActivity {

    private ActivityAuthenticScan2Binding binding;

    private String resultUrl, resultCompany, resultCode;

    private SwebsCorpAPI swebsCorpAPI;

    private CodeCertifyModel codeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding = ActivityAuthenticScanBinding.inflate(getLayoutInflater());
        binding = ActivityAuthenticScan2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setLoadingVisibility(true);

        swebsCorpAPI = SwebsCorpClient.getRetrofitClient().create(SwebsCorpAPI.class);

        resultUrl = getIntent().getStringExtra("url");
        resultCompany = getIntent().getStringExtra("company");
        resultCode = getIntent().getStringExtra("code");

        resultCompany = "skinhill";
        resultCode = "57PE688KE7Z3W57787Y1";

        //resultCode += "11";

        String loadUrl = "https://www.swebs.co.kr/certchk/" + resultCompany + "/swebs_result.html?q=" + resultCode;
        webViewInit();
        getCodeInfo(resultUrl, resultCompany, resultCode);
        //webViewLoadUrl(loadUrl);

        binding.btnAuthenticScanBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.btnPurchaseInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PurchaseInfoActivity.class);
                intent.putExtra("prodImageUrl", codeModel.getComp_logo_img());
                intent.putExtra("corpName", codeModel.getComp_nm());
                startActivity(intent);
            }
        });
    }

    private void getCodeInfo(String loadUrl, String company, String code) {
        HashMap<String, RequestBody> body = new HashMap<>();
        body.put("comp_url", RequestBody.create(company, MediaType.parse("text/plane")));
        body.put("q_no", RequestBody.create(code, MediaType.parse("text/plane")));
        body.put("lang", RequestBody.create("kr", MediaType.parse("text/plane")));

        Call<CodeCertifyModel> call = swebsCorpAPI.getCodeInfo(body);
        call.enqueue(new Callback<CodeCertifyModel>() {
            @Override
            public void onResponse(Call<CodeCertifyModel> call, Response<CodeCertifyModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        CodeCertifyModel body = response.body();
                        codeModel = body;
                        if (body.getCode().equals("S")
                        || body.getCode().equals("N")) {
                            //String loadUrl;
                            //loadUrl = "https://www.swebs.co.kr/certchk/" + company + "/qr_result.html?q=" + code;
                            webViewLoadUrl(loadUrl);
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<CodeCertifyModel> call, Throwable t) {

            }
        });

    }

    private void webViewInit() {
        binding.webView.setWebViewClient(new WebViewClient(){
            int finishCount = 0;
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

    private void webViewLoadUrl(String url) {
        binding.webView.loadUrl(url);
    }

    private void renderWebViewComplete(int finishCount) {
        if (finishCount >= 2) {
            setLoadingVisibility(false);
            setWebViewVisibility(true);
            if (codeModel.getCode().equals("S")) {
                binding.purchaseTextView.setText("구매 등록");
                binding.btnPurchaseInput.setBackground(ContextCompat.getDrawable(this, R.drawable.radious_button_swebscolor));
                setPurchaseVisibility(true);
            } else if (codeModel.getCode().equals("N")) {
                binding.purchaseTextView.setText("신고 하기");
                binding.btnPurchaseInput.setBackground(ContextCompat.getDrawable(this, R.drawable.radious_rectangle_red));
                setPurchaseVisibility(true);
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