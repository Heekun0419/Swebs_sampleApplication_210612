package com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.FragmentEventInfoBinding;

public class EventInfoFragment extends Fragment {
    private FragmentEventInfoBinding binding;

    String url = "https://image.shutterstock.com/image-photo/beauty-concept-head-shoulders-girl-260nw-740874595.jpg";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEventInfoBinding.inflate(inflater,container,false);

        ImageView view = binding.imageViewEventInfoProfile;
        GlideImage(view);
        return binding.getRoot();
    }

    private void GlideImage(ImageView view){
        Glide.with(requireContext()).load(url).into(view);
    }

}