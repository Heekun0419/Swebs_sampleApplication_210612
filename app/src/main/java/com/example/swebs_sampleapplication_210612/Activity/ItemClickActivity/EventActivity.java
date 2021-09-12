package com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.swebs_sampleapplication_210612.Activity.EventApply.EventApplyActivity;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventDetailInfoModel;
import com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment.BottomCommentFragment;
import com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment.BottomReviewFragment;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.EventViewModel;
import com.example.swebs_sampleapplication_210612.databinding.ActivityEventBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventActivity extends AppCompatActivity {
    private ActivityEventBinding binding;
    private EventViewModel viewModel;
    private FragmentManager manager;
    private String documentSrl;
    private boolean isApplied = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEventBinding.inflate(getLayoutInflater());
        viewModel = new EventViewModel(getApplication());
        setContentView(binding.getRoot());


        viewModel.getEventDetailFromServer(getIntent().getStringExtra("eventSrl"));

        // 이벤트 신청...
        binding.btnEventApply.setOnClickListener(v -> {
            if (isApplied) {
                Intent intent = new Intent(getApplicationContext(), EventApplyActivity.class);
                intent.putExtra("eventSrl", getIntent().getStringExtra("eventSrl"));
                finishResult.launch(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        // 찜 클릭
        binding.likeFilled.setOnClickListener(v -> {
            viewModel.pushEventLike(getIntent().getStringExtra("eventSrl"));
        });

        binding.btnItemClickedBack.setOnClickListener(v -> onBackPressed());

        viewModel.getLiveEventDetailInfo().observe(this, models -> {
            documentSrl = models.getDocument_srl();

            // 이벤트 대표 이미지
            Glide.with(this)
                    .load(getImageViewUrl(models.getFile_srl(), "1000"))
                    .into(binding.imageViewEventInfoProfile);

            // 업체명
            binding.companyName.setText(models.getCorp_name());

            // 이벤트 제목
            binding.textViewProductName.setText(models.getEvent_title());

            // 이벤트 기간
            binding.textviewEventDate.setText(models.getStart_date() + " - " + models.getEnd_date());

            // 이벤트 본문 이미지
            if (models.getContent_file_srl() == null || models.getContent_file_srl().equals(""))
                binding.imageViewEventInfo.setVisibility(View.GONE);
            else
                Glide.with(this)
                        .load(getImageViewUrl(models.getContent_file_srl(), "1000"))
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .into(binding.imageViewEventInfo);

            // 이벤트 참여 가능...
            String eventStatus = getStatusFromDate(models.getNow_date(), models.getStart_date(), models.getEnd_date());
            renderEventApplyButton(models.isCan_join(), eventStatus);

            // 이벤트 본문
            binding.textViewEventInfoDetailText.setText(htmlToString(models.getContent()));

            // 댓글 불러오기
            manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.frameLayout_event_activity
                    , BottomCommentFragment.newInstance(models.getDocument_srl())).commit();
        });

        viewModel.getIsEventCanLike().observe(this, aBoolean -> {
            if (aBoolean)
                binding.likeFilled.setImageResource(R.drawable.ic_heart_not_filled);
            else
                binding.likeFilled.setImageResource(R.drawable.ic_heart_filled);
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onResume() {
        super.onResume();

        viewModel.getIsLoading().observe(this, aBoolean -> {
            if (aBoolean != null) {
                if (aBoolean)
                    binding.loadingView.getRoot().setOnTouchListener((v, event) -> true);
                binding.loadingView.getRoot().setVisibility(aBoolean ? View.VISIBLE : View.GONE);
            }
        });

    }

    private final ActivityResultLauncher<Intent> finishResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null)
                        if (result.getData().getStringExtra("data").equals("finish"))
                            finish();
                }
            }
    );

    private String getStatusFromDate(String now_Date, String start_Date, String end_Date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null, endDate = null, nowDate = null;
        try {
            startDate = simpleDateFormat.parse(start_Date);
            endDate = simpleDateFormat.parse(end_Date);
            nowDate = simpleDateFormat.parse(now_Date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (nowDate.compareTo(startDate) >= 0
                && nowDate.compareTo(endDate) <= 0) {
            // 이벤트 기간 남았을 경우...
            return "progress";
        } else {
            int statusType;
            String statusText;
            if (nowDate.compareTo(endDate) <= 0) {
                return "notProgress";
            } else {
                return "deadLine";
            }
        }
    }

    private String getImageViewUrl(String fileSrl, String Width) {
        String result = getString(R.string.IMAGE_VIEW_URL) + "?inputFileSrl=" + fileSrl;
        if (Width != null) result += "&inputImageWidth=" + Width;
        return result;
    }

    private String htmlToString(String html) {
        return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
    }

    private void renderEventApplyButton(boolean isCanJoin, String eventStatus) {
        if (isCanJoin && eventStatus.equals("progress")) {
            binding.btnEventApply.setImageResource(R.drawable.radious_button_swebscolor);
            binding.textviewEventApply.setText("이벤트 신청하기");
            isApplied = true;
        } else {
            binding.btnEventApply.setImageResource(R.drawable.radious_button_graycolor);
            isApplied = false;
            
            if (eventStatus.equals("notProgress"))
                binding.textviewEventApply.setText("미 진행 이벤트");
            else if (eventStatus.equals("deadLine"))
                binding.textviewEventApply.setText("마감된 이벤트");
            else
                binding.textviewEventApply.setText("신청된 이벤트");
        }
    }
}