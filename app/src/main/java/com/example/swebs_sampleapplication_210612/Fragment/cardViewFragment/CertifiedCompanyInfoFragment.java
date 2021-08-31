package com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Activity.InformationActivity;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.FragmentCertifiedCompanyInfoBinding;


public class CertifiedCompanyInfoFragment extends Fragment {


    private FragmentCertifiedCompanyInfoBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding =FragmentCertifiedCompanyInfoBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment


        binding.btnProductInfoMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.gradientWhite.setVisibility(View.GONE);
                binding.constraintLayoutBtnLayout.setVisibility(View.GONE);
                binding.textViewProductInfo.setMaxLines(300);
            }
        });


        return binding.getRoot();
    }


}