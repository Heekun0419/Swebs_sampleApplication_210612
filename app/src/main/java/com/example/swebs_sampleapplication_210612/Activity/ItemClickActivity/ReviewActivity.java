package com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Data.Repository.ReviewRepository;
import com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment.BottomCommentFragment;
import com.example.swebs_sampleapplication_210612.IntroPage.IntroAdapter;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ReviewModel;
import com.example.swebs_sampleapplication_210612.ViewModel.ReViewViewModel;
import com.example.swebs_sampleapplication_210612.adapter.ReviewViewPagerAdapter;
import com.example.swebs_sampleapplication_210612.databinding.ActivityReviewBinding;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReviewActivity extends AppCompatActivity {

    private ActivityReviewBinding binding;
    private FragmentManager manager;
    private ReviewRepository reviewRepository;
    private ReViewViewModel viewModel;
    ReviewViewPagerAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        reviewRepository = new ReviewRepository(getApplication());
        viewModel = new ReViewViewModel(getApplication());


        // 해당 리뷰 리스트 불러오기
        viewModel.getReviewDetailList( getIntent().getStringExtra("memberSrl")
                , getIntent().getStringExtra("reviewSrl")
                , null
                , null);

        viewModel.getLiveDetailReviewModel().observe(this, new Observer<ReviewModel>() {
            @Override
            public void onChanged(ReviewModel model) {
               renderView(model);
            }
        });

        // 댓글 불러오기
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frameLayout_review_activity,
                new BottomCommentFragment(getIntent().getStringExtra("documentSrl"))).commit();

        binding.viewPager2Review.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        binding.viewPager2Review.setAdapter(adapter);

        DotsIndicator indicator = binding.indicatorReview;
        indicator.setViewPager2(binding.viewPager2Review);


    }

    @SuppressLint("SimpleDateFormat")
    private void renderView(ReviewModel model){
        binding.textViewReviewTitle.setText(model.getReview_title());
        binding.textViewLikeCount.setText(model.getLike_count());
        binding.textViewReviewContent.setText(model.getContent());
        binding.textViewUserName.setText(model.getNickname());

        Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(model.getRegdate(),new ParsePosition(0));
        binding.textViewRegDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));

       adapter = new ReviewViewPagerAdapter(this,model.getFile_srl());

       GlideImage(binding.imageViewUserProfile,model.getMember_srl());
    }

    private String getImageViewUrl(String fileSrl, String Width) {
        String result = getString(R.string.IMAGE_VIEW_URL) + "?inputFileSrl=" + fileSrl;
        if (Width != null)
            result += "&inputImageWidth=" + Width;
        return result;
    }

    private void GlideImage(ImageView view, String url){
        Glide.with(this).load(url).placeholder(R.drawable.ic_profile_basic).circleCrop().into(view);
    }
}