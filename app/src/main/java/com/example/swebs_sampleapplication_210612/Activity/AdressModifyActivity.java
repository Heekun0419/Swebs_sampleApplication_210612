package com.example.swebs_sampleapplication_210612.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.ViewModel.MyInfoViewModel;
import com.example.swebs_sampleapplication_210612.databinding.ActivityAdressModifyBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class AdressModifyActivity extends AppCompatActivity {
    private ActivityAdressModifyBinding binding;
    private MyInfoViewModel viewModel;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdressModifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new MyInfoViewModel(getApplication());

        // 이벤트 정보 얻어오기
        viewModel.getEventAddressFromServer();

        binding.btnInformationActivityBack.setOnClickListener(v -> onBackPressed());

        binding.btnAddressSearch.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddressSearchActivity.class);
            AddressResult.launch(intent);
        });

        // 저장하기...
        binding.btnModifyProgress.setOnClickListener(v -> {
            modifyEventInfo();
        });

        // 서버에서 얻오기
        viewModel.getEventAddressInfo().observe(this, models -> {
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

        // 수정 완료
        viewModel.getProgressResult().observe(this, s -> {
            if (s != null) {
                switch (s) {
                    case "modifySuccess":
                        finish();
                        break;
                    case "modifyFailed":

                        break;
                    case "serverError":

                        break;
                }
            }
        });

        // 로딩
        viewModel.getIsLoading().observe(this, aBoolean -> {
            if (aBoolean != null) {
                if (aBoolean)
                    binding.loadingView.getRoot().setOnTouchListener((v, event) -> true);
                binding.loadingView.getRoot().setVisibility(aBoolean ? View.VISIBLE : View.GONE);
            }
        });

        binding.editTextPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    private final ActivityResultLauncher<Intent> AddressResult = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                if (result.getData() != null)
                    binding.addressData1.setText(result.getData().getStringExtra("data"));
            }
        }
    );

    private void modifyEventInfo() {
        viewModel.addressModify(
                binding.editTextName.getText().toString(),
                binding.editTextPhoneNumber.getText().toString(),
                binding.addressData1.getText().toString(),
                binding.addressData2.getText().toString()
        );
    }
}