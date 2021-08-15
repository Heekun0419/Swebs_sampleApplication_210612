package com.example.swebs_sampleapplication_210612.Data.Retrofit.Model;

import androidx.annotation.Nullable;

public class SignUpModel {

    private Boolean success;

    @Nullable
    private String reason;

    @Nullable
    private String userSrl;

    @Nullable
    private String userNickname;

    @Nullable
    private String token;

    public Boolean getSuccess() {
        return success;
    }

    @Nullable
    public String getReason() {
        return reason;
    }

    @Nullable
    public String getUserSrl() {
        return userSrl;
    }

    @Nullable
    public String getUserNickname() {
        return userNickname;
    }

    @Nullable
    public String getToken() {
        return token;
    }
}
