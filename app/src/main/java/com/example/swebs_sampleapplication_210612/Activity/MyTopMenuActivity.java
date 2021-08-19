package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.example.swebs_sampleapplication_210612.Fragment.MyTopMenuFragment.MyReviewFragment;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityMyTopMenuBinding;

public class MyTopMenuActivity extends AppCompatActivity {

    private ActivityMyTopMenuBinding binding;
    String requestCode;
    private FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyTopMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        manager = getSupportFragmentManager();
        requestCode = getIntent().getStringExtra("resultCode");
        if(requestCode.equals("review"))
        moveFragment(new MyReviewFragment(),"내 리뷰");

        binding.btnMyTopActivityBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void moveFragment(Fragment fragment, String string){
        binding.textViewMyTopActivityName.setText(string);
        manager.beginTransaction().replace(R.id.frameLayout_myTopMenu_activity, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (manager.getBackStackEntryCount() == 0) {
            finish();
        }
    }
}