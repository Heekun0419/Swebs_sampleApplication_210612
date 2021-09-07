package com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.FragmentSurveyInfoBinding;

public class SurveyInfoFragment extends Fragment {

    private FragmentSurveyInfoBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding =FragmentSurveyInfoBinding.inflate(inflater,container,false);



       return binding.getRoot();
    }





}