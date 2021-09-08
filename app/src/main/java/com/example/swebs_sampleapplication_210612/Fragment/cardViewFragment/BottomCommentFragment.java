package com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.Data.Repository.CommentRepository;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.CommentInputModel;
import com.example.swebs_sampleapplication_210612.ViewModel.ChatViewModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.CommentModel;
import com.example.swebs_sampleapplication_210612.adapter.Comment_EventInfoAdapter;
import com.example.swebs_sampleapplication_210612.databinding.FragmentBottomCommentBinding;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomCommentFragment extends Fragment {

    private FragmentBottomCommentBinding binding;
    private ArrayList<CommentModel> commentModels = new ArrayList<>();
    private ChatViewModel chatViewModel;

    private CommentRepository commentRepository;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        commentRepository = new CommentRepository(requireActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBottomCommentBinding.inflate(inflater,container,false);

        binding.buttonSendComment.setOnClickListener(v -> {
            String message = binding.editTextEventInfoComment.getText().toString();


            commentRepository.pushComment("1", "5080", stringToHtml(binding.editTextEventInfoComment.getText()), null)
                    .enqueue(new Callback<CommentInputModel>() {
                        @Override
                        public void onResponse(Call<CommentInputModel> call, Response<CommentInputModel> response) {
                            if (response.isSuccessful()
                            && response.body() != null) {
                                Toast.makeText(requireContext(), "업로드 성공 : " + response.body().getComment_srl(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<CommentInputModel> call, Throwable t) {

                        }
                    });

            Toast.makeText(requireContext(), Html.toHtml(binding.editTextEventInfoComment.getText()), Toast.LENGTH_SHORT).show();
            commentModels.add(new CommentModel(null, message,
                    null,
                    new SimpleDateFormat("MM-dd HH:mm").format(new Date())));
            chatViewModel.setChattingLiveData(commentModels);

            binding.editTextEventInfoComment.setText(null);
        });

        chatViewModel.getChattingLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<CommentModel>>() {
            @Override
            public void onChanged(ArrayList<CommentModel> commentModels) {
                initRecyclerView(commentModels);
            }
        });

        return binding.getRoot();
    }

    private void initRecyclerView(ArrayList<CommentModel> commentModels){
        Comment_EventInfoAdapter adapter = new Comment_EventInfoAdapter(requireContext(), commentModels);
        LinearLayoutManager manager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false);
        binding.recyclerViewEventInfoComment.setLayoutManager(manager);
        binding.recyclerViewEventInfoComment.setAdapter(adapter);
    }

    private String stringToHtml(Spanned string) {
        return Html.toHtml(string, Html.FROM_HTML_MODE_LEGACY);
    }
}