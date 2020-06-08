package com.example.my_movie_memoir.Signin.entity;

import android.icu.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;


// person class
public class Person {

    private Integer perId;
    private String perFn;
    private String perLn;
    private String gender;
    private String dob;// date 可以存为string
    private String address;
    private String perState;
    private Integer perPostcode;
    private Credential credId;

    public Person(Integer perId) {
        this.perId = perId;
    }

    public Person(Integer perId, String perFn, String perLn, String gender, Date dob, String address, String perState, Integer perPostcode) {
        this.perId = perId;
        this.perFn = perFn;
        this.perLn = perLn;
        this.gender = gender;
        this.dob = localToUTC(dob);
        this.address = address;
        this.perState = perState;
        this.perPostcode = perPostcode;
    }

    public Person(Integer perId, String perFn, String perLn, String gender, String dob, String address, String perState, Integer perPostcode) {
        this.perId = perId;
        this.perFn = perFn;
        this.perLn = perLn;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        this.perState = perState;
        this.perPostcode = perPostcode;
    }

    public Integer getperId() {
        return perId;
    }

    public void setperId(Integer perId) {
        this.perId = perId;
    }

    public String getperFn() {
        return perFn;
    }

    public void setperFn(String perFn) {
        this.perFn = perFn;
    }

    public String getperLn() {
        return perLn;
    }

    public void setperLn(String perLn) {
        this.perLn = perLn;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getperState() {
        return perState;
    }

    public void setperState(String perState) {
        this.perState = perState;
    }

    public Integer getperPostcode() {
        return perPostcode;
    }

    public void setperPostcode(Integer perPostcode) {
        this.perPostcode = perPostcode;
    }

    public int getcredId() {
        return credId.getcredId();
    }

    public void setcredId(int credId) {
        this.credId = new Credential(credId);
    }

    private String localToUTC(Date localDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.CHINA);
        return sdf.format(localDate);
    }

}
