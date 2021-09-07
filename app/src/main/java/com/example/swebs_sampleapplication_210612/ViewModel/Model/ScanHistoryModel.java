package com.example.swebs_sampleapplication_210612.ViewModel.Model;

public class ScanHistoryModel {
    // 등록날짜
    private String regDate;
    // 제품명
    private String productName;
    // 기업명
    private String companyName;
    // 제품 시리얼
    private String productSrl;
    //코드넘버
    private String productCodeNum;
    // 일반 QR인 경우 URL
    private String barcodeUrl;

    // 스웹스 제품인지, 아닌지 (상단탭 SWEBS/ ETC 나눌때 사용)
    private String category;

    // 정품여부
    private String status;

    // 등록여부
    private boolean isRegistered;

    // 스캔 히스토리와 구매등록 리스트 동일.
    public ScanHistoryModel(String regDate, String productName, String companyName,
                            String productSrl, String productCodeNum,
                            String status, boolean isRegistered) {
        this.regDate = regDate;
        this.productName = productName;
        this.companyName = companyName;
        this.productSrl = productSrl;
        this.productCodeNum = productCodeNum;
        this.status = status;
        this.isRegistered = isRegistered;
    }

    // ETC 에 들어가는 리스트
    public ScanHistoryModel(String regDate, String barcodeUrl, String productCodeNum){
        this.barcodeUrl = barcodeUrl;
        this.regDate = regDate;
        this.productCodeNum =productCodeNum;
    }

    public String getRegDate() {
        return regDate;
    }

    public String getProductName() {
        return productName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getProductSrl() {
        return productSrl;
    }

    public String getProductCodeNum() {
        return productCodeNum;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public boolean isRegistered() {
        return isRegistered;
    }
}
