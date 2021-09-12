package com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model;

public class CommentInputModel {
    private boolean success;
    private String comment_srl;
    private String reason;
    private String nickname;
    private String profile_srl;
    private String regdate;

    public boolean isSuccess() {
        return success;
    }

    public String getComment_srl() {
        return comment_srl;
    }

    public String getReason() {
        return reason;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfile_srl() {
        return profile_srl;
    }

    public String getRegdate() {
        return regdate;
    }
}
