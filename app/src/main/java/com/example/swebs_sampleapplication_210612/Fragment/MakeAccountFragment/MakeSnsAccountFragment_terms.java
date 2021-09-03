package com.example.swebs_sampleapplication_210612.Fragment.MakeAccountFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.Activity.TermsActivity;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMakeSnsAccountTermsBinding;

public class MakeSnsAccountFragment_terms extends Fragment {
    private FragmentMakeSnsAccountTermsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMakeSnsAccountTermsBinding.inflate(inflater,container,false);


        binding.servicePrivateTerms.setOnClickListener(v ->{
            IntentTermsActivity("http://3.35.249.81/ToS/ToS_S.html");
        });

        binding.locationTerms.setOnClickListener(v ->{
            IntentTermsActivity("http://3.35.249.81/ToS/ToS_L.html");
        });

        binding.marketingTerms.setOnClickListener(v ->{
            IntentTermsActivity("http://3.35.249.81/ToS/ToS_M.html");
        });

        binding.checkBoxMakeAccountTermsAll.setOnClickListener(v -> allTermsCheck());
        binding.layoutTermsAll.setOnClickListener(v -> {
            binding.checkBoxMakeAccountTermsAll.setChecked(!binding.checkBoxMakeAccountTermsAll.isChecked());
            allTermsCheck();
        });

        binding.checkBoxMakeAccountTerms1.setOnClickListener(v -> termsCheck());
        binding.layoutTerms1.setOnClickListener(v ->{
            binding.checkBoxMakeAccountTerms1.setChecked(!binding.checkBoxMakeAccountTerms1.isChecked());
            termsCheck();
        });

        binding.checkBoxMakeAccountTerms2.setOnClickListener(v -> termsCheck());
        binding.layoutTerms2.setOnClickListener(v ->{
            binding.checkBoxMakeAccountTerms2.setChecked(!binding.checkBoxMakeAccountTerms2.isChecked());
            termsCheck();
        });

        binding.checkBoxMakeAccountTerms3.setOnClickListener(v -> termsCheck());
        binding.layoutTerms3.setOnClickListener(v ->{
            binding.checkBoxMakeAccountTerms3.setChecked(!binding.checkBoxMakeAccountTerms3.isChecked());
            termsCheck();
        });

        return binding.getRoot();
    }

    private void IntentTermsActivity(String Url){
        Intent intent = new Intent(requireActivity().getApplicationContext(), TermsActivity.class);
        intent.putExtra("url",Url);
        startActivity(intent);
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
}