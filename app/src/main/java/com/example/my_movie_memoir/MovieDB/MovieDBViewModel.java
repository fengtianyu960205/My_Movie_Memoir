package com.example.my_movie_memoir.MovieDB;

import android.app.Application;
import android.content.Context;
import android.graphics.Movie;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieDBViewModel extends ViewModel {

    private MovieRepository cRepository;
    private MutableLiveData<List<MovieInfo>> allMovieinfos;

    /*public MovieDBViewModel(@NonNull Application application){
        super(application);
        cRepository = new MovieRepository(application);
        allMovieinfos = cRepository.getAllMovies();

    }

    public void insert(MovieInfo movieinfo){
        cRepository.insert(movieinfo);
    }

    public void update(MovieInfo movieinfo){
        cRepository.update(movieinfo);
    }

    public void delete(MovieInfo movieinfo){
        cRepository.delete(movieinfo);
    }

    public void deleteAll(){
        cRepository.deleteAll();
    }
    public LiveData<List<MovieInfo>> getAll(){
        return allMovieinfos;
    }*/


    public MovieDBViewModel () {
        allMovieinfos=new MutableLiveData<>();
    }
    public void setMovieInfos(List<MovieInfo> movies) {
        allMovieinfos.setValue(movies);
    }
    public LiveData<List<MovieInfo>> getAllMovieInfos() {
        return cRepository.getAllMovies();
    }




    public void initalizeVars(Application application){
        cRepository = new MovieRepository(application);
    }
    public void insert(MovieInfo movie) {
        cRepository.insert(movie);
    }
    public void insertAll(MovieInfo... movies) {
        cRepository.insertAll(movies);
    }
    public void deleteAll() {
        cRepository.deleteAll();
    }
    public void delete(MovieInfo movie) {
        cRepository.delete(movie);
    }
    public void update(MovieInfo... movies) {
        cRepository.updateMovies(movies);
    }
    public MovieInfo insertAll(int id) {
        return cRepository.findByID(id);
    }
    public MovieInfo findByID(int movieINfoId){
        return cRepository.findByID(movieINfoId);
    }

    public MovieInfo findByMovie(int perId, String movieName, String releaseDate){
        return cRepository.findByMovie(perId,movieName,releaseDate);
    }

    public MovieInfo findByMovies(int perId, String movieName, String releaseDate) throws ExecutionException, InterruptedException {
        return cRepository.findByMovies(perId,movieName,releaseDate);
    }

}
