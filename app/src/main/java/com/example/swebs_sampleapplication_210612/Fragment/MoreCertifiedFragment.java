package com.example.swebs_sampleapplication_210612.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.swebs_sampleapplication_210612.adapter.TablayoutAdapter.GridMoreCretifiedAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMoreCertifiedCompanyBinding;

public class MoreCertifiedFragment extends Fragment {

    private FragmentMoreCertifiedCompanyBinding binding;

    public MoreCertifiedFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMoreCertifiedCompanyBinding.inflate(inflater,container,false);

        GridMoreCretifiedAdapter adapter = new GridMoreCretifiedAdapter(requireContext());
        binding.gridViewMoreCertified.setAdapter(adapter);

        return binding.getRoot();

    }
}
