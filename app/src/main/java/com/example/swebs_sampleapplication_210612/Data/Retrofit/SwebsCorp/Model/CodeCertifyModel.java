package com.example.swebs_sampleapplication_210612.Data.Retrofit.SwebsCorp.Model;

import org.jetbrains.annotations.Nullable;

public class CodeCertifyModel {
    private String Code;

    @Nullable
    private String comp_nm;

    @Nullable
    private String prod_nm;

    @Nullable
    private String r_chk;

    @Nullable
    private int r_cnt;

    @Nullable
    private String s_no;

    @Nullable
    private String r_date;

    @Nullable
    private String comp_logo_img;

    @Nullable
    private String product_img;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    @Nullable
    public String getComp_nm() {
        return comp_nm;
    }

    public void setComp_nm(@Nullable String comp_nm) {
        this.comp_nm = comp_nm;
    }

    @Nullable
    public String getProd_nm() {
        return prod_nm;
    }

    public void setProd_nm(@Nullable String prod_nm) {
        this.prod_nm = prod_nm;
    }

    @Nullable
    public String getR_chk() {
        return r_chk;
    }

    public void setR_chk(@Nullable String r_chk) {
        this.r_chk = r_chk;
    }

    public int getR_cnt() {
        return r_cnt;
    }

    public void setR_cnt(int r_cnt) {
        this.r_cnt = r_cnt;
    }

    @Nullable
    public String getS_no() {
        return s_no;
    }

    public void setS_no(@Nullable String s_no) {
        this.s_no = s_no;
    }

    @Nullable
    public String getR_date() {
        return r_date;
    }

    public void setR_date(@Nullable String r_date) {
        this.r_date = r_date;
    }

    @Nullable
    public String getComp_logo_img() {
        return comp_logo_img;
    }

    public void setComp_logo_img(@Nullable String comp_logo_img) {
        this.comp_logo_img = comp_logo_img;
    }

    @Nullable
    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(@Nullable String product_img) {
        this.product_img = product_img;
    }
}
