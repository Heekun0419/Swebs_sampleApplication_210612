package com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.ViewModel.EventViewModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.EventModel;
import com.example.swebs_sampleapplication_210612.adapter.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.adapter.TablayoutAdapter.EventMoreAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMoreEventBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MoreEventFragment extends Fragment implements OnItemClickListener {

    private FragmentMoreEventBinding binding;
    private EventViewModel viewModel;
    private int position;

    // viewPager 및 TabLayout position 받아옴.
    public MoreEventFragment(int position){
        this.position = position;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new EventViewModel(requireActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMoreEventBinding.inflate(inflater,container,false);

        //ViewModel 에서 리스트로 받아오기
        viewModel.getLiveEventList().observe(getViewLifecycleOwner(), new Observer<ArrayList<EventModel>>() {
            @Override
            public void onChanged(ArrayList<EventModel> list) {
                initEventRecycler(list);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onItemSelected(View view, int position, String code) {
    }

    private void initEventRecycler(ArrayList<EventModel> list){
        EventMoreAdapter adapter = new EventMoreAdapter(requireContext(),list,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewMoreEvent.setLayoutManager(linearLayoutManager);
        binding.recyclerViewMoreEvent.setAdapter(adapter);
    }
}