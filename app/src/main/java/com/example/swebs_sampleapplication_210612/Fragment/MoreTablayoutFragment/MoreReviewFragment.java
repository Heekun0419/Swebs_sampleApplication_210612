package com.example.swebs_sampleapplication_210612.Fragment.MoreTablayoutFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity.ReviewActivity;
import com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity.ReviewProductActivity;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ReviewListModel;
import com.example.swebs_sampleapplication_210612.ViewModel.ReViewViewModel;
import com.example.swebs_sampleapplication_210612.adapter.Listener.OnItemClickListener;
import com.example.swebs_sampleapplication_210612.adapter.ReviewAdapter;
import com.example.swebs_sampleapplication_210612.adapter.ReviewMoreAdapter;
import com.example.swebs_sampleapplication_210612.adapter.SurveyAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMoreReviewBinding;

import java.util.List;

public class MoreReviewFragment extends Fragment implements OnItemClickListener {

    private FragmentMoreReviewBinding binding;
    private String categorySrl;
    private ReViewViewModel viewModel;
    ReviewMoreAdapter reviewAdapter;

    // viewPager 및 TabLayout position 받아옴.
    public MoreReviewFragment(String categorySrl) {
        this.categorySrl = categorySrl;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ReViewViewModel(requireActivity().getApplication());
        viewModel.getReviewList(categorySrl,null,null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMoreReviewBinding.inflate(inflater,container,false);

        viewModel.getLiveReviewList().observe(getViewLifecycleOwner(), list ->{
            if (reviewAdapter != null) {
                // 추가
                for (int i=0; i<list.size(); i++){
                    reviewAdapter.addItem(list.get(i), reviewAdapter.getItemCount()+i);
                }
            } else {
                // 초기화
                initRecyclerView(list);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onItemSelected(View view, int position, String code) {
        ReviewListModel model = viewModel.getLiveReviewList().getValue().get(position);
        Intent intent = new Intent(requireContext(), ReviewProductActivity.class);
        intent.putExtra("prod_srl", code);
        intent.putExtra("corpName",model.getCorp_name());
        intent.putExtra("fileSrl",model.getFile_srl());
        intent.putExtra("title",model.getProd_title());
        intent.putExtra("rating",model.getAvg_rate());
        intent.putExtra("review_count",model.getReview_count());

        startActivity(intent);

    }

    private void initRecyclerView(List<ReviewListModel> list){
        reviewAdapter = new ReviewMoreAdapter(requireContext(),list,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewMoreReview.setLayoutManager(linearLayoutManager);
        binding.recyclerViewMoreReview.setAdapter(reviewAdapter);
    }

}