package com.example.swebs_sampleapplication_210612.Fragment.MyTopMenuFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.adapter.Model.EventModel;
import com.example.swebs_sampleapplication_210612.adapter.MyReviewAdapter;
import com.example.swebs_sampleapplication_210612.adapter.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.adapter.TablayoutAdapter.EventMoreAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMyEventBinding;

public class MyEventFragment extends Fragment implements OnItemClickListener {

    private FragmentMyEventBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMyEventBinding.inflate(inflater,container,false);
        EventMoreAdapter adapter = new EventMoreAdapter(requireContext(),new EventModel(
                "https://images.otwojob.com/product/l/r/P/lrP1mUhYpnR780M.jpg",
                "함소아",
                "오늘은 어디로 떠나볼까요?",
                "2021.08.11 ~ 2021.09.21"),this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewMyEvent.setLayoutManager(linearLayoutManager);
        binding.recyclerViewMyEvent.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onItemSelected(View view, int position, String code) {

    }
}