package com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment.BottomReviewFragment;
import com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment.CertifiedCompanyInfoFragment;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityCertifiedCompanyBinding;

public class CertifiedCompanyActivity extends AppCompatActivity {

    private ActivityCertifiedCompanyBinding binding;
    private FragmentManager manager;
    String ImageUrl2 ="http://lafi.live/php/src/files/image_viewer.php?inputFileSrl=1571";
    String ImageUrl = "https://i.pinimg.com/originals/a2/4f/e6/a24fe6cabab71872039e30af52e7dd9e.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCertifiedCompanyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frameLayout_certified_activity, new BottomReviewFragment()).commit();

        binding.btnItemClickedBack.setOnClickListener(v -> onBackPressed());

        //제품 설명 더보기
        binding.btnProductInfoMore.setOnClickListener(v -> {
            // 더보기 버튼 사라짐.
            binding.constraintLayoutBtnLayout.setVisibility(View.GONE);
            //최대 글자수 늘리기
            binding.textViewProductInfo.setMaxLines(300);

            //첨부 이미지 최대 5개 보여지기.
            binding.imageViewDetailInfo.setVisibility(View.VISIBLE);
        });

        // 최상단 대표이미지 등록
        GlideImage(binding.imageViewCertifiedInfoProfile, ImageUrl);
        // 상세페이지 이미지 등록
        //GlideImage(binding.imageViewDetailInfo,ImageUrl2);
        Glide.with(this)
                .load(ImageUrl2)
                .fitCenter()
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .into(binding.imageViewDetailInfo);

    }

    private void GlideImage(ImageView view, String url){
        Glide.with(this).load(url).into(view);
    }
}