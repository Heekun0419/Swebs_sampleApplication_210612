package com.example.swebs_sampleapplication_210612.ViewModel.Model;

public class ReviewMainModel {

    // 이미지 uri
    private String imageUri;
    // 회사명
    private String companyName;
    // 제품명
    private String productName;
    // 별점
    private String ratingNum;
    // 리뷰 작성 내용
    private String content;
    // 해당 제품 리뷰개수
    private String reviewNum;
    // 순위
    private String rank;

    // 메인에서 보여지는 리뷰 생성자
    public ReviewMainModel(String productName, String companyName, String content, String ratingNum) {
        this.content = content;
        this.productName = productName;
        this.ratingNum = ratingNum;
        this.companyName = companyName;
    }

    // TopMenu 에서 보여지는 제품 랭킹 생성자
    public ReviewMainModel(String imageUri, String companyName, String productName, String ratingNum, String reviewNum, String rank) {
        this.imageUri = imageUri;
        this.companyName = companyName;
        this.productName = productName;
        this.ratingNum = ratingNum;
        this.reviewNum = reviewNum;
        this.rank = rank;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getProductName() {
        return productName;
    }

    public String getRatingNum() {
        return ratingNum;
    }

    public String getContent() {
        return content;
    }

    public String getReviewNum() {
        return reviewNum;
    }

    public String getRank() {
        return rank;
    }
}
