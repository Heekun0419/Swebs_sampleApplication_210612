package com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity.CertifiedCompanyActivity;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.ProductListlModel;
import com.example.swebs_sampleapplication_210612.ViewModel.CertifiedCompanyViewModel;
import com.example.swebs_sampleapplication_210612.adapter.Listener.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.adapter.TablayoutAdapter.GridMoreCretifiedAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMoreCertifiedCompanyBinding;

import java.util.List;

public class MoreCertifiedFragment extends Fragment implements OnItemClickListener {

    private FragmentMoreCertifiedCompanyBinding binding;
    private CertifiedCompanyViewModel viewModel;
    GridMoreCretifiedAdapter adapter;

    private String categorySrl;

    // viewPager 및 TabLayout position 받아옴.
    public MoreCertifiedFragment(){

    }

    public static MoreCertifiedFragment newInstance(String categorySrl) {

        Bundle args = new Bundle();
        args.putString("category_srl", categorySrl);
        MoreCertifiedFragment fragment = new MoreCertifiedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            categorySrl = getArguments().getString("category_srl");
        }
        viewModel = new CertifiedCompanyViewModel(requireActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMoreCertifiedCompanyBinding.inflate(inflater,container,false);

        // 서버에서 리스트 받아오기.
        viewModel.getProductListFromServer(categorySrl, null, null);

        viewModel.getLiveCompanyModelList().observe(getViewLifecycleOwner(), models -> {
            if (adapter != null && models != null)
                for (int i=0; i < models.size(); i++)
                    adapter.addItem(models.get(i), adapter.getCount()+i);
            else
                initGridView(models);
        });

        // 상품이 없을때
        viewModel.getExistProductList().observe(getViewLifecycleOwner(), aBoolean -> {
            if (adapter == null && !aBoolean) {
                binding.noticeTextView.setVisibility(View.VISIBLE);
            }
        });

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

    private void initGridView(List<ProductListlModel> list) {
        adapter = new GridMoreCretifiedAdapter(requireContext(), list, this);
        binding.gridViewMoreCertified.setAdapter(adapter);
    }
}
