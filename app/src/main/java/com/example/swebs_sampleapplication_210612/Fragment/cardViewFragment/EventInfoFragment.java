package com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Activity.ItemClickViewActivty;
import com.example.swebs_sampleapplication_210612.ViewModel.ChatViewModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ChattingItem;
import com.example.swebs_sampleapplication_210612.adapter.Comment_EventInfoAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentEventInfoBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventInfoFragment extends Fragment {

    private FragmentEventInfoBinding binding;
    private ArrayList<ChattingItem> chattingItems = new ArrayList<>();
    private ChatViewModel chatViewModel;

    String url = "https://image.shutterstock.com/image-photo/beauty-concept-head-shoulders-girl-260nw-740874595.jpg";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEventInfoBinding.inflate(inflater,container,false);
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        ImageView view = binding.imageViewEventInfoProfile;
        GlideImage(view);

        chatViewModel.getChattingLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<ChattingItem>>() {
            @Override
            public void onChanged(ArrayList<ChattingItem> chattingItems) {
                initRecyclerView(chattingItems);
            }
        });

        binding.buttonSendComment.setOnClickListener(v -> {
            String message = binding.editTextEventInfoComment.getText().toString();
            chattingItems.add(new ChattingItem(null, message,
                    null,
                    new SimpleDateFormat("HH:mm").format(new Date())));
            chatViewModel.setChattingLiveData(chattingItems);

            binding.editTextEventInfoComment.setText(null);
        });

        binding.btnItemClickedBack.setOnClickListener(v ->
                ((ItemClickViewActivty)requireActivity()).onBackPressed());

        return binding.getRoot();
    }

    private void GlideImage(ImageView view){
        Glide.with(requireContext()).load(url).into(view);
    }

    private void initRecyclerView(ArrayList<ChattingItem> chattingItems){
        Comment_EventInfoAdapter adapter = new Comment_EventInfoAdapter(requireContext(), chattingItems);
        LinearLayoutManager manager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false);
        binding.recyclerViewEventInfoComment.setLayoutManager(manager);
        binding.recyclerViewEventInfoComment.setAdapter(adapter);
    }
}