package com.example.swebs_sampleapplication_210612.ViewModel.Model;

public class CommentModel {

    private String comment_srl;
    private String content;
    private String member_srl;
    private String regdate;
    private String lastupdate;
    private String nickname;
    private String profile_srl;
    private String recomment_count;

    public CommentModel(String comment_srl, String content, String member_srl, String regdate, String lastupdate, String nickname, String profile_srl, String recomment_count) {
        this.comment_srl = comment_srl;
        this.content = content;
        this.member_srl = member_srl;
        this.regdate = regdate;
        this.lastupdate = lastupdate;
        this.nickname = nickname;
        this.profile_srl = profile_srl;
        this.recomment_count = recomment_count;
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

    public String getLastupdate() {
        return lastupdate;
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
