package com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.Activity.ServiceNotReadyActivity;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ReviewModel;
import com.example.swebs_sampleapplication_210612.adapter.ReviewProductAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentBottomReviewBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BottomReviewFragment extends Fragment {

    private FragmentBottomReviewBinding binding;
    private ArrayList<ReviewModel> reviewList = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBottomReviewBinding.inflate(inflater,container,false);

        binding.btnAS.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ServiceNotReadyActivity.class);
            startActivity(intent);
        });

        addArrayList();
        initRecyclerView(reviewList);

        return binding.getRoot();
    }

    private void initRecyclerView(ArrayList<ReviewModel> reviewModelArrayList){
        ReviewProductAdapter adapter = new ReviewProductAdapter(requireContext(), reviewModelArrayList);
        LinearLayoutManager manager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false);
        binding.recyclerViewProductReview.setLayoutManager(manager);
        binding.recyclerViewProductReview.setAdapter(adapter);
    }

    private void addArrayList(){
       String date = new SimpleDateFormat("yyyy.MM.dd").format(new Date());

        reviewList.add(new ReviewModel("박규빈",
                date,"바느질도 좋고 마감처리도 좋네요.",
                "4.5",
                "인생에 선배로써 편안함과 안정감을 주시네요 너무 친절하고 감사합니다.",
                "20"));

        reviewList.add(new ReviewModel("홍길동",
                date,"매우 만족합니다.",
                "4.2",
                "인생에 선배로써 편안함과 안정감을 주시네요 너무 친절하고 감사합니다.",
                "20"));

    }
}