package com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Activity.SurveyDetailActivity;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.SurveyDetailInfoModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.SurveyOptionModel;
import com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment.BottomCommentFragment;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.SurveyVIewModel;
import com.example.swebs_sampleapplication_210612.adapter.Listener.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.adapter.ReviewAdapter;
import com.example.swebs_sampleapplication_210612.adapter.SurveyButtonAdapter;
import com.example.swebs_sampleapplication_210612.adapter.SurveyProgressAdapter;
import com.example.swebs_sampleapplication_210612.databinding.ActivitySurveyBinding;

import java.util.List;

public class SurveyActivity extends AppCompatActivity implements OnItemClickListener{

    private ActivitySurveyBinding binding;
    private SurveyVIewModel vIewModel;
    private SurveyButtonAdapter buttonAdapter;
    private SurveyProgressAdapter progressAdapter;
    private String surveySrl;
    private FragmentManager manager;
    private int selectPosition;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySurveyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        vIewModel = new SurveyVIewModel(getApplication());

        if (getIntent().getStringExtra("surveySrl") != null){
            surveySrl = getIntent().getStringExtra("surveySrl");
        }

        binding.constraintLayoutBtn2.setVisibility(View.GONE);

        vIewModel.getSurveyDetailInfo(surveySrl);

        binding.layoutSurveyList.setVisibility(View.VISIBLE);
        binding.layoutSurveyProgress.setVisibility(View.GONE);

        // 댓글 불러올때 사용할 FragmentManager
        manager = getSupportFragmentManager();

        // 뒤로가기
        binding.btnItemClickedBack.setOnClickListener(v -> onBackPressed());

        // 설문완료 버튼 누르면 다음페이지로 이동
        binding.btnSurveyOk.setOnClickListener(v -> {
            int vote = Integer.parseInt(buttonAdapter.getItem(selectPosition).getVote_count())+1;
            progressAdapter.setSelectedList(selectPosition,true);
            progressAdapter.setVote(selectPosition,Integer.toString(vote));
            progressAdapter.notifyDataSetChanged();

            binding.layoutSurveyList.setVisibility(View.GONE);
            binding.layoutSurveyProgress.setVisibility(View.VISIBLE);
            binding.constraintLayout.setVisibility(View.GONE);
            binding.constraintLayoutBtn2.setVisibility(View.VISIBLE);

        });

        binding.btnReSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressAdapter.clearSelectList();
                progressAdapter.notifyDataSetChanged();
                buttonAdapter.ClearSelectList();
                buttonAdapter.notifyDataSetChanged();

                binding.layoutSurveyList.setVisibility(View.VISIBLE);
                binding.layoutSurveyProgress.setVisibility(View.GONE);
                binding.constraintLayout.setVisibility(View.VISIBLE);
                binding.constraintLayoutBtn2.setVisibility(View.GONE);

            }
        });

        vIewModel.getOptionSrl().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });

        vIewModel.getLiveDataSurveyDetail().observe(this, new Observer<SurveyDetailInfoModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(SurveyDetailInfoModel model) {
                // 타이틀
                binding.textviewSurveyTitle.setText(model.getSurvey_title());

                // 상세 내용
                binding.textViewContent.setText(htmlToString(model.getContent()));

                // 참여 인원
                binding.textviewJoinCount.setText(model.getJoin_count()+"명 참여중");

                // 투표 기간
                binding.textViewStartDate.setText(model.getStart_date().substring(0,10));
                binding.textViewEndDate.setText(" ~ "+model.getEnd_date().substring(0,10));

                // 대표 이미지 로드
                GlideImage(binding.imageViewProductSurveyProfile,getImageViewUrl(model.getFile_srl(), "1000"));

                // 댓글 불러오기
                manager.beginTransaction().replace(R.id.frameLayout_survey_detailActivity
                        , BottomCommentFragment.newInstance(model.getDocument_srl())).commit();
            }
        });

        vIewModel.getLiveDataSurveyOptionList().observe(this, new Observer<List<SurveyOptionModel>>() {
            @Override
            public void onChanged(List<SurveyOptionModel> list) {
                initProgressAdapter(list);
                initButtonAdapter(list);
            }
        });




    }

    private void initButtonAdapter(List<SurveyOptionModel> list) {
        buttonAdapter = new SurveyButtonAdapter(this, list, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewButton.setLayoutManager(linearLayoutManager);
        binding.recyclerViewButton.setAdapter(buttonAdapter);
    }

    private void initProgressAdapter(List<SurveyOptionModel> list) {
        progressAdapter = new SurveyProgressAdapter(this, list, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewProgress.setLayoutManager(linearLayoutManager);
        binding.recyclerViewProgress.setAdapter(progressAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onItemSelected(View view, int position, String code) {
        for (int i=0; i<buttonAdapter.getItemCount(); i++) {
            if (position == i) {
                selectPosition = i;
                buttonAdapter.setSelectedList(i, true);

            } else {
                buttonAdapter.setSelectedList(i, false);
            }
        }
        buttonAdapter.notifyDataSetChanged();

    }

    private String getImageViewUrl(String fileSrl, String Width) {
        String result = getString(R.string.IMAGE_VIEW_URL) + "?inputFileSrl=" + fileSrl;
        if (Width != null)
            result += "&inputImageWidth=" + Width;
        return result;
    }

    private String htmlToString(String html) {
        return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
    }

    private void GlideImage(ImageView view, String url){
        Glide.with(this).load(url).centerCrop().into(view);
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