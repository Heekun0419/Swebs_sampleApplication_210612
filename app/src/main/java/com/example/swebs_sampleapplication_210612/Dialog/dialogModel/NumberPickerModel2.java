package com.example.swebs_sampleapplication_210612.Dialog.dialogModel;

import java.util.List;

public class NumberPickerModel2 {
    private String title;
    private List<String> list ;
    private String positive_btn;
    private String negative_btn;
    private int defaultValue;

    public NumberPickerModel2(String title, List<String> list, int defaultValue, String positive_btn, String negative_btn) {
        this.title = title;
        this.list = list;
        this.defaultValue = defaultValue;
        this.positive_btn = positive_btn;
        this.negative_btn = negative_btn;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getList() {
        return list;
    }

    public String getPositive_btn() {
        return positive_btn;
    }

    public String getNegative_btn() {
        return negative_btn;
    }

    public int getDefaultValue() {
        return defaultValue;
    }
}
