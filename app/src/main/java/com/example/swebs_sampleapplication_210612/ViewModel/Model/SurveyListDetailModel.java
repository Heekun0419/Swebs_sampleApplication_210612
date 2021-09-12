package com.example.swebs_sampleapplication_210612.ViewModel.Model;

public class SurveyListDetailModel {
    private String survey_srl;
    // 메인 이미지
    private String file_srl;
    // 서베이 참여시 지급 포인트
    private String point;
    // 서베이 제목
    private String survey_title;
    // 시작일
    private String start_date;
    // 종료일
    private String end_date;
    // 참여한 인원
    private String join_count;
    // 분류
    private String category_title;

    public String getFile_srl() {
        return file_srl;
    }

    public String getPoint() {
        return point;
    }

    public String getSurvey_srl() {
        return survey_srl;
    }

    public String getSurvey_title() {
        return survey_title;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getJoin_count() {
        return join_count;
    }

    public String getCategory_title() {
        return category_title;
    }



}
