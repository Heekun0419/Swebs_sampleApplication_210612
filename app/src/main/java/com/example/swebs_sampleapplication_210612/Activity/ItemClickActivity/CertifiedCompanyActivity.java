package com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.swebs_sampleapplication_210612.Activity.ServiceNotReadyActivity;
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
    private String marketLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCertifiedCompanyBinding.inflate(getLayoutInflater());
        viewModel = new CertifiedCompanyViewModel(getApplication());
        setContentView(binding.getRoot());

        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frameLayout_certified_activity,
                BottomReviewFragment.newInstance(getIntent().getStringExtra("productSrl"))).commit();

        binding.btnItemClickedBack.setOnClickListener(v -> onBackPressed());

        viewModel.getProductDetailFromServer(getIntent().getStringExtra("productSrl"));

        //?????? ?????? ?????????
        binding.btnProductInfoMore.setOnClickListener(v -> {
            // ????????? ?????? ?????????.
            binding.constraintLayoutBtnLayout.setVisibility(View.GONE);
            //?????? ????????? ?????????
            binding.textViewProductContent.setMaxLines(300);

            //?????? ????????? ?????? 5??? ????????????.
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

        // AS ?????? ??????
        binding.btnAS.setOnClickListener(v -> {
            Intent intent = new Intent(this, ServiceNotReadyActivity.class);
            startActivity(intent);
        });

        // MARKET LINK ??????
        binding.btnMarket.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(marketLink));
            startActivity(browserIntent);
        });

        viewModel.getLiveProductDetail().observe(this, models -> {
            if (models != null) {
                // ?????? ?????????
                GlideImage(binding.imageViewCertifiedInfoProfile, getImageViewUrl(models.getFile_srl(), null));

                // ?????? ???
                binding.corpName.setText(models.getCorp_name());

                // ?????? ???
                binding.textViewProductName.setText(models.getProd_title());

                // ?????????
                binding.ratingBarReview.setRating(Float.parseFloat(models.getRating()));

                // ????????? ??????
                binding.ratingText.setText(Float.toString(binding.ratingBarReview.getRating()));

                // ?????? ??????
                binding.reviewCount.setText("("+models.getReview_count()+")");

                // ????????????
                binding.categoryTitle.setText(models.getCategory_title());

                // ??????
                binding.categoryRank.setText("("+models.getRank()+"???)");

                // ??????
                binding.textViewProductContent.setText(htmlToString(models.getContent()));

                // ?????? ?????????
                for (int i=0; i<models.getContent_file_srl().size(); i++) {
                    loadImageViewDetail(i+1, models.getContent_file_srl().get(i));
                }

                // ?????? ??????...
                marketLink = models.getMarket_link();
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onResume() {
        super.onResume();

        viewModel.getIsLoading().observe(this, aBoolean -> {
            if (aBoolean != null) {
                if (aBoolean)
                    binding.loadingView.getRoot().setOnTouchListener((v, event) -> true);
                binding.loadingView.getRoot().setVisibility(aBoolean ? View.VISIBLE : View.GONE);
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