package com.me.xposed;

/**
 * Created by cheng on 2016/05/24.
 */
public class Tag {
    long id;
    int cnt;
    int point;
    String imsi;
    String mail;
    String pwd;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        if(cnt>4)cnt=4;
        this.cnt = cnt;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        if (point > 20) point = 20;
        this.point = point;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
