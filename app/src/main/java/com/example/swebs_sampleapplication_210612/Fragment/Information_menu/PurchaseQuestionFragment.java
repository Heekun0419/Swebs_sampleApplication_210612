package com.example.swebs_sampleapplication_210612.Fragment.Information_menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.swebs_sampleapplication_210612.databinding.FragmentPurchaseQuestionBinding;

import org.jetbrains.annotations.NotNull;

public class PurchaseQuestionFragment  extends Fragment {

    private FragmentPurchaseQuestionBinding binding;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPurchaseQuestionBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }
}
