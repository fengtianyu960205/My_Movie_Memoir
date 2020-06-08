package com.example.my_movie_memoir.MovieDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity

public class MovieInfo {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "person_id")
    public int perId;
    @ColumnInfo(name = "movie_name")
    public String movieName;
    @ColumnInfo(name = "release_date")
    public String releaseDate;
    @ColumnInfo(name = "add_date_time")
    public Date addDate;
    @ColumnInfo(name = "movie_id")
    public int movieId;

    public MovieInfo( Integer perId,String movieName, String releaseDate, Date addDate,int movieId) {
        this.movieName = movieName;
        this.releaseDate = releaseDate;
        this.addDate = addDate;
        this.perId = perId;
        this.movieId = movieId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPersonId() {
        return perId;
    }

    public void setPersonId(int perId) {
        this.perId = perId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}

