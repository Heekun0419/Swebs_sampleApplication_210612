package com.example.swebs_sampleapplication_210612.ViewModel.Model;

import java.util.ArrayList;

public class ReviewModel {

    // 이미지 uri
    private String imageUri;
    //사용자가 추가한 이미지 (최대 5걔)
    private ArrayList<String> reviewImageList = new ArrayList<>();
    // 작성한 유저 이름
    private String userName;
    // 작성 날짜
    private String date;
    // 리뷰 타이틀 - 사용자가 작성한 리뷰 타이틀
    private String title;
    // 별점
    private String ratingNum;
    // 리뷰 작성 내용
    private String content;
    // 리뷰 좋아요 개수
    private String likeNum;

    // 해당 생성자는 내리뷰, 인증업체 제품 상세페이지 하단에 달려있는 리뷰형태 모델
    public ReviewModel(String userName, String date, String title, String ratingNum, String content, String likeNum) {
        this.userName = userName;
        this.date = date;
        this.title = title;
        this.ratingNum = ratingNum;
        this.content = content;
        this.likeNum = likeNum;
    }

    public String getUserName() {
        return userName;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getRatingNum() {
        return ratingNum;
    }

    public String getContent() {
        return content;
    }

    public String getLikeNum() {
        return likeNum;
    }
}
