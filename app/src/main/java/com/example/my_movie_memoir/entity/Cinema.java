package com.example.my_movie_memoir.entity;


//Cinema class

public class Cinema {
    private int cinId;
    private String cinName;
    private String cinSuburb;
    private int cinPostcode;


    public Cinema(int cinId,String cinName, String cinSuburb, int  cinPostcode) {
        this.cinId = cinId;
        this.cinName = cinName;
        this.cinSuburb = cinSuburb;
        this.cinPostcode = cinPostcode;

    }

    public Cinema(int cinId) {
        this.cinId = cinId;
    }

    public int getCinId() {
        return cinId;
    }

    public void setCinId(int cinId) {
        this.cinId = cinId;
    }

    public String getCinName() {
        return cinName;
    }

    public void setCinName(String cinName) {
        this.cinName = cinName;
    }

    public String getCinSuburb() {
        return cinSuburb;
    }

    public void setCinSuburb(String cinSuburb) {
        this.cinSuburb = cinSuburb;
    }

    public int getCinPostcode() {
        return cinPostcode;
    }

    public void setCinPostcode(int cinPostcode) {
        this.cinPostcode = cinPostcode;
    }
}
