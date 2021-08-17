package com.example.swebs_sampleapplication_210612.Dialog;

public class NumberPickerModel {
    private String title;
    private String[] list ;
    private String positive_btn;
    private String negative_btn;

    public NumberPickerModel(String title, String[] list, String positive_btn, String negative_btn) {
        this.title = title;
        this.list = list;
        this.positive_btn = positive_btn;
        this.negative_btn = negative_btn;
    }

    public String getTitle() {
        return title;
    }

    public String[] getList() {
        return list;
    }

    public String getPositive_btn() {
        return positive_btn;
    }

    public String getNegative_btn() {
        return negative_btn;
    }
}
