package com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.ProductDetailModel;
import com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment.BottomReviewFragment;
import com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment.CertifiedCompanyInfoFragment;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.CertifiedCompanyViewModel;
import com.example.swebs_sampleapplication_210612.databinding.ActivityCertifiedCompanyBinding;

public class CertifiedCompanyActivity extends AppCompatActivity {

    private ActivityCertifiedCompanyBinding binding;
    private CertifiedCompanyViewModel viewModel;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCertifiedCompanyBinding.inflate(getLayoutInflater());
        viewModel = new CertifiedCompanyViewModel(getApplication());
        setContentView(binding.getRoot());

        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frameLayout_certified_activity, new BottomReviewFragment()).commit();

        binding.btnItemClickedBack.setOnClickListener(v -> onBackPressed());

        viewModel.getProductDetailFromServer(getIntent().getStringExtra("productSrl"));

        //제품 설명 더보기
        binding.btnProductInfoMore.setOnClickListener(v -> {
            // 더보기 버튼 사라짐.
            binding.constraintLayoutBtnLayout.setVisibility(View.GONE);
            //최대 글자수 늘리기
            binding.textViewProductContent.setMaxLines(300);

            //첨부 이미지 최대 5개 보여지기.
            if (binding.imageViewDetailInfo1.getResources() != null)
                binding.imageViewDetailInfo1.setVisibility(View.VISIBLE);
            if (binding.imageViewDetailInfo2.getResources() != null)
                binding.imageViewDetailInfo2.setVisibility(View.VISIBLE);
            if (binding.imageViewDetailInfo3.getResources() != null)
                binding.imageViewDetailInfo3.setVisibility(View.VISIBLE);
            if (binding.imageViewDetailInfo4.getResources() != null)
                binding.imageViewDetailInfo4.setVisibility(View.VISIBLE);
            if (binding.imageViewDetailInfo5.getResources() != null)
                binding.imageViewDetailInfo5.setVisibility(View.VISIBLE);
        });

        viewModel.getLiveProductDetail().observe(this, models -> {
            if (models != null) {
                // 대표 이미지
                GlideImage(binding.imageViewCertifiedInfoProfile, getImageViewUrl(models.getFile_srl(), null));

                // 업체 명
                binding.corpName.setText(models.getCorp_name());

                // 제품 명
                binding.textViewProductName.setText(models.getProd_title());

                // 레이팅
                binding.ratingBarReview.setRating(Float.parseFloat(models.getRating()));

                // 레이팅 표시
                binding.ratingText.setText(models.getRating());

                // 리뷰 갯수
                binding.reviewCount.setText("("+models.getReview_count()+")");

                // 카테고리
                binding.categoryTitle.setText(models.getCategory_title());

                // 랭킹
                binding.categoryRank.setText("("+models.getRank()+"위)");

                // 본문
                binding.textViewProductContent.setText(htmlToString(models.getContent()));

                // 본문 이미지
                for (int i=0; i<models.getContent_file_srl().size(); i++) {
                    loadImageViewDetail(i+1, models.getContent_file_srl().get(i));
                }
            }
        });
    }

    private void loadImageViewDetail(int index, String fileSrl) {
        if (index == 1)
            GlideImage(binding.imageViewDetailInfo1, getImageViewUrl(fileSrl, null));
        if (index == 2)
            GlideImage(binding.imageViewDetailInfo2, getImageViewUrl(fileSrl, null));
        if (index == 3)
            GlideImage(binding.imageViewDetailInfo3, getImageViewUrl(fileSrl, null));
        if (index == 4)
            GlideImage(binding.imageViewDetailInfo4, getImageViewUrl(fileSrl, null));
        if (index == 5)
            GlideImage(binding.imageViewDetailInfo5, getImageViewUrl(fileSrl, null));
    }

    private String getImageViewUrl(String fileSrl, String Width) {
        String result = getString(R.string.IMAGE_VIEW_URL) + "?inputFileSrl=" + fileSrl;
        if (Width != null) result += "&inputImageWidth=" + Width;
        return result;
    }

    private String htmlToString(String html) {
        return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
    }

    private void GlideImage(ImageView view, String url){
        Glide.with(this).load(url).into(view);
    }
}