package com.example.swebs_sampleapplication_210612.Activity.ItemClickActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment.BottomCommentFragment;
import com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment.BottomReviewFragment;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityEventBinding;

public class EventActivity extends AppCompatActivity {
    private ActivityEventBinding binding;
    private FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 댓글 불러오기
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frameLayout_event_activity, new BottomCommentFragment()).commit();

        binding.btnItemClickedBack.setOnClickListener(v -> onBackPressed());
    }
}