package com.example.swebs_sampleapplication_210612.Activity.PurchaseApply;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Activity.ServiceNotReadyActivity;
import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityPurchaseInfoBinding;
import com.google.android.material.datepicker.MaterialDatePicker;

public class PurchaseInfoActivity extends AppCompatActivity {

    private ActivityPurchaseInfoBinding binding;
    private String Date;
    private MaterialDatePicker datePicker;
    private String ImageUrI;
    private MyInfoRepository myInfoRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPurchaseInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myInfoRepository = new MyInfoRepository(getApplication());

        // * 표시 빨간색으로 바꿈꿈
       renderFirstTextRed();

        ImageUrI = getIntent().getStringExtra("prodImageUrl");
        binding.textView12.setText(getIntent().getStringExtra("corpName"));

        // 이미지 뷰 Glide
        ImageView view = binding.ImageViewPurchaseImage;
        GlideImage(view);

        //추가항목 View GONE
        binding.layoutPurchaseAddedAll.setVisibility(View.GONE);

        binding.btnPurchaseInfoBack.setOnClickListener(v -> onBackPressed());

        binding.btnPurchaseRegister.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ServiceNotReadyActivity.class);
            startActivity(intent);
            // IntentSuccessActivity();
        });

        // 달력보기
        binding.btnPurchaseDatePicker.setOnClickListener(v -> DatePicker());

    }

    @Override
    protected void onResume() {
        super.onResume();

        // 이름 가져오기
        myInfoRepository.getValueToLiveData("name").observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s != null) {
                    binding.userName.setText(s);
                } else {
                    binding.editTextName.setHint("이름을 입력해주세요");
                    binding.editTextName.setVisibility(View.VISIBLE);
                }
            }
        });

        // 폰번호 가져오기
        myInfoRepository.getValueToLiveData("phone").observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s != null) {
                    binding.userPhone.setText(s);
                } else{
                    binding.editTextPhone.setHint("휴대폰 번호를 입력해주세요");
                    binding.editTextPhone.setVisibility(View.VISIBLE);
                }
            }
        });

        // 아이디 가져오기
        myInfoRepository.getValueToLiveData("email").observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    binding.userEmail.setText(s);
                } else {
                    binding.editTextEmail.setHint("이메일을 입력해주세요");
                    binding.editTextEmail.setVisibility(View.VISIBLE);
                }
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

            if(binding.editTextEmail.getVisibility() == View.VISIBLE )
               intent.putExtra("email",binding.editTextEmail.getText().toString());
            else
                intent.putExtra("email",binding.userEmail.getText().toString());
            if(binding.editTextPhone.getVisibility() == View.VISIBLE)
                intent.putExtra("phone",binding.editTextPhone.getText().toString());
            else
                intent.putExtra("phone",binding.userPhone.getText().toString());
            if(binding.editTextName.getVisibility() == View.VISIBLE)
                intent.putExtra("name",binding.editTextName.getText().toString());
            else
                intent.putExtra("name",binding.userName.getText().toString());

            intent.putExtra("imageUrl",ImageUrI);

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
        Glide.with(this).load(ImageUrI).placeholder(R.drawable.ic_camera).into(view);
    }

    private void renderFirstTextRed() {
        binding.textviewProductName.setText(spannable(binding.textviewProductName.getText().toString()));
        binding.textviewProductSrl.setText(spannable(binding.textviewProductSrl.getText().toString()));
        binding.textViewPurchaseDate.setText(spannable(binding.textViewPurchaseDate.getText().toString()));
        binding.textViewPurchasePlace.setText(spannable(binding.textViewPurchasePlace.getText().toString()));
    }

    private SpannableString spannable(String string) {
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ED6D6D")), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

}