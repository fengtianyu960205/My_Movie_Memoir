package com.example.my_movie_memoir.MovieDB;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DAO {
    @Query("SELECT * FROM MovieInfo")
    LiveData<List<MovieInfo>>  getAll();
    @Query("SELECT * FROM MovieInfo WHERE uid = :movieId LIMIT 1")
    MovieInfo findByID(int movieId);
    @Insert
    void insertAll(MovieInfo... movieInfos);
    @Insert
    long insert(MovieInfo movieInfo);
    @Delete
    void delete(MovieInfo movieInfo);
    @Update(onConflict = REPLACE)
    void updateMovies(MovieInfo... movieInfos);
    @Query("DELETE FROM MovieInfo")
    void deleteAll();
    @Query("SELECT * FROM MovieInfo WHERE person_id = :perId and movie_name = :MovieName and release_date = :releaseDate LIMIT 1")
    MovieInfo findByMovie(int perId, String MovieName , String releaseDate);
}

