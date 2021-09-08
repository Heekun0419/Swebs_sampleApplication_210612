package com.example.swebs_sampleapplication_210612.ViewModel.Model;

public class EventModel {
    /*
    status = 1 >> 진행 중
    status = 2 >> 미 진행
    status = 3 >> 모집 종료
    */
    private int status;
    private String statusText;

    private String eventSrl;
    private String imageSrl;
    private String CompanyName;
    private String Title;
    private String DateOfEvent;

    public EventModel(int status, String statusText, String eventSrl, String imageSrl, String companyName, String title, String dateOfEvent) {
        this.status = status;
        this.statusText = statusText;
        this.eventSrl = eventSrl;
        this.imageSrl = imageSrl;
        CompanyName = companyName;
        Title = title;
        DateOfEvent = dateOfEvent;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusText() {
        return statusText;
    }

    public String getEventSrl() {
        return eventSrl;
    }

    public String getImageSrl() {
        return imageSrl;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public String getTitle() {
        return Title;
    }

    public String getDateOfEvent() {
        return DateOfEvent;
    }
}
