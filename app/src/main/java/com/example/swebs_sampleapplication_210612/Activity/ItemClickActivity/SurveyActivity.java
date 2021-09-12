package com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Activity.SurveyDetailActivity;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivitySurveyBinding;

public class SurveyActivity extends AppCompatActivity {
    private ActivitySurveyBinding binding;
    private String Url = "https://i.pinimg.com/originals/a2/dc/00/a2dc008c48ce7d7934fbc5538166b8ff.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySurveyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnItemClickedBack.setOnClickListener(v -> onBackPressed());

        ImageView view = binding.imageViewProductSurveyProfile;
        GlideImage(view);

        binding.btnSurveyOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SurveyDetailActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        binding.btnSurvey1.setOnClickListener(v -> { btn_select1(); });
        binding.btnSurvey2.setOnClickListener(v -> btn_select2());
        binding.btnSurvey3.setOnClickListener(v -> btn_select3());
        binding.btnSurvey4.setOnClickListener(v -> btn_select4());

    }

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

    private void GlideImage(ImageView view){
        Glide.with(this).load(Url).into(view);
    }
}