package com.example.swebs_sampleapplication_210612.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdressModifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new MyInfoViewModel(getApplication());

        binding.btnInformationActivityBack.setOnClickListener(v -> onBackPressed());

        binding.btnAddressSearch.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddressSearchActivity.class);
            AddressResult.launch(intent);
        });

        binding.btnMakeAccountOk.setOnClickListener(v -> {

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
}