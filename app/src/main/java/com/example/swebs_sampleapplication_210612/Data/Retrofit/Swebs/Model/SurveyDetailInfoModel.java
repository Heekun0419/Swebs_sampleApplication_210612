package com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model;

import java.util.List;

public class SurveyDetailInfoModel {

    private String survey_srl;
    private String survey_title;
    private String start_date;
    private String end_data;
    private String file_srl;
    private String category_srl;
    private String document_srl;
    private String join_count;
    private String point;
    private String regdate;
    private String content;
    private List<SurveyOptionModel> option;

    public List<SurveyOptionModel> getOption() {
        return option;
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

    public String getEnd_data() {
        return end_data;
    }

    public String getFile_srl() {
        return file_srl;
    }

    public String getCategory_srl() {
        return category_srl;
    }

    public String getDocument_srl() {
        return document_srl;
    }

    public String getJoin_count() {
        return join_count;
    }

    public String getPoint() {
        return point;
    }

    public String getRegdate() {
        return regdate;
    }

    public String getContent() {
        return content;
    }
}
