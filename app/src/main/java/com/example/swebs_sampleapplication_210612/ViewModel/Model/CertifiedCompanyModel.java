package com.example.swebs_sampleapplication_210612.ViewModel.Model;

public class CertifiedCompanyModel {

    // 대표이미지 uri
    private String profileImageUrl;
    // 회사 이름
    private String companyName;
    // 상품 이름
    private String productName;
    //회사 로고 uri
    private String companyLogoUrl;
    //상품 등록 일자? 확인해서 새로등록한 제품이면 New 마크 Visible / 아니면 마크 Gone -> TopMenu 인증업체에서 보여짐.
    private boolean isNew;

    public CertifiedCompanyModel (String profileImageUrl, String companyName,
                                 String productName, String companyLogoUrl, boolean isNew) {
        this.profileImageUrl = profileImageUrl;
        this.companyName = companyName;
        this.productName = productName;
        this.companyLogoUrl = companyLogoUrl;
        this.isNew = isNew;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getProductName() {
        return productName;
    }

    public String getCompanyLogoUrl() {
        return companyLogoUrl;
    }

    public boolean isNew() {
        return isNew;
    }
}
