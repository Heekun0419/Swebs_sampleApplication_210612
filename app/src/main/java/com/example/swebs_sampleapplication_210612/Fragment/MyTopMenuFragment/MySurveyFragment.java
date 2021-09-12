package com.example.swebs_sampleapplication_210612.Fragment.MyTopMenuFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.SurveyModel;
import com.example.swebs_sampleapplication_210612.ViewModel.SurveyVIewModel;
import com.example.swebs_sampleapplication_210612.adapter.TablayoutAdapter.SurveyMoreAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMySurveyBinding;

import java.util.List;


public class MySurveyFragment extends Fragment {

    private FragmentMySurveyBinding binding;
    private SPmanager sPmanager;
    private SurveyVIewModel vIewModel;
    private String tabPosition;
    SurveyMoreAdapter adapter;

    public static MySurveyFragment newInstance(String tabPosition) {
        Bundle args = new Bundle();
        args.putString("tab", tabPosition);
        MySurveyFragment fragment = new MySurveyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tabPosition = getArguments().getString("tab");
        }
        sPmanager = new SPmanager(requireContext());
        vIewModel = new SurveyVIewModel(requireActivity().getApplication());
        vIewModel.getMySurveyListFromServer(sPmanager.getUserSrl());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMySurveyBinding.inflate(inflater,container,false);

        vIewModel.getLiveDataSurveyList().observe(getViewLifecycleOwner(), surveyModels -> {
            initRecycler(surveyModels);
        });

        return binding.getRoot();
    }


    private void initRecycler(List<SurveyModel> list){
        adapter = new SurveyMoreAdapter(requireContext(), list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewMySurvey.setLayoutManager(linearLayoutManager);
        binding.recyclerViewMySurvey.setAdapter(adapter);

    }
}