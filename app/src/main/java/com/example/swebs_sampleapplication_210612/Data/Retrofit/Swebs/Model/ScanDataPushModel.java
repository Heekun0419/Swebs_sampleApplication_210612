package com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ScanDataPushModel {
    @NonNull
    private boolean success;

    @Nullable
    private String scanSrl;

    private String now_date;

    @Nullable
    private String reason;

    public boolean isSuccess() {
        return success;
    }

    @Nullable
    public String getScanSrl() {
        return scanSrl;
    }

    public void setScanSrl(@Nullable String scanSrl) {
        this.scanSrl = scanSrl;
    }

    @Nullable
    public String getReason() {
        return reason;
    }

    public void setReason(@Nullable String reason) {
        this.reason = reason;
    }

    public String getNow_date() {
        return now_date;
    }
}
