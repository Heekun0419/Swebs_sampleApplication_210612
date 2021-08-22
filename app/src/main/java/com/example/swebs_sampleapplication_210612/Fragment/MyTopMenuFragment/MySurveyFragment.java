package com.example.swebs_sampleapplication_210612.Fragment.MyTopMenuFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.adapter.TablayoutAdapter.SurveyMoreAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMySurveyBinding;


public class MySurveyFragment extends Fragment {
    private FragmentMySurveyBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMySurveyBinding.inflate(inflater,container,false);

        SurveyMoreAdapter adapter = new SurveyMoreAdapter(requireContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewMySurvey.setLayoutManager(linearLayoutManager);
        binding.recyclerViewMySurvey.setAdapter(adapter);

        return binding.getRoot();
    }
}