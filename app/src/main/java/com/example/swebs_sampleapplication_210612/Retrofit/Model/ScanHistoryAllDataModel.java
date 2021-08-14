package com.example.swebs_sampleapplication_210612.Retrofit.Model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ScanHistoryAllDataModel {
    @NonNull
    private Boolean success;

    @Nullable
    private String scanSrl;

    @Nullable
    private String reason;

    @NonNull
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(@NonNull Boolean success) {
        this.success = success;
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
}
