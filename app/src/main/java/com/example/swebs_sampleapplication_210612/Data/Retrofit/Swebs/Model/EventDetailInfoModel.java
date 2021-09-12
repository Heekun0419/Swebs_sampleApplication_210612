package com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model;

import androidx.annotation.Nullable;

public class EventDetailInfoModel {
    private String event_title;
    private String corp_name;
    private String start_date;
    private String end_date;
    private String now_date;
    private String file_srl;
    @Nullable
    private String content_file_srl;
    private String content;
    private String link;
    private String like_count;
    private boolean can_like;
    private boolean can_join;
    private String document_srl;

    public String getEvent_title() {
        return event_title;
    }

    public String getCorp_name() {
        return corp_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getNow_date() {
        return now_date;
    }

    public String getFile_srl() {
        return file_srl;
    }

    @Nullable
    public String getContent_file_srl() {
        return content_file_srl;
    }

    public String getContent() {
        return content;
    }

    public String getLink() {
        return link;
    }

    public String getLike_count() {
        return like_count;
    }

    public boolean isCan_like() {
        return can_like;
    }

    public boolean isCan_join() {
        return can_join;
    }

    public String getDocument_srl() {
        return document_srl;
    }
}
