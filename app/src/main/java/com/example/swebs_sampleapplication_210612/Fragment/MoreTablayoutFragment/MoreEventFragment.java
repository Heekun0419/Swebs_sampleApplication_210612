package com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment;

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

        //ViewModel 에서 리스트로 받아오기
        viewModel.getLiveEventList().observe(getViewLifecycleOwner(), eventModels -> {
            if (eventModels.size() > 0) {
                binding.noticeTextView.setVisibility(View.GONE);
                binding.recyclerViewMoreEvent.setVisibility(View.VISIBLE);
                initEventRecycler(eventModels);
            } else {
                binding.noticeTextView.setVisibility(View.VISIBLE);
                binding.recyclerViewMoreEvent.setVisibility(View.GONE);
            }
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean != null)
                binding.loadingView.getRoot().setVisibility(aBoolean ? View.VISIBLE : View.GONE);
        });

        viewModel.getEventListFromServer();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onItemSelected(View view, int position, String code) {
        for(int i=0; i<5; i++) {
            eventMoreAdapter.addItem(new EventModel(
                    1
                    , "추가"
                    , "23"
                    , "https://images.otwojob.com/product/l/r/P/lrP1mUhYpnR780M.jpg"
                    , "함소야"
                    , "title ["+ i +"]"
                    , "date of event "
            ), eventMoreAdapter.getItemCount());
        }
    }

    private void initEventRecycler(ArrayList<EventModel> list){
        eventMoreAdapter = new EventMoreAdapter(requireContext(),list,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewMoreEvent.setLayoutManager(linearLayoutManager);
        binding.recyclerViewMoreEvent.setAdapter(eventMoreAdapter);
    }

}