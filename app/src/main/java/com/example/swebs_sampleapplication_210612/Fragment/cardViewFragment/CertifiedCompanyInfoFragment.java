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

    String ImageUrl = "https://i.pinimg.com/originals/a2/4f/e6/a24fe6cabab71872039e30af52e7dd9e.png";
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
        GlideImage(binding.imageViewCertifiedInfoProfile);

        binding.btnProductInfoMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.gradientWhite.setVisibility(View.GONE);
                binding.constraintLayoutBtnLayout.setVisibility(View.GONE);
                binding.textViewProductInfo.setMaxLines(300);
            }
        });

        binding.btnAS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), InformationActivity.class);
                intent.putExtra("resultCode", "AS");
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }

    private void GlideImage(ImageView view){
        Glide.with(requireContext()).load(ImageUrl).into(view);
    }
}