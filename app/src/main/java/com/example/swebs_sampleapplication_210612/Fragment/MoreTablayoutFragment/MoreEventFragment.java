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

    public MoreEventFragment() {
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

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
              int position = tab.getPosition();
              Log.d("position", "pos:  "+ position);
              switch (position){
                  case 0: // 전체
                      Log.d("position", "All:  "+ position);
                    viewModel.setListNum(10,"https://cdn.pixabay.com/photo/2015/07/30/17/24/audience-868074_1280.jpg");
                    break;
                  case 1: // 스웹스
                      Log.d("position", "swebs:  "+ position);
                      viewModel.setListNum(3,"https://cdn.pixabay.com/photo/2021/01/06/07/52/lipsticks-5893480_1280.jpg");
                      break;
                  case 2: // 체험단
                      Log.d("position", "체험단 :  "+ position);
                      viewModel.setListNum(5,"https://image.shutterstock.com/image-vector/sale-banner-template-design-big-600w-1005958072.jpg");
                      break;
                  case 3: // 쇼핑몰
                      Log.d("position", "쇼핑몰 :  "+ position);
                      viewModel.setListNum(4,"https://image.shutterstock.com/image-vector/set-old-vintage-ribbon-banners-600w-397984774.jpg");
                      break;
                  case 4: // 제휴업체
                      Log.d("position", "제휴업체 :  "+ position);
                      viewModel.setListNum(6,"https://image.shutterstock.com/image-vector/vintage-trendy-ribbon-text-today-600w-587782574.jpg");
                      break;
              }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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