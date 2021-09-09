package com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity.CertifiedCompanyActivity;
import com.example.swebs_sampleapplication_210612.ViewModel.CertifiedCompanyViewModel;
import com.example.swebs_sampleapplication_210612.adapter.Listener.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.adapter.TablayoutAdapter.GridMoreCretifiedAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMoreCertifiedCompanyBinding;

public class MoreCertifiedFragment extends Fragment implements OnItemClickListener {

    private FragmentMoreCertifiedCompanyBinding binding;
    private final String categorySrl;
    private CertifiedCompanyViewModel viewModel;
    private String LastIndex ="0";
    GridMoreCretifiedAdapter adapter;
    // viewPager 및 TabLayout position 받아옴.
    public MoreCertifiedFragment(String categorySrl){
        this.categorySrl = categorySrl;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new CertifiedCompanyViewModel(requireActivity().getApplication());
        viewModel.getProductListFromServer(categorySrl, LastIndex);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMoreCertifiedCompanyBinding.inflate(inflater,container,false);
        binding.noticeTextView.setVisibility(View.VISIBLE);

        viewModel.getLiveCompanyModelList().observe(getViewLifecycleOwner(), certifiedCompanyModels -> {
            if (LastIndex.equals("0")) {
                GridMoreCretifiedAdapter adapter = new GridMoreCretifiedAdapter(requireContext(),certifiedCompanyModels, this);
                binding.gridViewMoreCertified.setAdapter(adapter);
            } else {
            LastIndex = certifiedCompanyModels.get(certifiedCompanyModels.size()-1).getProd_srl();
            adapter.changeItem(certifiedCompanyModels);}

            if (certifiedCompanyModels.size() > 0) {
                binding.noticeTextView.setVisibility(View.GONE);
            }
        });

       // Log.d("scroll", String.valueOf(binding.gridViewMoreCertified.getScrollY()));

        return binding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean != null)
                binding.loadingView.getRoot().setVisibility(aBoolean ? View.VISIBLE : View.GONE);
        });
    }

    @Override
    public void onItemSelected(View view, int position, String code) {
        Intent intent = new Intent(requireActivity().getApplicationContext(), CertifiedCompanyActivity.class);
        intent.putExtra("productSrl", code);
        startActivity(intent);
    }
}
