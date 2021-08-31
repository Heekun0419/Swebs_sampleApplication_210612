package com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment.BottomReviewFragment;
import com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment.CertifiedCompanyInfoFragment;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityCertifiedCompanyBinding;

public class CertifiedCompanyActivity extends AppCompatActivity {

    private ActivityCertifiedCompanyBinding binding;
    private FragmentManager manager;
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
            binding.gradientWhite.setVisibility(View.GONE);
            binding.constraintLayoutBtnLayout.setVisibility(View.GONE);
            binding.textViewProductInfo.setMaxLines(300);
        });

        GlideImage(binding.imageViewCertifiedInfoProfile);
    }

    private void GlideImage(ImageView view){
        Glide.with(this).load(ImageUrl).into(view);
    }
}