package com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model;

import androidx.annotation.Nullable;

public class LoginModel {

    private boolean success;

    @Nullable
    private String userSrl;
    @Nullable
    private String userEmail;
    @Nullable
    private String reason;
    @Nullable
    private String userType;
    @Nullable
    private String token;
    @Nullable
    private String password;
    @Nullable
    private String point;
    @Nullable
    private String name;

    @Nullable
    private String nickname;

    @Nullable
    private String gender;

    @Nullable
    private String region;


    public boolean isSuccess() {
        return success;
    }

    @Nullable
    public String getUserSrl() {
        return userSrl;
    }

    @Nullable
    public String getUserEmail() {
        return userEmail;
    }

    @Nullable
    public String getReason() {
        return reason;
    }

    @Nullable
    public String getUserType() {
        return userType;
    }

    @Nullable
    public String getToken() {
        return token;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    @Nullable
    public String getPoint() {
        return point;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getNickname() {
        return nickname;
    }

    @Nullable
    public String getGender() {
        return gender;
    }

    @Nullable
    public String getRegion() {
        return region;
    }
}

