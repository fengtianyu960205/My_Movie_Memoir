package com.example.my_movie_memoir.MovieDB;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MovieRepository {

     private DAO dao;
    private LiveData<List<MovieInfo>> allmovieInfo;
    private MovieInfo movieInfo;


    public MovieRepository(Application application){
        MovieDB db = MovieDB.getInstance(application);
        dao=db.DAO();

    }
    public LiveData<List<MovieInfo>> getAllMovies() {
        allmovieInfo=dao.getAll();
       return allmovieInfo;
    }

   /* public void insert(MovieInfo movieInfo){
        new insertMovieAsyncTask(dao).execute(movieInfo);
    }

    public void update(MovieInfo movieInfo){
        new updateMovieAsyncTask(dao).execute(movieInfo);
    }

    public void delete(MovieInfo movieInfo){
        new deleteMovieAsyncTask(dao).execute(movieInfo);
    }

    public void deleteAll(){
        new deleteAllMovieAsyncTask(dao).execute();
    }*/
    public void insert(final MovieInfo movie){
        MovieDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(movie);
            }
        });
    }
    public void deleteAll(){
        MovieDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteAll();
            }
        });
    }
    public void delete(final MovieInfo movie){
        MovieDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(movie);
            }
        });
    }
    public void insertAll(final MovieInfo... movies){
        MovieDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertAll(movies);
            }
        });
    }
    public void updateMovies(final MovieInfo... movies){
        MovieDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.updateMovies(movies);
            }
        });
    }
     public MovieInfo findByID(final int moviesId){
        MovieDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                MovieInfo runMovie= dao.findByID(moviesId);
                setMovieInfo(runMovie);
            }
        });
        return movieInfo;
    }

    public MovieInfo findByMovie(final int perId, final String MovieName , final String releaseDate){
        MovieDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                MovieInfo runMovie= dao.findByMovie(perId,MovieName,releaseDate);
                setMovieInfo(runMovie);
            }
        });
        return movieInfo;
    }
    public void setMovieInfo(MovieInfo movie){
        this.movieInfo=movie;
    }

    public MovieInfo findByMovies(final int perId, final String MovieName , final String releaseDate) throws ExecutionException, InterruptedException {
        Future future = MovieDB.databaseWriteExecutor.submit(new Runnable() {
            @Override
            public void run() {
                MovieInfo runMovie = dao.findByMovie(perId, MovieName, releaseDate);
                setMovieInfo(runMovie);
            }
        });
        return (MovieInfo)future.get();
    }

   /* private static class insertMovieAsyncTask extends AsyncTask<MovieInfo , Void, Void>{
        private DAO dao;

        private insertMovieAsyncTask(DAO dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MovieInfo... movieInfos) {
            dao.insert(movieInfos[0]);
            return null;
        }
    }
    private static class updateMovieAsyncTask extends AsyncTask<MovieInfo , Void, Void>{
        private DAO dao;

        private updateMovieAsyncTask(DAO dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MovieInfo... movieInfos) {
            dao.updateMovies(movieInfos[0]);
            return null;
        }
    }
    private static class deleteMovieAsyncTask extends AsyncTask<MovieInfo , Void, Void>{
        private DAO dao;

        private deleteMovieAsyncTask(DAO dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MovieInfo... movieInfos) {
            dao.delete(movieInfos[0]);
            return null;
        }
    }

    private static class deleteAllMovieAsyncTask extends AsyncTask<Void , Void, Void>{
        private DAO dao;

        private deleteAllMovieAsyncTask(DAO dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }*/
}
