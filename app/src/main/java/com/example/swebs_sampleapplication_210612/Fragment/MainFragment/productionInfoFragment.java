package com.example.swebs_sampleapplication_210612.Fragment.MainFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.example.swebs_sampleapplication_210612.Activity.ItemClickViewActivty;
import com.example.swebs_sampleapplication_210612.Activity.MainActivity;
import com.example.swebs_sampleapplication_210612.Activity.PurchaseInfoActivity;
import com.example.swebs_sampleapplication_210612.Activity.TopMenuActivity;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.adapter.CertifiedCompanyAdapter;
import com.example.swebs_sampleapplication_210612.adapter.EventAdapter;
import com.example.swebs_sampleapplication_210612.adapter.OnItemClickListener;
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
        /*
        binding.recyclerViewCertifiedCompany.setOnTouchListener((v, event) -> {
            ((MainActivity)requireActivity()).setViewPagerTouchStatus(event.getAction() == MotionEvent.ACTION_UP);
            return false;
        });

        binding.recyclerViewCertifiedCompany.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean ret = false;
                if(v.getId() == R.id.recyclerView_certified_company){
                    // 터치가 되고 있는 위치 // move나 up등을 계산하려면 이 값을 계속 유지하여 계산해야 됨
                    float x = event.getX();
                    float y = event.getY();
                    switch (event.getActionMasked()){
                        case MotionEvent.ACTION_DOWN:
                            Log.d( "testtest", "onTouch Down ACTION_Down: (" + x +", " + y + ")" );
                            ((MainActivity)requireActivity()).setViewPagerTouchStatus(false);
                            ret = false;
                            break;
                        case MotionEvent.ACTION_UP:
                            ((MainActivity)requireActivity()).setViewPagerTouchStatus(true);
                            ret = true;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            Log.d( "testtest", "onTouch Down ACTION_MOVE: (" + x +", " + y + ")" );
                            ret = false;
                            ((MainActivity)requireActivity()).setViewPagerTouchStatus(false);
                            break;
                    }
                }
                return ret;
            }
        });*/

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
       if(code.equals("event")){
           Intent intent = new Intent(requireContext(), ItemClickViewActivty.class);
           intent.putExtra("resultCode", "event");
           startActivity(intent);
       }else if(code.equals("survey")){
           Intent intent = new Intent(requireContext(), ItemClickViewActivty.class);
           intent.putExtra("resultCode", "survey");
           startActivity(intent);
       }else if(code.equals("certified")){
           Intent intent = new Intent(requireContext(), ItemClickViewActivty.class);
           intent.putExtra("resultCode", "certified");
           startActivity(intent);
       }
    }

    private void moveActivity(String code){
        Intent intent = new Intent(requireContext(), TopMenuActivity.class);
        intent.putExtra("resultCode",code);
        startActivity(intent);
    }
}