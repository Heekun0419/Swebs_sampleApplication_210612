package com.example.swebs_sampleapplication_210612.Activity.EventApply;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.EventViewModel;
import com.example.swebs_sampleapplication_210612.databinding.ActivityEventRegisterSuccessBinding;

public class EventRegisterSuccessActivity extends AppCompatActivity {

    private ActivityEventRegisterSuccessBinding binding;
    private EventViewModel eventViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEventRegisterSuccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        eventViewModel = new EventViewModel(getApplication());

        // 이벤트 정보 불러오기...
        eventViewModel.getEventDetailFromServer(getIntent().getStringExtra("eventSrl"));

        // 신청 정보 받아오기기
        eventViewModel.getEventApplyInfo(getIntent().getStringExtra("partSrl"));


        // 신청 정보 UI UPDATE
        eventViewModel.getLiveEventApplyInfo().observe(this, model -> {
            binding.textViewName.setText(model.getReceiver());
            binding.textviewPhoneNumber.setText(model.getPhonenumber());

            String addressFull = model.getAddress1();
            if (model.getAddress2() != null)
                addressFull += model.getAddress2();
            binding.TextViewAddress.setText(addressFull);
        });

        // 이벤트 관련 정보 UI UPDATE
        eventViewModel.getLiveEventDetailInfo().observe(this, models -> {
            // 대표 이미지...
            Glide.with(this)
                    .load(getImageViewUrl(models.getFile_srl(), "1000"))
                    .into(binding.imageViewProductEventProfile);

            // 회사 이름
            binding.textViewCompanyName.setText(models.getCorp_name());

            // 이벤트 제목
            binding.textViewEventTitle.setText(models.getEvent_title());
        });
    }

    private String getImageViewUrl(String fileSrl, String Width) {
        String result = getString(R.string.IMAGE_VIEW_URL) + "?inputFileSrl=" + fileSrl;
        if (Width != null) result += "&inputImageWidth=" + Width;
        return result;
    }
}
