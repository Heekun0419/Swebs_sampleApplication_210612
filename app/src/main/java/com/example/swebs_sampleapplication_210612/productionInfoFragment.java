package com.example.swebs_sampleapplication_210612;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.Activity.MainActivity;
import com.example.swebs_sampleapplication_210612.Activity.PurchaseInfoActivity;
import com.example.swebs_sampleapplication_210612.Activity.TopMenuActivity;
import com.example.swebs_sampleapplication_210612.adapter.CertifiedCompanyAdapter;
import com.example.swebs_sampleapplication_210612.adapter.EventAdapter;
import com.example.swebs_sampleapplication_210612.adapter.ReviewAdapter;
import com.example.swebs_sampleapplication_210612.adapter.SurveyAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMainProductBinding;

public class productionInfoFragment extends Fragment {

   private FragmentMainProductBinding binding;


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
        CertifiedCompanyAdapter certifiedCompanyAdapter = new CertifiedCompanyAdapter(requireContext());
        binding.recyclerViewCertifiedCompany.setLayoutManager(linearLayoutManager);
        binding.recyclerViewCertifiedCompany.setAdapter(certifiedCompanyAdapter);

        EventAdapter eventAdapter = new EventAdapter(requireContext());
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.recyclerViewEvent.setLayoutManager(linearLayoutManager2);
        binding.recyclerViewEvent.setAdapter(eventAdapter);

        ReviewAdapter reviewAdapter = new ReviewAdapter(requireContext());
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewReview.setLayoutManager(linearLayoutManager3);
        binding.recyclerViewReview.setAdapter(reviewAdapter);

        SurveyAdapter surveyAdapter = new SurveyAdapter(requireContext());
        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.recyclerViewSurvey.setLayoutManager(linearLayoutManager4);
        binding.recyclerViewSurvey.setAdapter(surveyAdapter);

        binding.btnEvent.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), TopMenuActivity.class);
            intent.putExtra("resultCode", "event");
            startActivity(intent);
        });

        binding.btnCertifiedCompany.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), TopMenuActivity.class);
            intent.putExtra("resultCode", "certified");
            startActivity(intent);
        });

        binding.btnReview.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), TopMenuActivity.class);
            intent.putExtra("resultCode", "review");
            startActivity(intent);
        });

        binding.btnSurvey.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), PurchaseInfoActivity.class);
            startActivity(intent);
        });

        binding.includedAppbarProduct.imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)requireActivity()).BottomSheetOpen();
            }
        });
        return binding.getRoot();
    }
}