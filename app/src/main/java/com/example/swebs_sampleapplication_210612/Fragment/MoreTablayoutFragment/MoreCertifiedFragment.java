package com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment;

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
    private int position;

    // viewPager 및 TabLayout position 받아옴.
    public MoreCertifiedFragment(int position){
        this.position = position;
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
