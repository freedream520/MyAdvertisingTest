package com.hbln.myadvertisingtest.Model;

/**
 * 这个类的作用
 * Created by lwc on 2016/3/10.
 */
public class UserInfo{

    private String nickname;

    private String csid;

    private String create_time;

    public UserInfo() {
    }

    public UserInfo(String nickname, String csid, String create_time) {
        this.nickname = nickname;
        this.csid = csid;
        this.create_time = create_time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCsid() {
        return csid;
    }

    public void setCsid(String csid) {
        this.csid = csid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "nickname='" + nickname + '\'' +
                ", csid='" + csid + '\'' +
                ", create_time='" + create_time + '\'' +
                '}';
    }

}
