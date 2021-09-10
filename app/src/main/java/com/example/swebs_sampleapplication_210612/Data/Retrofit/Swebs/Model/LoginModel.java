package com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model;

import androidx.annotation.Nullable;

public class LoginModel {
    private boolean success;

    @Nullable
    private String userSrl;
    @Nullable
    private String userType;
    @Nullable
    private String token;
    @Nullable
    private String nickName;
    @Nullable
    private String name;
    @Nullable
    private String birthday;
    @Nullable
    private String gender;
    @Nullable
    private String phoneNumber;
    @Nullable
    private String country;
    @Nullable
    private String region;
    @Nullable
    private String email;
    @Nullable
    private String referralCode;
    @Nullable
    private String point;
    private String profileSrl;


    public boolean isSuccess() {
        return success;
    }

    @Nullable
    public String getUserSrl() {
        return userSrl;
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
    public String getNickName() {
        return nickName;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getBirthday() {
        return birthday;
    }

    @Nullable
    public String getGender() {
        return gender;
    }

    @Nullable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Nullable
    public String getCountry() {
        return country;
    }

    @Nullable
    public String getRegion() {
        return region;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    @Nullable
    public String getReferralCode() {
        return referralCode;
    }

    @Nullable
    public String getPoint() {
        return point;
    }

    public String getProfileSrl() {
        return profileSrl;
    }
}

