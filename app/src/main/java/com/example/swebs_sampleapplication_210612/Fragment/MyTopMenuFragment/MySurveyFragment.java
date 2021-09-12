package com.example.swebs_sampleapplication_210612.Fragment.MyTopMenuFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.ViewModel.Model.SurveyListModel;
import com.example.swebs_sampleapplication_210612.ViewModel.SurveyVIewModel;
import com.example.swebs_sampleapplication_210612.adapter.TablayoutAdapter.SurveyMoreAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMySurveyBinding;

import java.util.List;


public class MySurveyFragment extends Fragment {

    private FragmentMySurveyBinding binding;
    private SurveyVIewModel vIewModel;
    private String tabPosition;
    SurveyMoreAdapter adapter;

    public static MySurveyFragment newInstance(String tabPosition) {
        Bundle args = new Bundle();
        args.putString("tabPosition", tabPosition);
        MySurveyFragment fragment = new MySurveyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMySurveyBinding.inflate(inflater,container,false);

        vIewModel = new SurveyVIewModel(requireActivity().getApplication());

        if (getArguments() != null) {
            tabPosition = getArguments().getString("tabPosition");
            switch (tabPosition) {
                case "0":
                    vIewModel.getSurveyListFromServer("progress");
                    break;
                case "1":
                    vIewModel.getSurveyListFromServer("closed");
                    break;
                case "2":
                    vIewModel.getSurveyListFromServer("participate");
                    break;
            }
        }

        vIewModel.getLiveDataSurveyList().observe(getViewLifecycleOwner(), surveyModels -> {
            if (surveyModels != null && adapter == null)
                initRecycler(surveyModels);
            else {

            }
        });

        return binding.getRoot();
    }


    private void initRecycler(List<SurveyListModel> list){
        adapter = new SurveyMoreAdapter(requireContext(), list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewMySurvey.setLayoutManager(linearLayoutManager);
        binding.recyclerViewMySurvey.setAdapter(adapter);
    }
}