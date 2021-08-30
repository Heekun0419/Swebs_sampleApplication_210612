package com.example.swebs_sampleapplication_210612.util.Listener;

public interface onScanListener {
    void onSwebs(boolean isSwebsUrl, String scanSrl, String company, String code);
    void onOther();
    void onFailed();
}
