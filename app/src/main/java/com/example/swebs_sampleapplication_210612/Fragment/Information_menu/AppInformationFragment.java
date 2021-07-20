package com.example.swebs_sampleapplication_210612.Fragment.Information_menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.Activity.InformationActivity;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.LanguageDialog;
import com.example.swebs_sampleapplication_210612.Dialog.PermissionDialog;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.FragmentAppInformationBinding;

public class AppInformationFragment extends Fragment {

    private FragmentAppInformationBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAppInformationBinding.inflate(inflater,container,false);

        binding.btnChangeLanguage.setOnClickListener(v -> {
            LanguageDialog dialog = new LanguageDialog(requireContext(), new DialogClickListener() {
                @Override
                public void onPositiveClick(int position) {
                    String pickValue = "";
                    if(position == 0) pickValue = "한국어";
                    else if(position == 1) pickValue = "영어";
                    else if(position == 2) pickValue = "중국어";
                    else if(position == 3) pickValue = "일본어";
                    else pickValue ="한국어";

                    Toast.makeText(requireContext(),"선택 언어 : "+ pickValue ,Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onNegativeClick() {

                }
            });
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.show();
        });

        binding.textViewAppInfoDelete.setOnClickListener(v -> ((InformationActivity)requireActivity()).moveFragment(new DeleteIdFargment(),"탈퇴하기"));

        return binding.getRoot();
    }
}