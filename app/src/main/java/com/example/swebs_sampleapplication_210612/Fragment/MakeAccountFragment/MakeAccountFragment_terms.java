package com.example.swebs_sampleapplication_210612.Fragment.MakeAccountFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.Activity.MakeAccountActivity;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMakeAccountTermsBinding;

public class MakeAccountFragment_terms extends Fragment {

    private FragmentMakeAccountTermsBinding binding;
    private Fragment fragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMakeAccountTermsBinding.inflate( inflater,container, false);


        binding.servicePrivateTerms.setOnClickListener(v ->
                ((MakeAccountActivity)requireActivity()).moveFragment(TermsFragment.newInstance("http://3.36.65.243/ToS/ToS_S.html"))
        );
        binding.locationTerms.setOnClickListener(v ->
                ((MakeAccountActivity)requireActivity()).moveFragment(TermsFragment.newInstance("http://3.36.65.243/ToS/ToS_L.html"))
        );
        binding.marketingTerms.setOnClickListener(v ->
                ((MakeAccountActivity)requireActivity()).moveFragment(TermsFragment.newInstance("http://3.36.65.243/ToS/ToS_M.html"))
        );


        binding.btnTermsAgree.setOnClickListener(v -> {
            if(binding.checkBoxMakeAccountTerms1.isChecked() && binding.checkBoxMakeAccountTerms2.isChecked()){
                ((MakeAccountActivity)requireActivity()).moveFragment(new MakeAccountFragment_userInfo());
            }
            else {
                Toast.makeText(requireContext(),"필수약관 동의를 해주세요.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.checkBoxMakeAccountTermsAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


        return binding.getRoot();
    }
    private void termsCheck(){
        if(binding.checkBoxMakeAccountTermsAll.isChecked()) binding.checkBoxMakeAccountTermsAll.setChecked(false);
        else if(binding.checkBoxMakeAccountTerms1.isChecked() && binding.checkBoxMakeAccountTerms2.isChecked()
                && binding.checkBoxMakeAccountTerms3.isChecked()){
            binding.checkBoxMakeAccountTermsAll.setChecked(true);
        }
    }
}