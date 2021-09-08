package com.example.swebs_sampleapplication_210612.Fragment.MainFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity.CertifiedCompanyActivity;
import com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity.EventActivity;
import com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity.SurveyActivity;
import com.example.swebs_sampleapplication_210612.Activity.MainActivity;
import com.example.swebs_sampleapplication_210612.Activity.TopMenuActivity.TopMenuActivity;
import com.example.swebs_sampleapplication_210612.adapter.CertifiedCompanyAdapter;
import com.example.swebs_sampleapplication_210612.adapter.EventAdapter;
import com.example.swebs_sampleapplication_210612.adapter.Listener.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.adapter.ReviewAdapter;
import com.example.swebs_sampleapplication_210612.adapter.SurveyAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMainProductBinding;

public class productionInfoFragment extends Fragment implements OnItemClickListener {

    private FragmentMainProductBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMainProductBinding.inflate(inflater,container,false);

        // 네비게이션 드로어 열기
        binding.includedAppbarProduct.imageButton.setOnClickListener(v ->
                ((MainActivity)requireActivity()).openDrawer());


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        CertifiedCompanyAdapter certifiedCompanyAdapter = new CertifiedCompanyAdapter(requireContext(),this);
        binding.recyclerViewCertifiedCompany.setLayoutManager(linearLayoutManager);
        binding.recyclerViewCertifiedCompany.setAdapter(certifiedCompanyAdapter);

        EventAdapter eventAdapter = new EventAdapter(requireContext(),this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.recyclerViewEvent.setLayoutManager(linearLayoutManager2);
        binding.recyclerViewEvent.setAdapter(eventAdapter);

        ReviewAdapter reviewAdapter = new ReviewAdapter(requireContext());
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewReview.setLayoutManager(linearLayoutManager3);
        binding.recyclerViewReview.setAdapter(reviewAdapter);

        SurveyAdapter surveyAdapter = new SurveyAdapter(requireContext(),this);
        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.recyclerViewSurvey.setLayoutManager(linearLayoutManager4);
        binding.recyclerViewSurvey.setAdapter(surveyAdapter);

        binding.btnEvent.setOnClickListener(v -> {
          moveActivity("event");
        });

        binding.btnCertifiedCompany.setOnClickListener(v -> {
           moveActivity("certified");
        });

        binding.btnReview.setOnClickListener(v -> {
          moveActivity("review");
        });

        binding.btnSurvey.setOnClickListener(v -> {
          moveActivity("survey");
        });

        binding.includedAppbarProduct.imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)requireActivity()).BottomSheetOpen();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onItemSelected(View view, int position, String code) {
        switch (code) {
            case "event": {
                Intent intent = new Intent(requireContext(), EventActivity.class);
                startActivity(intent);
                break;
            }
            case "survey": {
                Intent intent = new Intent(requireContext(), SurveyActivity.class);
                startActivity(intent);
                break;
            }
            case "certified": {
                Intent intent = new Intent(requireContext(), CertifiedCompanyActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    private void moveActivity(String code){
        Intent intent = new Intent(requireContext(), TopMenuActivity.class);
        intent.putExtra("resultCode",code);
        startActivity(intent);
    }
}