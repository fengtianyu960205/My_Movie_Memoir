package com.example.my_movie_memoir.UI.MovieSearch.MovieView;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.my_movie_memoir.networking.NetworkConnection;
import com.example.my_movie_memoir.sp.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

// this is the view model to get movie infomration from tmdb api
public class MovieVIewVIewModel extends ViewModel {

    private MutableLiveData<String[]> MovieViewInfoDetail;
    private Context context;


    NetworkConnection networkConnection = new NetworkConnection();

    public MovieVIewVIewModel(){
        MovieViewInfoDetail = new MutableLiveData<>();
    }

    public LiveData<String[]> getMovieInfo(){
        return MovieViewInfoDetail;
    }

    public void setMovieInfo(String[] movieViewInfo) {
        MovieViewInfoDetail.setValue(movieViewInfo);
    }


    public void getMovieDetaliTask(){

        SharedPreferenceUtil sp = SharedPreferenceUtil.getInstance(context);
        int movieid = sp.getInt("movieId");
        GetMovieDetail getMovieDetail = new GetMovieDetail();
        getMovieDetail.execute(movieid);

    }

    private class GetMovieDetail extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... params) {
            return networkConnection.getMovieInfoDetail(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArrayGenres = jsonObject.getJSONArray("genres");


                Log.d("sign_in", "json: " + jsonObject);
                String name = jsonObject.getString("title");
                String year = jsonObject.getString("release_date");
                String genres = "";
                String country = jsonObject.getJSONArray("production_countries").getJSONObject(0).getString("name");
                String plot = jsonObject.getString("overview");
                String imageURL = jsonObject.getString("poster_path");
                Integer movieId = jsonObject.getInt("id");
                String movieID = movieId.toString();


                for (int i = 0; i < jsonArrayGenres.length(); i++) {
                    JSONObject Genres = jsonArrayGenres.getJSONObject(i);
                    genres += Genres.getString("name") + "  ";
                }

                String[] movieDetail = {name, year, genres, country, plot,imageURL,movieID};
                MovieViewInfoDetail.setValue(movieDetail);
            } catch (Exception e) {
                String [] movieDetail = {"failed"};
                MovieViewInfoDetail.setValue(movieDetail);
            }
        }
    }
}
