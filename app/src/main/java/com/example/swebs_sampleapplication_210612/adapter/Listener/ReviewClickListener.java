package com.example.swebs_sampleapplication_210612.adapter.Listener;

import com.example.swebs_sampleapplication_210612.ViewModel.Model.CommentModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ReviewModel;

public interface ReviewClickListener {
    void profileClicked(int position);
    void LikeClicked(int position,boolean isLike);
    void reportClicked(int position);
    void modifyClicked(int position);
    void removeClicked(int position);
    void CommentClicked(int position, ReviewModel model);
}
