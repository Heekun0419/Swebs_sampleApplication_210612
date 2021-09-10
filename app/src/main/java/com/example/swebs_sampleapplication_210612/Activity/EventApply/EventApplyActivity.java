package com.example.swebs_sampleapplication_210612.Activity.EventApply;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.Activity.AddressSearchActivity;
import com.example.swebs_sampleapplication_210612.Activity.TopMenuActivity.TopMenuActivity;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.EventApplyDialog;
import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.BasicDialogTextModel;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityApplyEventBinding;

public class EventApplyActivity extends AppCompatActivity {

    private ActivityApplyEventBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApplyEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnEventApplyOk.setOnClickListener(v -> {
            EventApplyDialog();
        });

        binding.btnBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        });

    }


    private void EventApplyDialog(){
        EventApplyDialog dialog = new EventApplyDialog(this, new DialogClickListener() {
            @Override
            public void onPositiveClick(int position) {
               Intent intent = new Intent(getApplicationContext(), TopMenuActivity.class);
               intent.putExtra("resultCode","event");
               startActivity(intent);
               finish();
               overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }

            @Override
            public void onNegativeClick() {
                Intent intent = new Intent(getApplicationContext(), EventRegisterSuccessActivity.class);
                startActivity(intent);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}