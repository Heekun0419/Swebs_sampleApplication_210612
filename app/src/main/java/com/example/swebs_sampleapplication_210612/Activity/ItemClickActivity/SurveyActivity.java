package com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Activity.SurveyDetailActivity;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.SurveyDetailInfoModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.SurveyOptionModel;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.SurveyVIewModel;
import com.example.swebs_sampleapplication_210612.adapter.Listener.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.adapter.ReviewAdapter;
import com.example.swebs_sampleapplication_210612.adapter.SurveyButtonAdapter;
import com.example.swebs_sampleapplication_210612.databinding.ActivitySurveyBinding;

import java.util.List;

public class SurveyActivity extends AppCompatActivity implements OnItemClickListener {

    private ActivitySurveyBinding binding;
    private SurveyVIewModel vIewModel;
    private SurveyButtonAdapter buttonAdapter;
    private String surveySrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySurveyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        vIewModel = new SurveyVIewModel(getApplication());

        if (getIntent().getStringExtra("surveySrl") != null){
            surveySrl = getIntent().getStringExtra("surveySrl");
        }

        vIewModel.getSurveyDetailInfo(surveySrl);

        // 뒤로가기
        binding.btnItemClickedBack.setOnClickListener(v -> onBackPressed());

        // 설문완료 버튼 누르면 다음페이지로 이동
        binding.btnSurveyOk.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SurveyDetailActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        vIewModel.getLiveDataSurveyOptionList().observe(this, new Observer<List<SurveyOptionModel>>() {
            @Override
            public void onChanged(List<SurveyOptionModel> surveyOptionModels) {
                initButtonAdapter(surveyOptionModels);
            }
        });

        vIewModel.getLiveDataSurveyDetail().observe(this, new Observer<SurveyDetailInfoModel>() {
            @Override
            public void onChanged(SurveyDetailInfoModel model) {

            }
        });

    }

    private void initButtonAdapter(List<SurveyOptionModel> surveyOptionModels){
        buttonAdapter = new SurveyButtonAdapter(this,surveyOptionModels ,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewButton.setLayoutManager(linearLayoutManager);
        binding.recyclerViewButton.setAdapter(buttonAdapter);
    }

    @Override
    public void onItemSelected(View view, int position, String code) {

    }
/*
    private void btn_select1(){
        binding.btnSurvey1.setSelected(true);
        binding.textViewSurveyInfo1.setTextColor(Color.parseColor("#21CCB2"));
        binding.btnSurvey2.setSelected(false);
        binding.textViewSurveyInfo2.setTextColor(Color.parseColor("#000000"));
        binding.btnSurvey3.setSelected(false);
        binding.textViewSurveyInfo3.setTextColor(Color.parseColor("#000000"));
        binding.btnSurvey4.setSelected(false);
        binding.textViewSurveyInfo4.setTextColor(Color.parseColor("#000000"));
    } private void btn_select2(){
        binding.btnSurvey2.setSelected(true);
        binding.textViewSurveyInfo2.setTextColor(Color.parseColor("#21CCB2"));
        binding.btnSurvey1.setSelected(false);
        binding.textViewSurveyInfo1.setTextColor(Color.parseColor("#000000"));
        binding.btnSurvey3.setSelected(false);
        binding.textViewSurveyInfo3.setTextColor(Color.parseColor("#000000"));
        binding.btnSurvey4.setSelected(false);
        binding.textViewSurveyInfo4.setTextColor(Color.parseColor("#000000"));
    }
    private void btn_select3(){
        binding.btnSurvey3.setSelected(true);
        binding.textViewSurveyInfo3.setTextColor(Color.parseColor("#21CCB2"));
        binding.btnSurvey2.setSelected(false);
        binding.textViewSurveyInfo2.setTextColor(Color.parseColor("#000000"));
        binding.btnSurvey1.setSelected(false);
        binding.textViewSurveyInfo1.setTextColor(Color.parseColor("#000000"));
        binding.btnSurvey4.setSelected(false);
        binding.textViewSurveyInfo4.setTextColor(Color.parseColor("#000000"));
    }
    private void btn_select4(){
        binding.btnSurvey4.setSelected(true);
        binding.textViewSurveyInfo4.setTextColor(Color.parseColor("#21CCB2"));
        binding.btnSurvey2.setSelected(false);
        binding.textViewSurveyInfo2.setTextColor(Color.parseColor("#000000"));
        binding.btnSurvey3.setSelected(false);
        binding.textViewSurveyInfo3.setTextColor(Color.parseColor("#000000"));
        binding.btnSurvey1.setSelected(false);
        binding.textViewSurveyInfo1.setTextColor(Color.parseColor("#000000"));
    }
*/

}