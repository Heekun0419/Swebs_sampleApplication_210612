package com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model;

import org.jetbrains.annotations.Nullable;

public class GuestSignUpModel {
    private boolean success;
    @Nullable
    private String userSrl;
    @Nullable
    private String token;
    @Nullable
    private String name;
    @Nullable
    private String nickname;
    @Nullable
    private String point;
    @Nullable
    private String country;
    @Nullable
    private String reason;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Nullable
    public String getUserSrl() {
        return userSrl;
    }

    public void setUserSrl(@Nullable String userSrl) {
        this.userSrl = userSrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Nullable
    public String getCountry() {
        return country;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Nullable
    public String getPoint() {
        return point;
    }

    public void setPoint(@Nullable String point) {
        this.point = point;
    }
}
