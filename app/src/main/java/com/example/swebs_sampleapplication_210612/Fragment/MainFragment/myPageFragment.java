package com.example.swebs_sampleapplication_210612.Fragment.MainFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.Activity.MainActivity;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMyPageBinding;

import org.jetbrains.annotations.NotNull;

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

        // 닫기버튼 누르면 튜토리얼 닫힘.
        binding.tutorialMyPage.textViewMyPageTutorialClose.setOnClickListener(v -> {
            binding.tutorialMyPage.getRoot().setVisibility(View.GONE); });
        binding.tutorialMyPage.imageButton5.setOnClickListener(v -> {
            binding.tutorialMyPage.getRoot().setVisibility(View.GONE); });

        // 네비게이션 드로어 열기
        binding.includedAppbarMy.imageButton.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).drawer.openDrawer(GravityCompat.START);
        });

        // 바텀시트 열기
        binding.includedAppbarMy.imageButton2.setOnClickListener(v ->
                ((MainActivity) requireActivity()).BottomSheetOpen());


        return binding.getRoot();

    }

}