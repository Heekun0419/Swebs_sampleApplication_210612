package com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity.EventActivity;
import com.example.swebs_sampleapplication_210612.Activity.TopMenuActivity.TopMenuActivity;
import com.example.swebs_sampleapplication_210612.R;
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

    EventMoreAdapter eventMoreAdapter;

    // viewPager 및 TabLayout position 받아옴.
    public MoreEventFragment(int position) {
        this.position = position;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMoreEventBinding.inflate(inflater,container,false);
        viewModel = new EventViewModel(requireActivity().getApplication());

        viewModel.getEventListFromServer();

        //ViewModel 에서 리스트로 받아오기
        viewModel.getLiveEventList().observe(getViewLifecycleOwner(), eventModels -> {
            Toast.makeText(requireContext(), "111", Toast.LENGTH_SHORT).show();
            if (eventModels.size() > 0) {
                binding.noticeTextView.setVisibility(View.GONE);
                binding.recyclerViewMoreEvent.setVisibility(View.VISIBLE);
                initEventRecycler(eventModels);
            } else {
                binding.noticeTextView.setVisibility(View.VISIBLE);
                binding.recyclerViewMoreEvent.setVisibility(View.GONE);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean != null)
                binding.loadingView.getRoot().setVisibility(aBoolean ? View.VISIBLE : View.GONE);
        });

    }

    @Override
    public void onItemSelected(View view, int position, String code) {
        Intent intent = new Intent(requireContext(), EventActivity.class);
        intent.putExtra("eventSrl", viewModel.getLiveEventList().getValue().get(position).getEventSrl());
        startActivity(intent);
    }

    private void initEventRecycler(ArrayList<EventModel> list){
        eventMoreAdapter = new EventMoreAdapter(requireContext(),list,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewMoreEvent.setLayoutManager(linearLayoutManager);
        binding.recyclerViewMoreEvent.setAdapter(eventMoreAdapter);
    }

}