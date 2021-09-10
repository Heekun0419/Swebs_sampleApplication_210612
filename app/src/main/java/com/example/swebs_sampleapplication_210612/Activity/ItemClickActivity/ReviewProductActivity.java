package com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment.BottomReviewFragment;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityReviewProductBinding;

public class ReviewProductActivity extends AppCompatActivity {

    private ActivityReviewProductBinding binding;
    private FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // 상단 제품 정보 설정
        renderView();

        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frameLayout_review_product_activity,new BottomReviewFragment(getIntent().getStringExtra("prod_srl"))).commit();
    }

    @SuppressLint("SetTextI18n")
    private void renderView(){
        binding.textViewProductName.setText( getIntent().getStringExtra("title"));
        binding.ratingBar.setRating(Float.parseFloat(getIntent().getStringExtra("rating")));
        binding.textViewRatingNum.setText(Float.toString(binding.ratingBar.getRating()));
        binding.textViewReviewNum.setText("("+getIntent().getStringExtra("review_count")+")");
        binding.textViewReviewBrandName.setText(getIntent().getStringExtra("corpName"));
        GlideImage(binding.imageViewReviewProfile, getImageViewUrl(getIntent().getStringExtra("fileSrl"), "300"));
    }
    private String getImageViewUrl(String fileSrl, String Width) {
        String result = getString(R.string.IMAGE_VIEW_URL) + "?inputFileSrl=" + fileSrl;
        if (Width != null)
            result += "&inputImageWidth=" + Width;
        return result;
    }

    private void GlideImage(ImageView view, String url){
        Glide.with(this).load(url).centerCrop().into(view);
    }

}