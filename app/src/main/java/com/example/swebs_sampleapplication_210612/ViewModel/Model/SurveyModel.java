package com.example.swebs_sampleapplication_210612.ViewModel.Model;

public class SurveyModel {
    // 메인 이미지
    private String ImageUrl;
    // 서베이 참여시 지급 포인트
    private String plusPoint;
    // 서베이 제목
    private String title;
    // 시작일
    private String startDate;
    // 종료일
    private String endDate;
    // 참여한 인원
    private String joinPeopleNum;
    // 분류
    private String category;

    public SurveyModel(String imageUrl, String plusPoint, String title,
                       String startDate, String endDate, String joinPeopleNum, String category) {
        ImageUrl = imageUrl;
        this.plusPoint = plusPoint;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.joinPeopleNum = joinPeopleNum;
        this.category = category;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getPlusPoint() {
        return plusPoint;
    }

    public String getTitle() {
        return title;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getJoinPeopleNum() {
        return joinPeopleNum;
    }

    public String getCategory() {
        return category;
    }
}
