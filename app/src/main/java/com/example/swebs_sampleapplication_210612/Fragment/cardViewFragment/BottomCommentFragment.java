package com.example.swebs_sampleapplication_210612.Fragment.cardViewFragment;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.Activity.ReCommentActivity;
import com.example.swebs_sampleapplication_210612.Data.Repository.CommentRepository;
import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.CommentInputModel;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.CommentViewModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.CommentModel;
import com.example.swebs_sampleapplication_210612.adapter.Comment_EventInfoAdapter;
import com.example.swebs_sampleapplication_210612.adapter.Listener.CommentClickListener;
import com.example.swebs_sampleapplication_210612.databinding.FragmentBottomCommentBinding;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomCommentFragment extends Fragment implements CommentClickListener {

    private FragmentBottomCommentBinding binding;
    private CommentViewModel viewModel;
    private MyInfoRepository myInfoRepository;
    private CommentRepository commentRepository;
    private Comment_EventInfoAdapter adapter;
    private SPmanager sPmanager;


    private final String documentSrl;

    public BottomCommentFragment(String documentSrl) {
        this.documentSrl = documentSrl;
    }

    private String nickname;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new CommentViewModel(requireActivity().getApplication());
        sPmanager = new SPmanager(requireActivity().getApplication());

        viewModel.getCommentList(documentSrl, null, null);

        commentRepository = new CommentRepository(requireActivity().getApplication());
        myInfoRepository = new MyInfoRepository(requireActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBottomCommentBinding.inflate(inflater,container,false);

        myInfoRepository.getValueToLiveData("nickName").observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s != null) nickname = s;
            }
        });

        binding.buttonSendComment.setOnClickListener(v -> {
            viewModel.pushComment(documentSrl, binding.editTextEventInfoComment.getText(), null);
            binding.editTextEventInfoComment.setText(null);
        });

        viewModel.getCommentLiveData().observe(getViewLifecycleOwner(), commentModels -> {
            if (adapter != null) {
                // 추가
                for (int i=0; i<commentModels.size(); i++){
                    adapter.addItem(commentModels.get(i), adapter.getItemCount()+i);
                }
            } else {
                // 초기화
                initRecyclerView(commentModels);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initRecyclerView(List<CommentModel> commentModels) {
        LinearLayoutManager manager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false);
        adapter = new Comment_EventInfoAdapter(requireContext(), commentModels, this);
        binding.recyclerViewEventInfoComment.setLayoutManager(manager);
        binding.recyclerViewEventInfoComment.setAdapter(adapter);
    }

    private String stringToHtml(Editable string) {
        return HtmlCompat.toHtml(string, HtmlCompat.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL);
    }

    @Override
    public void reportClicked(int position) {
        Toast.makeText(requireContext(), "신고", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void modifyClicked(int position) {
        Toast.makeText(requireContext(), "수정", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void removeClicked(int position) {
        Toast.makeText(requireContext(), "삭제", Toast.LENGTH_SHORT).show();
        adapter.removeItem(position);
    }

    @Override
    public void reCommentClicked(int position, CommentModel model) {
        Intent intent = new Intent(requireContext(), ReCommentActivity.class);
        intent.putExtra("name",model.getNickname());
        intent.putExtra("recomment_count",model.getRecomment_count());
        intent.putExtra("content",model.getContent());
        intent.putExtra("profile",model.getProfile_srl());
        intent.putExtra("date",model.getRegdate());
        intent.putExtra("documentSrl",documentSrl);
        intent.putExtra("commentSrl",model.getComment_srl());
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }
}