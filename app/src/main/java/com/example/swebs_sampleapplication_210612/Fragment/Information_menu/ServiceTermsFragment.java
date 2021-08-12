package com.example.swebs_sampleapplication_210612.Fragment.Information_menu;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.Activity.InformationActivity;
import com.example.swebs_sampleapplication_210612.Fragment.MakeAccountFragment.TermsFragment;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.FragmentServiceTermsBinding;

public class ServiceTermsFragment extends Fragment {
    private FragmentServiceTermsBinding binding;
    String string;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentServiceTermsBinding.inflate(inflater,container,false);
        string  = getString(R.string.service_terms_agree1);
        // 필수권한 Check
        binding.checkBox01.setChecked(true);
        binding.checkBox02.setChecked(true);
        binding.checkBox03.setChecked(true);
        binding.checkBox04.setChecked(true);

        binding.showTerms1.setOnClickListener(v ->
                ((InformationActivity)requireActivity()).moveFragment(
                TermsFragment.newInstance("http://3.36.65.243/ToS/ToS_S.html"),string));
        binding.showTerms2.setOnClickListener(v ->
                ((InformationActivity)requireActivity()).moveFragment(
                        TermsFragment.newInstance("http://3.36.65.243/ToS/ToS_S.html"),string));
        binding.showTerms3.setOnClickListener(v ->
                ((InformationActivity)requireActivity()).moveFragment(
                        TermsFragment.newInstance("http://3.36.65.243/ToS/ToS_S.html"),string));
        binding.showTerms4.setOnClickListener(v ->
                ((InformationActivity)requireActivity()).moveFragment(
                        TermsFragment.newInstance(" http://3.36.65.243/ToS/ToS_L.html"),string));
        binding.showTerms5.setOnClickListener(v ->
                ((InformationActivity)requireActivity()).moveFragment(
                        TermsFragment.newInstance(" http://3.36.65.243/ToS/ToS_M.html"),string));
        binding.showTerms6.setOnClickListener(v ->
                ((InformationActivity)requireActivity()).moveFragment(
                        TermsFragment.newInstance(" http://3.36.65.243/ToS/ToS_I.html"),string));

        return binding.getRoot();
    }
}