package com.example.swebs_sampleapplication_210612.Fragment.MyTopMenuFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.ViewModel.EventViewModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.EventModel;
import com.example.swebs_sampleapplication_210612.adapter.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.adapter.TablayoutAdapter.EventMoreAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMyEventBinding;

import java.util.ArrayList;

public class MyEventFragment extends Fragment implements OnItemClickListener {

    private FragmentMyEventBinding binding;
    private EventViewModel viewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new EventViewModel(requireActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMyEventBinding.inflate(inflater,container,false);

        viewModel.getLiveEventList().observe(getViewLifecycleOwner(), eventModels -> {
            if (eventModels.size() > 0) {
                binding.noticeTextView.setVisibility(View.GONE);
                binding.recyclerViewMyEvent.setVisibility(View.VISIBLE);
                initEventRecycler(eventModels);
            } else {
                binding.noticeTextView.setVisibility(View.VISIBLE);
                binding.recyclerViewMyEvent.setVisibility(View.GONE);
            }
        });

        viewModel.getEventListFromServer();

        return binding.getRoot();
    }

    @Override
    public void onItemSelected(View view, int position, String code) {

    }

    private void initEventRecycler(ArrayList<EventModel> list){
        EventMoreAdapter adapter = new EventMoreAdapter(requireContext(), list,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewMyEvent.setLayoutManager(linearLayoutManager);
        binding.recyclerViewMyEvent.setAdapter(adapter);
    }
}