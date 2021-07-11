package com.example.swebs_sampleapplication_210612;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.Activity.MainActivity;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMyPageBinding;

public class myPageFragment extends Fragment {

    private FragmentMyPageBinding binding;


    public myPageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyPageBinding.inflate(getLayoutInflater());

        binding.includedAppbarMy.imageButton.setOnClickListener(v -> {
            ((MainActivity)requireActivity()).drawer.openDrawer(GravityCompat.START);
        });

        binding.tutorialMyPage.textViewMyPageTutorialClose.setOnClickListener(v -> {
            binding.tutorialMyPage.getRoot().setVisibility(View.GONE);
        });

        binding.includedAppbarMy.imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)requireActivity()).BottomSheetOpen();
            }
        });
        return binding.getRoot();

    }
}