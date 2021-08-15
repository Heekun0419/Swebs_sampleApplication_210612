package com.example.swebs_sampleapplication_210612.Data.Retrofit.Model;

import androidx.annotation.Nullable;

public class LoginModel {

    private boolean success;

    @Nullable
    private String member_srl;
    @Nullable
    private String user_email;
    @Nullable
    private String reason;
    @Nullable
    private String user_type;
    @Nullable
    private String token;
    @Nullable
    private String password;
    @Nullable
    private String points;
    @Nullable
    private String name;

    @Nullable
    private String nickname;

    @Nullable
    private String dateofbirth;
    @Nullable
    private String phonenumber;

    @Nullable
    private String gender;

    @Nullable
    private String region;

    @Nullable
    private String lastlogindate;
    @Nullable
    private String regdate;

    public boolean isSuccess() {
        return success;
    }

    @Nullable
    public String getPoints() {
        return points;
    }

    @Nullable
    public String getMember_srl() {
        return member_srl;
    }

    @Nullable
    public String getUser_email() {
        return user_email;
    }

    @Nullable
    public String getUser_type() {
        return user_type;
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
    public String getName() {
        return name;
    }

    @Nullable
    public String getNickname() {
        return nickname;
    }

    @Nullable
    public String getDateofbirth() {
        return dateofbirth;
    }

    @Nullable
    public String getPhonenumber() {
        return phonenumber;
    }

    @Nullable
    public String getGender() {
        return gender;
    }

    @Nullable
    public String getRegion() {
        return region;
    }

    @Nullable
    public String getLastlogindate() {
        return lastlogindate;
    }

    @Nullable
    public String getRegdate() {
        return regdate;
    }

    @Nullable
    public String getReason() {
        return reason;
    }
}

