package com.example.swebs_sampleapplication_210612.Activity.EventApply;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Activity.AddressSearchActivity;
import com.example.swebs_sampleapplication_210612.Activity.TermsActivity;
import com.example.swebs_sampleapplication_210612.Activity.TopMenuActivity.TopMenuActivity;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventOptionModel;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.EventApplyDialog;
import com.example.swebs_sampleapplication_210612.Dialog.NumberPickerDialog2;
import com.example.swebs_sampleapplication_210612.Dialog.OneButtonBasicDialog;
import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.BasicDialogTextModel;
import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.NumberPickerModel2;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.EventViewModel;
import com.example.swebs_sampleapplication_210612.ViewModel.MyInfoViewModel;
import com.example.swebs_sampleapplication_210612.databinding.ActivityApplyEventBinding;

import java.util.ArrayList;
import java.util.List;

public class EventApplyActivity extends AppCompatActivity {
    private final String DIALOG_TITLE = "이벤트 신청 안내";
    private ActivityApplyEventBinding binding;
    private MyInfoViewModel myInfoViewModel;
    private EventViewModel eventViewModel;
    private List<EventOptionModel> eventOption;
    private String selectOptionSrl = null;
    private String eventPartSrl;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApplyEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myInfoViewModel = new MyInfoViewModel(getApplication());
        eventViewModel = new EventViewModel(getApplication());

        myInfoViewModel.getEventAddressFromServer();

        // 이벤트 정보 불러오기...
        eventViewModel.getEventDetailFromServer(getIntent().getStringExtra("eventSrl"));

        // 이벤트 옵션 불러오기...
        eventViewModel.getEventOption(getIntent().getStringExtra("eventSrl"));

        // 이벤트 신청하기 버튼
        binding.btnEventApplyOk.setOnClickListener(v -> {
            if(binding.checkBoxTerms.isChecked()) {
                EventApplyDialog();
            } else {
                Toast.makeText(this, "약관 동의가 필요합니다.", Toast.LENGTH_SHORT).show();
            }
            pushEventApply();
        });

