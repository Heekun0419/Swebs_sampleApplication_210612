package com.example.swebs_sampleapplication_210612.Fragment.Information_menu;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.Activity.InformationActivity;
import com.example.swebs_sampleapplication_210612.Activity.LoginActivity;
import com.example.swebs_sampleapplication_210612.Activity.MainActivity;
import com.example.swebs_sampleapplication_210612.Dialog.BasicDialogTextModel;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.LanguageDialog;
import com.example.swebs_sampleapplication_210612.Dialog.PermissionDialog;
import com.example.swebs_sampleapplication_210612.Dialog.TwoButtonBasicDialog;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.databinding.FragmentAppInformationBinding;

public class AppInformationFragment extends Fragment {

    private FragmentAppInformationBinding binding;
    private SPmanager sPmanager;
    LanguageDialog dialog;
    TwoButtonBasicDialog logOutDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = container.getContext();
        sPmanager = new SPmanager(context);
        binding = FragmentAppInformationBinding.inflate(inflater,container,false);
        binding.btnChangeLanguage.setOnClickListener(v -> {
            dialog = new LanguageDialog(requireContext(), new DialogClickListener() {
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
                    dialog.dismiss();
                }

                @Override
                public void onCloseClick() {
                    dialog.dismiss();
                }
            });
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.show();
        });

        binding.textViewLogOut.setOnClickListener(v -> {
            logOutDialog = new TwoButtonBasicDialog(requireContext(), new BasicDialogTextModel("로그아웃",
                    "정말로 로그아웃 하시겠습니까?", "로그아웃", "취소"), new DialogClickListener() {
                @Override
                public void onPositiveClick(int position) {
                    if (sPmanager.getUserType().equals("guest")) {
                        Toast.makeText(requireContext(), "게스트 계정입니다", Toast.LENGTH_SHORT).show();
                    } else {
                        sPmanager.removeUserBirth();
                        sPmanager.removeUserGender();
                        sPmanager.removeUserInfo();
                        sPmanager.removeUserPoint();
                        sPmanager.removeUserType();
                        sPmanager.removeUserName();
                        ((InformationActivity) requireActivity()).finishAffinity();
                        Intent intent = new Intent(requireContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onNegativeClick() {

                }

                @Override
                public void onCloseClick() {

                }
            });
            logOutDialog.setCancelable(false);
            //배경색 투명하게 하기
            logOutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            logOutDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            logOutDialog.show();
        });

        binding.textViewAppInfoDelete.setOnClickListener(v ->
                ((InformationActivity)requireActivity()).moveFragment(new DeleteIdFargment(),"탈퇴하기"));

        binding.textViewServiceAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = getString(R.string.service_terms_agree1);
                ((InformationActivity)requireActivity()).moveFragment(new ServiceTermsFragment(),s);
            }
        });
        return binding.getRoot();
    }
}