package com.example.swebs_sampleapplication_210612.ViewModel.Model;

public class ChattingItem {

    private String ImageUrl;
    private String content;
    private String user_name;
    private String RegDate;

    public ChattingItem(String ImageUrl, String content, String user_name, String RegDate ){
        this.ImageUrl = ImageUrl;
        this.RegDate = RegDate;
        this.content = content;
        this.user_name = user_name;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getContent() {
        return content;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getRegDate() {
        return RegDate;
    }
}
