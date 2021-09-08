package com.example.swebs_sampleapplication_210612.ViewModel.Model;

public class CommentModel {

    private String comment_srl;
    private String content;
    private String member_srl;
    private String regdate;
    private String lastupadate;
    private String nickname;
    private String profile_srl;
    private String recomment_count;

    public CommentModel(String content, String nickname, String lastupadate, String profile_srl ){
        this.content = content;
        this.lastupadate = lastupadate;
        this.nickname = nickname;
        this.profile_srl = profile_srl;
    }

    public String getComment_srl() {
        return comment_srl;
    }

    public String getContent() {
        return content;
    }

    public String getMember_srl() {
        return member_srl;
    }

    public String getRegdate() {
        return regdate;
    }

    public String getLastupadate() {
        return lastupadate;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfile_srl() {
        return profile_srl;
    }

    public String getRecomment_count() {
        return recomment_count;
    }
}
