package com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.CertifiedCompanyDetailModel;
import com.example.swebs_sampleapplication_210612.ViewModel.CertifiedCompanyViewModel;
import com.example.swebs_sampleapplication_210612.adapter.TablayoutAdapter.GridMoreCretifiedAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMoreCertifiedCompanyBinding;

import java.util.ArrayList;

public class MoreCertifiedFragment extends Fragment {

    private FragmentMoreCertifiedCompanyBinding binding;
    private final int categorySrl;
    private CertifiedCompanyViewModel viewModel;
    private String LastIndex ="0";
    GridMoreCretifiedAdapter adapter;
    // viewPager 및 TabLayout position 받아옴.
    public MoreCertifiedFragment(int categorySrl){
        this.categorySrl = categorySrl;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new CertifiedCompanyViewModel(requireActivity().getApplication());
        String categorySrl = Integer.toString(this.categorySrl);
        viewModel.getListFromServer(categorySrl,LastIndex);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMoreCertifiedCompanyBinding.inflate(inflater,container,false);

        viewModel.getLiveCompanyModelList().observe(getViewLifecycleOwner(), new Observer<ArrayList<CertifiedCompanyDetailModel>>() {
            @Override
            public void onChanged(ArrayList<CertifiedCompanyDetailModel> certifiedCompanyModels) {
                GridMoreCretifiedAdapter adapter = new GridMoreCretifiedAdapter(requireContext(),certifiedCompanyModels);
                binding.gridViewMoreCertified.setAdapter(adapter);
            }
        });

       // Log.d("scroll", String.valueOf(binding.gridViewMoreCertified.getScrollY()));

        return binding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
