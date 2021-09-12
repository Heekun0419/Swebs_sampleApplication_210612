package com.example.swebs_sampleapplication_210612.Fragment.MainFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity.CertifiedCompanyActivity;
import com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity.EventActivity;
import com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity.ReviewProductActivity;
import com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity.SurveyActivity;
import com.example.swebs_sampleapplication_210612.Activity.MainActivity;
import com.example.swebs_sampleapplication_210612.Activity.TopMenuActivity.TopMenuActivity;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventListDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.MainReviewModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.ProductListModel;
import com.example.swebs_sampleapplication_210612.ViewModel.MainProductViewModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.EventModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.SurveyDetailModel;
import com.example.swebs_sampleapplication_210612.adapter.CertifiedCompanyAdapter;
import com.example.swebs_sampleapplication_210612.adapter.EventAdapter;
import com.example.swebs_sampleapplication_210612.adapter.Listener.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.adapter.ReviewAdapter;
import com.example.swebs_sampleapplication_210612.adapter.SurveyAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMainProductBinding;

import java.util.List;

public class productionInfoFragment extends Fragment implements OnItemClickListener {

    private FragmentMainProductBinding binding;
    private MainProductViewModel viewModel;

    // 리사클러뷰 어뎁터
    private CertifiedCompanyAdapter productAdapter;
    private EventAdapter eventAdapter;
    private ReviewAdapter reviewAdapter;
    private SurveyAdapter surveyAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new MainProductViewModel(requireActivity().getApplication());
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMainProductBinding.inflate(inflater,container,false);

        // 네비게이션 드로어 열기
        binding.includedAppbarProduct.imageButton.setOnClickListener(v -> ((MainActivity)requireActivity()).openDrawer());

        // 서버에서 데이터 받아오기...
        viewModel.getListFromServer();

        // 인증 업체 관련 데이터
        viewModel.getProductList().observe(getViewLifecycleOwner(), models -> {
            if (productAdapter != null && models != null) {
                // 데이터 하나씩 넣기.
            } else
                initProductRecycler(models);
        });

        // 이벤트 관련 데이터
        viewModel.getEventList().observe(getViewLifecycleOwner(), models -> {
            if (eventAdapter != null && models != null) {
                // 데이터 하나씩 넣기.
            } else
                initEventRecycler(models);
        });

        // 리뷰 관련 데이터
        viewModel.getReviewList().observe(getViewLifecycleOwner(), models -> {
            if (reviewAdapter != null && models != null){
                // 데이터 하나씩 넣기
            } else {
                initReviewRecycler(models);
            }
        });
        viewModel.getSurveyList().observe(getViewLifecycleOwner(), models -> {
            if(surveyAdapter != null && models != null){
                // 데이터 하나씩 넣기
            } else {
                initSurveyRecycler(models);
            }
        });


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

        binding.includedAppbarProduct.imageButton2.setOnClickListener(v -> {
            ((MainActivity)requireActivity()).BottomSheetOpen();
        });
        return binding.getRoot();
    }

    @Override
    public void onItemSelected(View view, int position, String code) {
        switch (code) {
            case "event": {
                Intent intent = new Intent(requireActivity().getApplicationContext(), EventActivity.class);
                intent.putExtra("eventSrl",eventAdapter.getItem(position).getEvent_srl());
                startActivity(intent);
                break;
            }
            case "survey": {
                Intent intent = new Intent(requireContext(), SurveyActivity.class);
                startActivity(intent);
                break;
            }
            case "certified": {
                Intent intent = new Intent(requireActivity().getApplicationContext(), CertifiedCompanyActivity.class);
                intent.putExtra("productSrl", productAdapter.getItem(position).getProd_srl());

                startActivity(intent);
                break;
            }
            case "review":
                Intent intent = new Intent(requireActivity().getApplicationContext(), ReviewProductActivity.class);
                intent.putExtra("prod_srl",reviewAdapter.getItem(position).getProd_srl());
                intent.putExtra("title",reviewAdapter.getItem(position).getProd_title());
                intent.putExtra("rating",reviewAdapter.getItem(position).getRate());
                intent.putExtra("corpName",reviewAdapter.getItem(position).getCorp_name());
                intent.putExtra("fileSrl",reviewAdapter.getItem(position).getFile_srl());
                startActivity(intent);
                break;
        }
    }

    private void moveActivity(String code){
        Intent intent = new Intent(requireContext(), TopMenuActivity.class);
        intent.putExtra("resultCode",code);
        startActivity(intent);
    }

    private void initProductRecycler(List<ProductListModel> models) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        productAdapter = new CertifiedCompanyAdapter(requireContext(),models,this);
        binding.recyclerViewCertifiedCompany.setLayoutManager(linearLayoutManager);
        binding.recyclerViewCertifiedCompany.setAdapter(productAdapter);
    }

    private void initEventRecycler(List<EventListDetailModel> list){
        EventAdapter eventAdapter = new EventAdapter(requireContext(),list,this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.recyclerViewEvent.setLayoutManager(linearLayoutManager2);
        binding.recyclerViewEvent.setAdapter(eventAdapter);
    }

    private void initReviewRecycler(List<MainReviewModel> list){
        reviewAdapter = new ReviewAdapter(requireContext(), list, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewReview.setLayoutManager(linearLayoutManager);
        binding.recyclerViewReview.setAdapter(reviewAdapter);
    }

    private void initSurveyRecycler(List<SurveyDetailModel> list){
        surveyAdapter = new SurveyAdapter(requireContext(),list,this);
        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.recyclerViewSurvey.setLayoutManager(linearLayoutManager4);
        binding.recyclerViewSurvey.setAdapter(surveyAdapter);

    }
}