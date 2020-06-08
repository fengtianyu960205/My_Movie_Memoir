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
import org.json.JSONObject;

// this is the view model to get OMTB api information
public class MovieViewFromOMDBViewModel extends ViewModel {

    private MutableLiveData<String[]> MovieViewInfoDetail;
    private Context context;


    NetworkConnection networkConnection = new NetworkConnection();

    public MovieViewFromOMDBViewModel(){
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
        String movieName = sp.getString("movieName");
        MovieViewFromOMDBViewModel.GetMovieDetail getMovieDetail = new MovieViewFromOMDBViewModel.GetMovieDetail();
        getMovieDetail.execute(movieName);

    }

    private class GetMovieDetail extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return networkConnection.getMovieInfoFromomdb(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject jsonObject = new JSONObject(result);

                Log.d("sign_in", "json: " + jsonObject);
                String director = jsonObject.getString("Director");
                String cast = jsonObject.getString("Actors");
                String Rate = jsonObject.getString("imdbRating");

                String[] movieDetail = {director,cast,Rate};
                MovieViewInfoDetail.setValue(movieDetail);
            } catch (Exception e) {
                String [] movieDetail = {"failed"};
                MovieViewInfoDetail.setValue(movieDetail);
            }
        }
    }
}
