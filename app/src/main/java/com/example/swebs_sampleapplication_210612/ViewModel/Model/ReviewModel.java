package com.example.swebs_sampleapplication_210612.ViewModel.Model;

import java.util.ArrayList;
import java.util.List;

public class ReviewModel {
    // profile srl
    private String profile_srl;
    //사용자가 추가한 이미지 (최대 5걔)
    private List<String> file_srl = new ArrayList<>();
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
    // 리뷰 내 댓글 불러올때 필요
    private String document_srl;
    // 리뷰 srl 넘겨서 리뷰 자세히 보기
    private String review_srl;
    //like 값
    private boolean can_like;

   // private String prod_srl;

  //  public String getProd_srl() {
       // return prod_srl;
    //}

    public List<String> getFile_srl() {
        return file_srl;
    }

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

    public boolean isCan_like() {
        return can_like;
    }
}
