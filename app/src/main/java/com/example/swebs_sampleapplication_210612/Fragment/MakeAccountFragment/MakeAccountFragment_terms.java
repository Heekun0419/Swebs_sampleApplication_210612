package com.example.swebs_sampleapplication_210612.Fragment.MakeAccountFragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.Activity.MakeAccountActivity;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;
import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.BasicDialogTextModel;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.TwoButtonBasicDialog;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMakeAccountTermsBinding;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeAccountFragment_terms extends Fragment {

    private FragmentMakeAccountTermsBinding binding;
    private Fragment fragment;
    private SwebsAPI swebsAPI;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        swebsAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMakeAccountTermsBinding.inflate( inflater,container, false);


        binding.servicePrivateTerms.setOnClickListener(v ->
                ((MakeAccountActivity)requireActivity()).moveFragment(TermsFragment.newInstance("http://3.36.65.243/ToS/ToS_S.html"),"")
        );
        binding.locationTerms.setOnClickListener(v ->
                ((MakeAccountActivity)requireActivity()).moveFragment(TermsFragment.newInstance("http://3.36.65.243/ToS/ToS_L.html"),"")
        );
        binding.marketingTerms.setOnClickListener(v ->
                ((MakeAccountActivity)requireActivity()).moveFragment(TermsFragment.newInstance("http://3.36.65.243/ToS/ToS_M.html"),"")
        );


        binding.btnTermsAgree.setOnClickListener(v -> {
            if(binding.checkBoxMakeAccountTerms1.isChecked() && binding.checkBoxMakeAccountTerms2.isChecked()){
                //((MakeAccountActivity)requireActivity()).moveFragment(new MakeAccountFragment_userInfo());

                existReferralCode(binding.editTextRecommendCode.getText().toString());
            }
            else {
                Toast.makeText(requireContext(),"필수약관 동의를 해주세요.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.checkBoxMakeAccountTermsAll.setOnClickListener(v -> {
           allTermsCheck();
        });
        binding.textViewTermsAll.setOnClickListener(v -> {
            binding.checkBoxMakeAccountTermsAll.setChecked(!binding.checkBoxMakeAccountTermsAll.isChecked());
            allTermsCheck();
        });

       binding.checkBoxMakeAccountTerms1.setOnClickListener(v -> {
          termsCheck();
       });
        binding.checkBoxMakeAccountTerms2.setOnClickListener(v -> {
            termsCheck();
        });
        binding.checkBoxMakeAccountTerms3.setOnClickListener(v -> {
            termsCheck();
        });
        binding.textViewTerms1.setOnClickListener(v -> {
            binding.checkBoxMakeAccountTerms1.setChecked(!binding.checkBoxMakeAccountTerms1.isChecked());
            termsCheck();
        });
        binding.textViewTerms2.setOnClickListener(v -> {
            binding.checkBoxMakeAccountTerms2.setChecked(!binding.checkBoxMakeAccountTerms2.isChecked());
            termsCheck();
        });
        binding.textViewTerms3.setOnClickListener(v -> {
            binding.checkBoxMakeAccountTerms3.setChecked(!binding.checkBoxMakeAccountTerms3.isChecked());
            termsCheck();
        });


        return binding.getRoot();
    }

    private void termsCheck(){
        // 전체선택 되어있으면 해제
        if(binding.checkBoxMakeAccountTermsAll.isChecked()) {
            binding.checkBoxMakeAccountTermsAll.setChecked(false);
        }
        // 아닐시에는 전체 동의
        else if(binding.checkBoxMakeAccountTerms1.isChecked() && binding.checkBoxMakeAccountTerms2.isChecked()
                && binding.checkBoxMakeAccountTerms3.isChecked()){
            binding.checkBoxMakeAccountTermsAll.setChecked(true);
        }
    }
    private void allTermsCheck(){
        if(binding.checkBoxMakeAccountTermsAll.isChecked()){
            binding.checkBoxMakeAccountTerms1.setChecked(true);
            binding.checkBoxMakeAccountTerms2.setChecked(true);
            binding.checkBoxMakeAccountTerms3.setChecked(true);
        }else{
            binding.checkBoxMakeAccountTerms1.setChecked(false);
            binding.checkBoxMakeAccountTerms2.setChecked(false);
            binding.checkBoxMakeAccountTerms3.setChecked(false);
        }
    }

    private void existReferralCode(String referralCode) {
        if (referralCode == null || referralCode.equals("")) {
                progressMakeAccount();
            return;
        }

        HashMap<String, RequestBody> body = new HashMap<>();
        body.put("inputReferralCode", RequestBody.create(referralCode, MediaType.parse("text/plane")));
        Call<Boolean> call = swebsAPI.existReferralCode(body);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                        && response.body()) {
                        ((MakeAccountActivity)requireActivity()).setReferralCode(referralCode);
                        progressMakeAccount();
                    } else {
                        showReferralErrorDialog("추천인 코드 안내", "추천코드가 존재하지 않습니다.\n그대로 진행하히겠습니까?");
                    }
                } else {
                    showReferralErrorDialog("추천인 코드 안내", "추천코드가 존재하지 않습니다.\n그대로 진행하히겠습니까?");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                showReferralErrorDialog("추천인 코드 안내", "추천코드가 존재하지 않습니다.\n그대로 진행하히겠습니까?");
            }
        });
    }

    private void progressMakeAccount() {
        ((MakeAccountActivity)requireActivity()).moveFragment(new MakeAccountFragment_userInfo(),"");
    }

    private void showReferralErrorDialog(String title, String content) {
        TwoButtonBasicDialog twoButtonBasicDialog = new TwoButtonBasicDialog(requireContext()
                , new BasicDialogTextModel(title, content, "확인", "취소")
                , new DialogClickListener() {
            @Override
            public void onPositiveClick(int position) {
                progressMakeAccount();
            }

            @Override
            public void onNegativeClick() {

            }

            @Override
            public void onCloseClick() {

            }
        });
        twoButtonBasicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        twoButtonBasicDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        twoButtonBasicDialog.show();
    }
}