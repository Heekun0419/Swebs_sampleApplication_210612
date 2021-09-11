package com.example.swebs_sampleapplication_210612.ViewModel.Model;

import java.util.ArrayList;
import java.util.List;

public class ReviewModel {

    // profile srl
    private String profile_srl;
    //사용자가 추가한 이미지 (최대 5걔)
    private List<String> files = new ArrayList<>();
    private String member_srl;
    // 작성한 유저 이름
    private String nickname;
    // 작성 날짜
    private String regdate;
    // 리뷰 타이틀 - 사용자가 작성한 리뷰 타이틀
    private String review_title;
    // 별점
    private String rate;
    // 리뷰 작성 내용
    private String content;
    // 리뷰 좋아요 개수
    private String like_count;
    private String document_srl;
    private String review_srl;
    private boolean can_like;

    public String getMember_srl() {
        return member_srl;
    }

    public String getProfile_srl() {
        return profile_srl;
    }

    public String getNickname() {
        return nickname;
    }

    public String getRegdate() {
        return regdate;
    }

    public String getReview_title() {
        return review_title;
    }

    public String getRate() {
        return rate;
    }

    public String getContent() {
        return content;
    }

    public String getLike_count() {
        return like_count;
    }

    public String getDocument_srl() {
        return document_srl;
    }

    public String getReview_srl() {
        return review_srl;
    }

    public List<String> getFiles() {
        return files;
    }

    public boolean isCan_like() {
        return can_like;
    }
}
