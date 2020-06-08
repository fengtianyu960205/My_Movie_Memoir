package com.example.my_movie_memoir.entity;

import android.icu.text.SimpleDateFormat;

import com.example.my_movie_memoir.Signin.entity.Person;

import java.util.Date;
import java.util.Locale;

// Memoir class


public class Memoir {
    private int memId;
    private String movName;
    private String movRelease;
    private String movWdate;
    private String comment;
    private int rate;
    private Cinema cinId;
    private Person perId;


    public Memoir(int memId, String movName, String movRelease, String movWdate, String comment, int rate) {
        this.memId = memId;
        this.movName = movName;
        this.movRelease = movRelease;
        this.movWdate = movWdate;
        this.comment = comment;
        this.rate = rate;

    }

    public Memoir(int memId, String movName, Date movRelease, Date movWdate, String comment, int rate) {
        this.memId = memId;
        this.movName = movName;
        this.movRelease = localToUTC(movRelease);
        this.movWdate = localToUTC(movWdate);
        this.comment = comment;
        this.rate = rate;

    }

    public Memoir(int memId, String movName, String movRelease, Date movWdate, String comment, int rate) {
        this.memId = memId;
        this.movName = movName;
        this.movRelease = movRelease;
        this.movWdate = localToUTC(movWdate);
        this.comment = comment;
        this.rate = rate;

    }

    public int getMemId() {
        return memId;
    }

    public void setMemId(int memId) {
        this.memId = memId;
    }

    public String getMovName() {
        return movName;
    }

    public void setMovName(String movName) {
        this.movName = movName;
    }

    public String getMovRelease() {
        return movRelease;
    }

    public void setMovRelease(String movRelease) {
        this.movRelease = movRelease;
    }

    public String getMovWdate() {
        return movWdate;
    }

    public void setMovWdate(String movWdate) {
        this.movWdate = movWdate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getCinId() {
        return cinId.getCinId();
    }

    public void setCinId(int cinId) {
        this.cinId = new Cinema(cinId);
    }

    public int getPerID() {
        return perId.getperId();
    }

    public void setPerID(int perID) {
        this.perId = new Person(perID);
    }

    private String localToUTC(Date localDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.CHINA);
        return sdf.format(localDate);
    }


}
