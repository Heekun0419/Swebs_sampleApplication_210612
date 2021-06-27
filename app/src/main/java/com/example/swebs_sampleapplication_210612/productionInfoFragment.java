package com.example.swebs_sampleapplication_210612;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.adapter.CertifiedCompanyAdapter;
import com.example.swebs_sampleapplication_210612.adapter.EventAdapter;
import com.example.swebs_sampleapplication_210612.adapter.ReviewAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMainProductBinding;

public class productionInfoFragment extends Fragment {

   private FragmentMainProductBinding binding;
   private CertifiedCompanyAdapter certifiedCompanyAdapter;
   private EventAdapter eventAdapter;
   private ReviewAdapter reviewAdapter;

    public productionInfoFragment() {
        // Required empty public constructor
    }

    public static productionInfoFragment newInstance(String param1, String param2) {
        productionInfoFragment fragment = new productionInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMainProductBinding.inflate(inflater,container,false);

        binding.includedAppbarProduct.imageButton.setOnClickListener((View.OnClickListener) v ->
                ((MainActivity)requireActivity()).drawer.openDrawer(GravityCompat.START));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        certifiedCompanyAdapter = new CertifiedCompanyAdapter(requireContext());
        binding.recyclerViewCertifiedCompany.setLayoutManager(linearLayoutManager);
        binding.recyclerViewCertifiedCompany.setAdapter(certifiedCompanyAdapter);

        eventAdapter = new EventAdapter(requireContext());
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.recyclerViewEvent.setLayoutManager(linearLayoutManager2);
        binding.recyclerViewEvent.setAdapter(eventAdapter);

        reviewAdapter = new ReviewAdapter(requireContext());
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewReview.setLayoutManager(linearLayoutManager3);
        binding.recyclerViewReview.setAdapter(reviewAdapter);


        return binding.getRoot();
    }
}