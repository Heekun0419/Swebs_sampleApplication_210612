package com.example.swebs_sampleapplication_210612.ViewModel.Model;

import java.util.List;

public class SurveyListModel {

    /*
 status = 1 >> 진행 중
 status = 2 >> 미 진행
 status = 3 >> 모집 종료
 */
    private int status;
    private String statusText;

    private String surveySrl;
    private String imageSrl;
    private String category_title;
    private String title;
    private String DateOfEvent;
    private String point;
    private String joinCount;

    public SurveyListModel(int status, String statusText, String surveySrl, String imageSrl, String category_title, String title, String dateOfEvent, String point, String joinCount) {
        this.status = status;
        this.statusText = statusText;
        this.surveySrl = surveySrl;
        this.imageSrl = imageSrl;
        this.category_title = category_title;
        this.title = title;
        DateOfEvent = dateOfEvent;
        this.point = point;
        this.joinCount = joinCount;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusText() {
        return statusText;
    }

    public String getSurveySrl() {
        return surveySrl;
    }

    public String getImageSrl() {
        return imageSrl;
    }

    public String getCategory_title() {
        return category_title;
    }

    public String getTitle() {
        return title;
    }

    public String getDateOfEvent() {
        return DateOfEvent;
    }

    public String getPoint() {
        return point;
    }

    public String getJoinCount() {
        return joinCount;
    }
}
