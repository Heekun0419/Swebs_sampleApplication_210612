package com.example.swebs_sampleapplication_210612.Activity.PurchaseApply;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityPurchaseInfoBinding;
import com.google.android.material.datepicker.MaterialDatePicker;

public class PurchaseInfoActivity extends AppCompatActivity {

    private ActivityPurchaseInfoBinding binding;
    private String Date;
    private MaterialDatePicker datePicker;
    private String ImageUrI = "https://i.pinimg.com/originals/a2/4f/e6/a24fe6cabab71872039e30af52e7dd9e.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPurchaseInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageUrI = getIntent().getStringExtra("prodImageUrl");
        binding.textView12.setText(getIntent().getStringExtra("corpName"));

        // 이미지 뷰 Glide
        ImageView view = binding.ImageViewPurchaseImage;
        GlideImage(view);

        binding.btnPurchaseInfoBack.setOnClickListener(v -> onBackPressed());

        binding.btnPurchaseRegister.setOnClickListener(v -> IntentSuccessActivity());

        // 달력보기
        binding.btnPurchaseDatePicker.setOnClickListener(v -> DatePicker());

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

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Date =  datePicker.getHeaderText();
            binding.dateOfPurchase.setText(Date);
        });
        datePicker.show(getSupportFragmentManager(),"datePicker");

    }

    private void GlideImage(ImageView view) {
        Glide.with(this).load(ImageUrI).into(view);
    }
}