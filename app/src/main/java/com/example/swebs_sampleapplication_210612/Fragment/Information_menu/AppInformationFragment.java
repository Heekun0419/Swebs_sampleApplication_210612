package com.example.swebs_sampleapplication_210612.Fragment.Information_menu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.Activity.InformationActivity;
import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Listener.netSignupListener;
import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.BasicDialogTextModel;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.NumberPickerDialog;
import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.NumberPickerModel;
import com.example.swebs_sampleapplication_210612.Dialog.TwoButtonBasicDialog;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.databinding.FragmentAppInformationBinding;
import com.example.swebs_sampleapplication_210612.util.UserLoginController;

public class AppInformationFragment extends Fragment {

    private FragmentAppInformationBinding binding;
    private SPmanager sPmanager;
    NumberPickerDialog dialog;
    TwoButtonBasicDialog logOutDialog;
    MyInfoRepository myInfoRepository;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myInfoRepository = new MyInfoRepository(requireActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = container.getContext();
        sPmanager = new SPmanager(context);
        binding = FragmentAppInformationBinding.inflate(inflater,container,false);

        binding.btnChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.btnChangeLanguage.setOnClickListener(v -> {
            dialog = new NumberPickerDialog(requireContext(),
                    new NumberPickerModel("??????",
                            new String[]{"?????????","ENGLISH","??????"},
                            1,
                            "??????",
                            "??????")
                    , new DialogClickListener() {
                @Override
                public void onPositiveClick(int position) {
                    String pickValue ;
                    if(position == 0) pickValue = "?????????";
                    else if(position == 1) pickValue = "??????";
                    else if(position == 2) pickValue = "?????????";
                    else if(position == 3) pickValue = "?????????";
                    else pickValue ="?????????";

                    Toast.makeText(requireContext(),"?????? ?????? : "+ pickValue ,Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onNegativeClick() {
                }
                @Override
                public void onCloseClick() {
                }
            });
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.show();
            dialog.setOnCancelListener(dialog -> {
                Toast.makeText(requireContext(), "?????????", Toast.LENGTH_SHORT).show();
            });
        });

        binding.textViewLogOut.setOnClickListener(v -> {
            logOutDialog = new TwoButtonBasicDialog(requireContext(), new BasicDialogTextModel("????????????",
                    "????????? ???????????? ???????????????????", "????????????", "??????"), new DialogClickListener() {
                @Override
                public void onPositiveClick(int position) {
                    if (sPmanager.getUserType().equals("guest")) {
                        Toast.makeText(requireContext(), "????????? ???????????????", Toast.LENGTH_SHORT).show();
                    } else {
                        UserLoginController userLoginController = new UserLoginController(
                                requireActivity().getApplication(),
                                new netSignupListener() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onFailed() {

                                    }

                                    @Override
                                    public void onServerError() {

                                    }
                                }
                        ).setActivity(requireActivity());
                        userLoginController.userLogout();
                        userLoginController.signUpForGuest();
                        Toast.makeText(requireContext(), "???????????? ??????", Toast.LENGTH_SHORT).show();
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
            //????????? ???????????? ??????
            logOutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            logOutDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            logOutDialog.show();
        });

        binding.textViewAppInfoDelete.setOnClickListener(v ->
                ((InformationActivity)requireActivity()).moveFragment(new DeleteIdFargment(),"????????????"));

        binding.textViewServiceAgree.setOnClickListener(v -> {
            String s = getString(R.string.service_terms_agree1);
            ((InformationActivity)requireActivity()).moveFragment(new ServiceTermsFragment(),s);
        });

        // ????????? ??????
        binding.layoutInstargram.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/sealticker"));
            startActivity(browserIntent);
        });

        // ???????????? ??????
        binding.layoutFacebook.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/SWEBS.official"));
            startActivity(browserIntent);
        });
        return binding.getRoot();
    }
}