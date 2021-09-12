package com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.swebs_sampleapplication_210612.ViewModel.Model.SurveyListModel;
import com.example.swebs_sampleapplication_210612.adapter.TablayoutAdapter.SurveyMoreAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMoreSurveyBinding;

import java.util.ArrayList;
import java.util.List;


public class MoreSurveyFragment extends Fragment {

    private FragmentMoreSurveyBinding binding;
    private String categorySrl;
    private List<SurveyListModel> list = new ArrayList<>();
    public MoreSurveyFragment(){
    }

    public static MoreSurveyFragment newInstance(String categorySrl) {

        Bundle args = new Bundle();
        args.putString("category_srl", categorySrl);
        MoreSurveyFragment fragment = new MoreSurveyFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            categorySrl = getArguments().getString("category_srl");
        addList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMoreSurveyBinding.inflate(inflater, container, false);

        SurveyMoreAdapter adapter = new SurveyMoreAdapter(requireContext(), list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewMoreSurvey.setLayoutManager(linearLayoutManager);
        binding.recyclerViewMoreSurvey.setAdapter(adapter);

        return binding.getRoot();
    }

    private void addList(){
        for(int i =0; i<10; i++)
        list.add(new SurveyListModel(
                1
                , "진행중"
                , "0"
                , "0"
                , "화장품"
                , "마스크팩의 수분 유지력은 어떤가요?"
                , "3 일 남음"
                , "10"
                , "120"
        ));
    }
}
