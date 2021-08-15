package com.example.swebs_sampleapplication_210612.Fragment.PointFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.Activity.InformationActivity;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.databinding.FragmentPointBinding;

public class PointFragment extends Fragment {

    private FragmentPointBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPointBinding.inflate(inflater,container,false);
        SPmanager sPmanager = new SPmanager(container.getContext());

        binding.textViewPointName.setText(sPmanager.getUserName());
        binding.textviewPoint.setText(sPmanager.getUserPoint());

        binding.pointUseCaution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = getString(R.string.point_advice_0);
                ((InformationActivity)requireActivity()).moveFragment(new PointAdviceFragment(), s);
            }
        });

        return binding.getRoot();
    }
}