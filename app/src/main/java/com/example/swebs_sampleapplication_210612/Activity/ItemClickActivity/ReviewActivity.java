package com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
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
                 BottomCommentFragment.newInstance(getIntent().getStringExtra("documentSrl"))).commit();

    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    private void renderView(ReviewModel model){

        binding.textViewReviewTitle.setText(model.getReview_title());
        binding.textViewLikeCount.setText("" + model.getLike_count());
        binding.textViewReviewContent.setText(model.getContent());
        binding.textViewUserName.setText(model.getNickname());
        binding.textViewProductName.setText(model.getProd_title());
        binding.textViewReviewBrandName.setText(model.getCorp_name());
        binding.ratingBar.setRating(Float.parseFloat(model.getAvg_rate()));
        binding.textViewRatingNum.setText(Float.toString(binding.ratingBar.getRating()));
        binding.textViewReviewCount.setText("리뷰 " + model.getMember_review_count()+" 개");
        binding.textViewReviewNum.setText(model.getReview_count());

        Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(model.getRegdate(),new ParsePosition(0));
        binding.textViewRegDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));

        // viewPager 설정 + 마진 설정

        if(model.getFile_srl().get(0).equals("")){
            binding.viewPager2Review.setVisibility(View.GONE);
        } else {
            adapter = new ReviewViewPagerAdapter(this,model.getFile_srl());
            binding.viewPager2Review.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
            binding.viewPager2Review.setAdapter(adapter);
            DotsIndicator indicator = binding.indicatorReview;
            indicator.setViewPager2(binding.viewPager2Review);

            binding.viewPager2Review.setClipToPadding(false);
            binding.viewPager2Review.setClipChildren(false);
            binding.viewPager2Review.setOffscreenPageLimit(3);
            binding.viewPager2Review.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

            CompositePageTransformer transformer = new CompositePageTransformer();
            transformer.addTransformer(new MarginPageTransformer(30));
            transformer.addTransformer((page, position) -> {
                float r = 1- Math.abs(position);
                page.setScaleY(0.95f + r * 0.1f);
            });
            binding.viewPager2Review.setPageTransformer(transformer);
        }


        GlideImage_circle(binding.imageViewUserProfile,getImageViewUrl(model.getProfile_srl(),"200"));
        GlideImage(binding.imageViewReviewProfile,getImageViewUrl(model.getProd_file_srl(),"200"));
    }

    private String getImageViewUrl(String fileSrl, String Width) {
        String result = getString(R.string.IMAGE_VIEW_URL) + "?inputFileSrl=" + fileSrl;
        if (Width != null)
            result += "&inputImageWidth=" + Width;
        return result;
    }

    private void GlideImage_circle(ImageView view, String url){
        Glide.with(this).load(url).placeholder(R.drawable.ic_profile_basic).circleCrop().into(view);
    }
    private void GlideImage(ImageView view, String url){
        Glide.with(this).load(url).centerCrop().into(view);
    }
}