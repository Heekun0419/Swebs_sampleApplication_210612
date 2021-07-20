package com.example.swebs_sampleapplication_210612.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.swebs_sampleapplication_210612.adapter.ReviewMoreAdapter;
import com.example.swebs_sampleapplication_210612.adapter.SurveyAdapter;
import com.example.swebs_sampleapplication_210612.adapter.SurveyMoreAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMoreSurveyBinding;


public class MoreSurveyFragment extends Fragment {

    private FragmentMoreSurveyBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMoreSurveyBinding.inflate(inflater, container, false);

        SurveyMoreAdapter adapter = new SurveyMoreAdapter(requireContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewMoreSurvey.setLayoutManager(linearLayoutManager);
        binding.recyclerViewMoreSurvey.setAdapter(adapter);

        return binding.getRoot();
    }
}
