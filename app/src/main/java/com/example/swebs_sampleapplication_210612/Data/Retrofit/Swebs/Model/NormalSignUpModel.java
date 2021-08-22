package com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model;

import org.jetbrains.annotations.Nullable;

public class NormalSignUpModel {
    private boolean success;

    @Nullable
    private String userSrl;

    @Nullable
    private String userType;

    @Nullable
    private String name;

    @Nullable
    private String nickname;

    @Nullable
    private String birthday;

    @Nullable
    private String gender;

    @Nullable
    private String country;

    @Nullable
    private String point;

    @Nullable
    private String token;

    @Nullable
    private String referralCode;

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

    @Nullable
    public String getUserType() {
        return userType;
    }

    public void setUserType(@Nullable String userType) {
        this.userType = userType;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getNickname() {
        return nickname;
    }

    public void setNickname(@Nullable String nickname) {
        this.nickname = nickname;
    }

    @Nullable
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(@Nullable String birthday) {
        this.birthday = birthday;
    }

    @Nullable
    public String getGender() {
        return gender;
    }

    public void setGender(@Nullable String gender) {
        this.gender = gender;
    }

    @Nullable
    public String getPoint() {
        return point;
    }

    public void setPoint(@Nullable String point) {
        this.point = point;
    }

    @Nullable
    public String getCountry() {
        return country;
    }

    @Nullable
    public String getToken() {
        return token;
    }

    public void setToken(@Nullable String token) {
        this.token = token;
    }

    @Nullable
    public String getReason() {
        return reason;
    }

    @Nullable
    public String getReferralCode() {
        return referralCode;
    }
}
