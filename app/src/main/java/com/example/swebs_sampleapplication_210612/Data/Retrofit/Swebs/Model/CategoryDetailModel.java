package com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model;

public class CategoryDetailModel {
    private String category_srl;
    private String category_title;

    public CategoryDetailModel(String category_srl, String category_title) {
        this.category_srl = category_srl;
        this.category_title = category_title;
    }

    public String getCategory_srl() {
        return category_srl;
    }

    public void setCategory_srl(String category_srl) {
        this.category_srl = category_srl;
    }

    public String getCategory_title() {
        return category_title;
    }

    public void setCategory_title(String category_title) {
        this.category_title = category_title;
    }
}
