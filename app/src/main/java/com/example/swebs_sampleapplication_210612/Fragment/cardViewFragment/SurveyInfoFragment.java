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
    private String Url = "https://i.pinimg.com/originals/a2/dc/00/a2dc008c48ce7d7934fbc5538166b8ff.png";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding =FragmentSurveyInfoBinding.inflate(inflater,container,false);

       ImageView view = binding.imageViewProductSurveyProfile;
       GlideImage(view);

        binding.btnSurvey1.setOnClickListener(v -> { btn_select1(); });
        binding.btnSurvey2.setOnClickListener(v -> btn_select2());
        binding.btnSurvey3.setOnClickListener(v -> btn_select3());
        binding.btnSurvey4.setOnClickListener(v -> btn_select4());


       return binding.getRoot();
    }
    private void GlideImage(ImageView view){
        Glide.with(requireContext()).load(Url).into(view);
    }


    private void btn_select1(){
        binding.btnSurvey1.setSelected(true);
        binding.textViewSurveyInfo1.setTextColor(Color.parseColor("#21CCB2"));
        binding.btnSurvey2.setSelected(false);
        binding.textViewSurveyInfo2.setTextColor(Color.parseColor("#000000"));
        binding.btnSurvey3.setSelected(false);
        binding.textViewSurveyInfo3.setTextColor(Color.parseColor("#000000"));
        binding.btnSurvey4.setSelected(false);
        binding.textViewSurveyInfo4.setTextColor(Color.parseColor("#000000"));
    } private void btn_select2(){
        binding.btnSurvey2.setSelected(true);
        binding.textViewSurveyInfo2.setTextColor(Color.parseColor("#21CCB2"));
        binding.btnSurvey1.setSelected(false);
        binding.textViewSurveyInfo1.setTextColor(Color.parseColor("#000000"));
        binding.btnSurvey3.setSelected(false);
        binding.textViewSurveyInfo3.setTextColor(Color.parseColor("#000000"));
        binding.btnSurvey4.setSelected(false);
        binding.textViewSurveyInfo4.setTextColor(Color.parseColor("#000000"));
    }
    private void btn_select3(){
        binding.btnSurvey3.setSelected(true);
        binding.textViewSurveyInfo3.setTextColor(Color.parseColor("#21CCB2"));
        binding.btnSurvey2.setSelected(false);
        binding.textViewSurveyInfo2.setTextColor(Color.parseColor("#000000"));
        binding.btnSurvey1.setSelected(false);
        binding.textViewSurveyInfo1.setTextColor(Color.parseColor("#000000"));
        binding.btnSurvey4.setSelected(false);
        binding.textViewSurveyInfo4.setTextColor(Color.parseColor("#000000"));
    }
    private void btn_select4(){
        binding.btnSurvey4.setSelected(true);
        binding.textViewSurveyInfo4.setTextColor(Color.parseColor("#21CCB2"));
        binding.btnSurvey2.setSelected(false);
        binding.textViewSurveyInfo2.setTextColor(Color.parseColor("#000000"));
        binding.btnSurvey3.setSelected(false);
        binding.textViewSurveyInfo3.setTextColor(Color.parseColor("#000000"));
        binding.btnSurvey1.setSelected(false);
        binding.textViewSurveyInfo1.setTextColor(Color.parseColor("#000000"));
    }

}