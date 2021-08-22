package com.example.swebs_sampleapplication_210612.Fragment.Information_menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.swebs_sampleapplication_210612.ViewModel.ProductRegisterViewModel;

import com.example.swebs_sampleapplication_210612.adapter.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.adapter.ProductTagAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentProductRegisterBinding;

import java.util.ArrayList;

public class ProductRegisterFragment extends Fragment implements OnItemClickListener {

    private FragmentProductRegisterBinding binding;
    private ArrayList<String> tagList = new ArrayList<>();
    private ProductRegisterViewModel viewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ProductRegisterViewModel(requireActivity().getApplication());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentProductRegisterBinding.inflate(inflater,container,false);

        viewModel.getTagList().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> list) {
                initRecyclerView(list);
            }
        });

        binding.addProductTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag =  binding.editTextTag.getText().toString();
                if(!tag.isEmpty()){
                    tagList.add(tag);
                    viewModel.setTagList(tagList);
                }
            }
        });


        return binding.getRoot();
    }


    private void initRecyclerView(ArrayList<String> list){
        ProductTagAdapter adapter = new ProductTagAdapter(requireContext(),list,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.recyclerViewTag.setLayoutManager(linearLayoutManager);
        binding.recyclerViewTag.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(View view, int position, String code) {
        tagList.remove(position);
        viewModel.setTagList(tagList);
    }
}