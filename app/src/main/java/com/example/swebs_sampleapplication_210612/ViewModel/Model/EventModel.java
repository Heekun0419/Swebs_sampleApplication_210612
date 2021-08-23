package com.example.swebs_sampleapplication_210612.ViewModel.Model;

public class EventModel {
    private String ImageUrl;
    private String CompanyName;
    private String Title;
    private String DateOfEvent;

    public EventModel(String imageUrl, String companyName, String title, String dateOfEvent) {
        ImageUrl = imageUrl;
        CompanyName = companyName;
        Title = title;
        DateOfEvent = dateOfEvent;
    }

    public String getImageUrl() {
        return ImageUrl;
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
