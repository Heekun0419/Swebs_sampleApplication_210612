package com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model;

public class ProductListModel {
    private String prod_srl;
    private String prod_title;
    private String file_srl;
    private String corp_name;
    private String logo_link;
    private boolean isNew;

    public String getProd_srl() {
        return prod_srl;
    }

    public String getProd_title() {
        return prod_title;
    }

    public String getFile_srl() {
        return file_srl;
    }

    public String getCorp_name() {
        return corp_name;
    }

    public String getLogo_link() {
        return logo_link;
    }

    public boolean isNew() {
        return isNew;
    }
}
