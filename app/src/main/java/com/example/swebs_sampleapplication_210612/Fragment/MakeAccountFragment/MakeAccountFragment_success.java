package com.example.swebs_sampleapplication_210612.Fragment.MakeAccountFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.Activity.MainActivity;
import com.example.swebs_sampleapplication_210612.Activity.MakeAccountActivity;
import com.example.swebs_sampleapplication_210612.Activity.PermissionCheckActivity;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.PermissionDialog;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMakeAccountSuccessBinding;

import java.security.Permission;

public class MakeAccountFragment_success extends Fragment {

    private FragmentMakeAccountSuccessBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =FragmentMakeAccountSuccessBinding.inflate(inflater,container,false);

        PermissionDialog dialog = new PermissionDialog(requireContext(), new DialogClickListener() {
            @Override
            public void onPositiveClick(int position) {
                Toast.makeText(requireContext(),"권한 모두 허용",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNegativeClick() {
                Intent intent = new Intent(requireContext(), PermissionCheckActivity.class);
                startActivity(intent);
            }
        });
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();

        binding.btnMakeAccountOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), MainActivity.class);
                startActivity(intent);
                ((MakeAccountActivity)requireActivity()).finish();
            }
        });

        return binding.getRoot();
    }
}