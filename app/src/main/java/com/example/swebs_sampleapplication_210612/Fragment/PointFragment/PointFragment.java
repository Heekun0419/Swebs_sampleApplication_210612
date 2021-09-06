package com.example.swebs_sampleapplication_210612.Fragment.PointFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.Activity.InformationActivity;
import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.databinding.FragmentPointBinding;

public class PointFragment extends Fragment {

    private FragmentPointBinding binding;
    private MyInfoRepository myInfoRepository;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myInfoRepository = new MyInfoRepository(requireActivity().getApplication());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPointBinding.inflate(inflater,container,false);

        binding.pointUseCaution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = getString(R.string.point_advice_0);
                ((InformationActivity)requireActivity()).moveFragment(new PointAdviceFragment(), s);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Point
        myInfoRepository.getValueToLiveData("point").observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    String viewText = s + " P";
                    binding.textviewPoint.setText(viewText);
                }
            }
        });

        // Name
        myInfoRepository.getValueToLiveData("nickName").observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    binding.textViewPointName.setText(s);
                }
            }
        });
    }
}