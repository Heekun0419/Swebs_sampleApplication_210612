package com.example.swebs_sampleapplication_210612.ViewModel.Model;

public class ReviewListModel {

    // file srl
    private String file_srl;
    // 회사명
    private String corp_name;
    // 제품명
    private String prod_title;
    // 별점
    private String avg_rate;
    // 해당 제품 리뷰개수
    private String review_count;
    // 제품 srl
    private String prod_srl;

    public ReviewListModel(String file_srl, String corp_name, String prod_title, String avg_rate, String review_count, String prod_srl) {
        this.file_srl = file_srl;
        this.corp_name = corp_name;
        this.prod_title = prod_title;
        this.avg_rate = avg_rate;
        this.review_count = review_count;
        this.prod_srl = prod_srl;
    }

    public String getFile_srl() {
        return file_srl;
    }

    public String getCorp_name() {
        return corp_name;
    }

    public String getProd_title() {
        return prod_title;
    }

    public String getAvg_rate() {
        return avg_rate;
    }

    public String getReview_count() {
        return review_count;
    }

    public String getProd_srl() {
        return prod_srl;
    }
}
