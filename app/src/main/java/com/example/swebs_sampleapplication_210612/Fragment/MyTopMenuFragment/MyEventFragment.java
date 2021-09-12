package com.example.swebs_sampleapplication_210612.Fragment.MyTopMenuFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity.EventActivity;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.ViewModel.EventViewModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.EventModel;
import com.example.swebs_sampleapplication_210612.adapter.Listener.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.adapter.TablayoutAdapter.EventMoreAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMyEventBinding;

import java.util.ArrayList;

public class MyEventFragment extends Fragment implements OnItemClickListener {

    private FragmentMyEventBinding binding;
    private EventViewModel viewModel;
    private SPmanager sPmanager;

    private String categoryType;

    public static MyEventFragment newInstance(String tabPosition) {
        Bundle args = new Bundle();
        args.putString("tabPosition", tabPosition);
        MyEventFragment fragment = new MyEventFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            if (getArguments().getString("tabPosition").equals("0"))
                categoryType = "myParticipation";
            else if (getArguments().getString("tabPosition").equals("2"))
                categoryType = "myLike";
            else
                categoryType = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMyEventBinding.inflate(inflater,container,false);

        viewModel = new EventViewModel(requireActivity().getApplication());
        sPmanager = new SPmanager(requireContext());

        if (categoryType == null) {
            binding.noticeTextView.setVisibility(View.VISIBLE);
            binding.recyclerViewMyEvent.setVisibility(View.GONE);
        } else
            viewModel.getEventListFromServer(categoryType, sPmanager.getUserSrl());

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

        return binding.getRoot();
    }

    @Override
    public void onItemSelected(View view, int position, String code) {
        Intent intent = new Intent(requireActivity().getApplicationContext(), EventActivity.class);
        intent.putExtra("eventSrl", viewModel.getLiveEventList().getValue().get(position).getEventSrl());
        startActivity(intent);
    }

    private void initEventRecycler(ArrayList<EventModel> list){
        EventMoreAdapter adapter = new EventMoreAdapter(requireContext(), list,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewMyEvent.setLayoutManager(linearLayoutManager);
        binding.recyclerViewMyEvent.setAdapter(adapter);
    }
}