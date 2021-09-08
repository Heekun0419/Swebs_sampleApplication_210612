package com.example.swebs_sampleapplication_210612.adapter.Listener;

import com.example.swebs_sampleapplication_210612.ViewModel.Model.CommentModel;

public interface CommentClickListener {
    void reportClicked(int position);
    void modifyClicked(int position);
    void removeClicked(int position);
    void reCommentClicked(int position, CommentModel model);
}
