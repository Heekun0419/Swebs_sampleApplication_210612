package com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailModel {

    private boolean success;
    private String corp_srl;
    private String corp_name;
    private String file_srl;
    private String prod_title;
    private String category_title;
    private String rating;
    private String review_count;
    private String rank;
    private String content;
    private List<String> content_file_srl;
    private String market_link;

    public boolean isSuccess() {
        return success;
    }

    public String getCorp_srl() {
        return corp_srl;
    }

    public String getCorp_name() {
        return corp_name;
    }

    public String getFile_srl() {
        return file_srl;
    }

    public String getProd_title() {
        return prod_title;
    }

    public String getCategory_title() {
        return category_title;
    }

    public String getRating() {
        return rating;
    }

    public String getReview_count() {
        return review_count;
    }

    public String getRank() {
        return rank;
    }

    public String getContent() {
        return content;
    }

    public List<String> getContent_file_srl() {
        return content_file_srl;
    }

    public String getMarket_link() {
        return market_link;
    }
}
