package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityPurchaseInfoBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class PurchaseInfoActivity extends AppCompatActivity {

    private ActivityPurchaseInfoBinding binding;
    private String Date;
    private MaterialDatePicker datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPurchaseInfoBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.btnPurchaseInfoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.btnPurchaseRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentSuccessActivity();
            }
        });

        binding.btnPurchaseDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker();
            }
        });


    }

    private void IntentSuccessActivity(){
        String store =binding.edtPlaceOfPurchase.getText().toString();
        String productName =binding.edtProductName.getText().toString();
        String productSrl =binding.edtSrlNumberOfProduct.getText().toString();

        if(!store.isEmpty() && !productName.isEmpty() && !productSrl.isEmpty() && !Date.isEmpty()){
            Intent intent = new Intent(getApplicationContext(),PurchaseRegistSuccessActivity.class);
            intent.putExtra("store",store);
            intent.putExtra("productName",productName);
            intent.putExtra("productSrl",productSrl);
            intent.putExtra("date",Date);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }else {
            Toast.makeText(this, "필수사항 입력이 필요합니다.", Toast.LENGTH_SHORT).show();
        }

    }
    private void DatePicker(){
        datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .build();

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Date =  datePicker.getHeaderText();
                binding.dateOfPurchase.setText(Date);
            }
        });
        datePicker.show(getSupportFragmentManager(),"datePicker");

    }
}