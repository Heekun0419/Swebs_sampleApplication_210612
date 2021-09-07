package com.example.swebs_sampleapplication_210612.util.Listener;

public interface onSimpleLoginListener {
    void onSuccess(String userType, String uid, String email, String nickname);
    void onFailed();
}
