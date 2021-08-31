package com.example.swebs_sampleapplication_210612.ViewModel.Model;

public class ReviewModel {

    private String userName;
    private String date;
    private int reviewSrl;
    private String title;
    private float ratingNum;
    private String content;
    private int likeNum;

    public ReviewModel(String userName, String date, int reviewSrl, String title, float ratingNum, String content, int likeNum) {
        this.userName = userName;
        this.date = date;
        this.reviewSrl = reviewSrl;
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

    public int getReviewSrl() {
        return reviewSrl;
    }

    public String getTitle() {
        return title;
    }

    public float getRatingNum() {
        return ratingNum;
    }

    public String getContent() {
        return content;
    }

    public int getLikeNum() {
        return likeNum;
    }
}