        // 뒤로가기 클릭
        binding.btnBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        });

        // 약관 보기 버튼 클릭
        binding.showTerms.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TermsActivity.class);
            intent.putExtra("url","http://3.35.249.81/ToS/ToS_S.html");
            startActivity(intent);
        });
        // 주소 검색
        binding.btnAddressSearch.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddressSearchActivity.class);
            AddressResult.launch(intent);
        });

        // 옵션 선택
        binding.constraintLayoutOptionSelect.setOnClickListener(v -> {
            if (eventOption.size() > 0)
                showOptionSelectDialog();
        });


        // 이벤트 배송 정보 UI UPDATE
        myInfoViewModel.getEventAddressInfo().observe(this, models -> {
            if (models != null) {
                if (models.getName() != null)
                    binding.editTextName.setText(models.getName());
                if (models.getPhone_number() != null)
                    binding.editTextPhoneNumber.setText(models.getPhone_number());
                if (models.getAddress1() != null)
                    binding.addressData1.setText(models.getAddress1());
                if (models.getAddress2() != null)
                    binding.addressData2.setText(models.getAddress2());
            }
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

        // 이벤트 옵션 UI UPDATE
        eventViewModel.getLiveEventOption().observe(this, models -> {
            eventOption = models;
            if (eventOption.size() == 0)
                binding.textViewOptionSelect.setText("옵션이 없습니다.");
            if (eventOption.size() == 1) {
                selectOptionSrl = eventOption.get(0).getOption_srl();
                binding.textViewOptionSelect.setText(eventOption.get(0).getOption_title());
            }
        });

        // 이벤트 신청 결과 UI UPDATE
        eventViewModel.getLiveApplyEventPartSrl().observe(this, s -> {
            if (s != null) {
                eventPartSrl = s;
                EventApplyDialog();
            }
        });
    }

    private final ActivityResultLauncher<Intent> AddressResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null)
                        binding.addressData1.setText(result.getData().getStringExtra("data"));
                }
            }
    );

    private void pushEventApply() {
        // 빈칸 체크
        String dialogMsg = null;
        if (binding.editTextName.getText().length() <= 0)
            dialogMsg = "이름을 입력 해주세요.";
        else if (binding.editTextPhoneNumber.getText().length() <= 0)
            dialogMsg = "전화번호를 입력 해주세요.";
        else if (binding.addressData1.getText().length() <= 0)
            dialogMsg = "주소를 입력해주세요.";
        else if (eventOption.size() >= 1 && selectOptionSrl == null)
            dialogMsg = "옵션을 선택 해주세요.";

        if (dialogMsg != null) {
            showOneButtonDialog(DIALOG_TITLE, dialogMsg);
            return;
        }

        eventViewModel.pushEventApply(
                getIntent().getStringExtra("eventSrl"),
                binding.editTextName.getText().toString(),
                binding.editTextPhoneNumber.getText().toString(),
                binding.addressData1.getText().toString(),
                binding.addressData2.getText().toString(),
                selectOptionSrl
        );
    }


    private void EventApplyDialog() {
        EventApplyDialog dialog = new EventApplyDialog(this, new DialogClickListener() {
            @Override
            public void onPositiveClick(int position) {
                setResultFinish();
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }

            @Override
            public void onNegativeClick() {
                Intent intent = new Intent(getApplicationContext(), EventRegisterSuccessActivity.class);
                intent.putExtra("eventSrl", getIntent().getStringExtra("eventSrl"));
                intent.putExtra("partSrl", eventPartSrl);
                startActivity(intent);

                setResultFinish();
                finish();
            }

            @Override
            public void onCloseClick() {

            }
        }, new BasicDialogTextModel("이벤트 신청 완료",
                "이벤트 신청을 완료했습니다.",
                "다른 이벤트 보러가기",
                "신청 이벤트 확인하기"));

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
    }

    private void setResultFinish() {
        Bundle extra = new Bundle();
        Intent intent = new Intent();
        extra.putString("data", "finish");
        intent.putExtras(extra);
        setResult(RESULT_OK, intent);
    }

    private void showOptionSelectDialog() {
        List<String> inputData = new ArrayList<>();
        for (int i=0; i < eventOption.size(); i++)
            inputData.add(eventOption.get(i).getOption_title());

        int defaultValue = 0;

        NumberPickerDialog2 dialog = new NumberPickerDialog2(
                this,
                new NumberPickerModel2("성별",
                        inputData,
                        defaultValue,
                        "확인",
                        "취소"
                ),
                new DialogClickListener() {
                    @Override
                    public void onPositiveClick(int position) {
                        binding.textViewOptionSelect.setText(eventOption.get(position).getOption_title());
                        selectOptionSrl = eventOption.get(position).getOption_srl();
                    }

                    @Override
                    public void onNegativeClick() {

                    }

                    @Override
                    public void onCloseClick() {

                    }
                }
        );
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private String getImageViewUrl(String fileSrl, String Width) {
        String result = getString(R.string.IMAGE_VIEW_URL) + "?inputFileSrl=" + fileSrl;
        if (Width != null) result += "&inputImageWidth=" + Width;
        return result;
    }

    private void showOneButtonDialog(String title, String content) {
        OneButtonBasicDialog oneButtonBasicDialog = new OneButtonBasicDialog(this
                , new BasicDialogTextModel(title, content, "확인", "")
                , new DialogClickListener() {
            @Override
            public void onPositiveClick(int position) {

            }

            @Override
            public void onNegativeClick() {

            }

            @Override
            public void onCloseClick() {

            }
        });

        oneButtonBasicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        oneButtonBasicDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        oneButtonBasicDialog.show();
    }
}