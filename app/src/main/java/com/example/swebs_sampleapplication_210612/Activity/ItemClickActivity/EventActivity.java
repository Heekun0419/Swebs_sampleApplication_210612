package com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
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

public class EventActivity extends AppCompatActivity {
    private ActivityEventBinding binding;
    private EventViewModel viewModel;
    private FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEventBinding.inflate(getLayoutInflater());
        viewModel = new EventViewModel(getApplication());
        setContentView(binding.getRoot());

        // 댓글 불러오기
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frameLayout_event_activity, new BottomCommentFragment()).commit();

        // 이벤트 신청...
        binding.btnEventApply.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), EventApplyActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        });

        binding.btnItemClickedBack.setOnClickListener(v -> onBackPressed());

        viewModel.getEventDetailFromServer(getIntent().getStringExtra("eventSrl"));
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewModel.getIsLoading().observe(this, aBoolean -> {
            if (aBoolean != null)
                binding.loadingView.getRoot().setVisibility(aBoolean ? View.VISIBLE : View.GONE);
        });

        viewModel.getLiveEventDetailInfo().observe(this, models -> {
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

            // 이벤트 본문
            binding.textViewEventInfoDetailText.setText(htmlToString(models.getContent()));
        });
    }

    private String getImageViewUrl(String fileSrl, String Width) {
        String result = getString(R.string.IMAGE_VIEW_URL) + "?inputFileSrl=" + fileSrl;
        if (Width != null) result += "&inputImageWidth=" + Width;
        return result;
    }

    private String htmlToString(String html) {
        return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
    }
}