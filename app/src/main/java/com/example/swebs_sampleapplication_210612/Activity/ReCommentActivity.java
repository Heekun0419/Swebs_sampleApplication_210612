package com.example.swebs_sampleapplication_210612.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.ViewModel.CommentViewModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.CommentModel;

import com.example.swebs_sampleapplication_210612.adapter.Listener.CommentClickListener;
import com.example.swebs_sampleapplication_210612.adapter.ReCommentAdapter;
import com.example.swebs_sampleapplication_210612.databinding.ActivityReCommentBinding;

import java.util.ArrayList;
import java.util.List;

public class ReCommentActivity extends AppCompatActivity implements CommentClickListener {
    private ActivityReCommentBinding binding;
    private String profileSrl;
    private CommentViewModel viewModel;
    private ReCommentAdapter adapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new CommentViewModel(getApplication());

        // 초기 데이터 불러오기.
        viewModel.getReCommentList(getStringExtra("documentSrl"), getStringExtra("commentSrl"), null, null);

        binding.textViewCommentDate.setText(getStringExtra("date"));
        binding.textViewCommentContent.setText(htmlToString(getStringExtra("content")));
        binding.textViewUserName.setText(getStringExtra("name"));
        binding.textViewRecommentCount.setText(getStringExtra("recomment_count")+" 개의 답글");

        profileSrl = getIntent().getStringExtra("profile");
        GlideImage(binding.imageViewCommentProfile,profileSrl);

        // 뒤로가기 클릭시
        binding.btnActivityBack.setOnClickListener(v -> onBackPressed());

        // 답글 추가 버튼 클릭
        binding.buttonSendComment.setOnClickListener(v -> {
            viewModel.pushComment(getStringExtra("documentSrl"), binding.editTextRecomment.getText(), getStringExtra("commentSrl"));
            binding.editTextRecomment.setText(null);
        });

        viewModel.getCommentLiveData().observe(this, commentModels -> {
            if (adapter != null) {
                // 추가
                for (int i=0; i<commentModels.size(); i++){
                    adapter.addItem(commentModels.get(i), adapter.getItemCount()+i);
                }
            } else {
                // 초기화
                initRecyclerView(commentModels);
            }

            binding.textViewRecommentCount.setText(adapter.getItemCount() + " 개의 답글");
        });

    }
    private String getStringExtra(String key){
        return getIntent().getStringExtra(key);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private String getImageViewUrl(String fileSrl, String Width) {
        String result = getString(R.string.IMAGE_VIEW_URL) + "?inputFileSrl=" + fileSrl;
        if (Width != null) result += "&inputImageWidth=" + Width;
        return result;
    }

    private void GlideImage(ImageView view, String srl){
        Glide.with(this).load(getImageViewUrl(srl,"300")).placeholder(R.drawable.ic_profile_basic).circleCrop().into(view);
    }

    private String htmlToString(String html) {
        return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
    }

    private void initRecyclerView(List<CommentModel> list) {
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        adapter = new ReCommentAdapter(this, list, this);
        binding.recyclerViewReComment.setLayoutManager(manager);
        binding.recyclerViewReComment.setAdapter(adapter);
    }

    @Override
    public void reportClicked(int position) {
    }

    @Override
    public void modifyClicked(int position) {

    }

    @Override
    public void removeClicked(int position) {
        adapter.removeItem(position);
    }

    @Override
    public void reCommentClicked(int position, CommentModel model) {

    }
}