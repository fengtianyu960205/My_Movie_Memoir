package com.example.my_movie_memoir.Signin.entity;

import android.icu.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;


// credential class
public class Credential {
    private Integer credId;
    private String username;
    private String password;
    private String signDate;

    public Credential(Integer credId) {
        this.credId = credId;

    }

    public Credential(Integer credId, String username, String password, String signDate) {
        this.credId = credId;
        this.username = username;
        this.password = password;
        this.signDate = signDate;
    }

    public Credential(Integer credId, String username, String password, Date signDate) {
        this.credId = credId;
        this.username = username;
        this.password = password;
        this.signDate = localToUTC(signDate);
    }

    public Integer getcredId() {
        return credId;
    }

    public void setcredId(Integer credId) {
        this.credId = credId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public String getsignDate() {
        return signDate;
    }

    public void setsignDate(String signDate) {
        this.signDate = signDate;
    }

    private String localToUTC(Date localDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.CHINA);
        return sdf.format(localDate);
    }
}
