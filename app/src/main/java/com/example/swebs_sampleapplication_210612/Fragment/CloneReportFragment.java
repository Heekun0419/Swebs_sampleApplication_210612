package com.example.swebs_sampleapplication_210612.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.FragmentCloneReportBinding;
import com.google.android.material.datepicker.MaterialDatePicker;

public class CloneReportFragment extends Fragment {

    private FragmentCloneReportBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment'
        binding = FragmentCloneReportBinding.inflate(inflater,container,false);

        binding.btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date")
                        .build();

                datePicker.show(getChildFragmentManager(),"datePicker");
            }
        });

        return binding.getRoot();
    }
}