package com.example.my_movie_memoir.UI.MovieSearch;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.my_movie_memoir.R;
import com.example.my_movie_memoir.networking.NetworkConnection;
import com.example.my_movie_memoir.sp.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
// this model is for search movie information using movie name
public class MovieSearchViewModel extends ViewModel {

    NetworkConnection networkConnection = new NetworkConnection();
    private MutableLiveData<ArrayList> MovieInfo;
    private Context context;
    private ArrayList<String[]> list = new ArrayList<>();
    private ArrayList<String[]> list1 = new ArrayList<>();

    public MovieSearchViewModel(){
        MovieInfo = new MutableLiveData<>();
    }

    public LiveData<ArrayList> getMovieInfo(){
        return MovieInfo;
    }

    public void setMovieInfo(ArrayList<String[]> movieInfo) {
        MovieInfo.setValue(movieInfo);
    }

    public void getMovieInfoTask(String MovieName){
        GetMovieInfoTask getmovieTask = new GetMovieInfoTask();
        getmovieTask.execute(MovieName);
    }

    public void getOneMovieInfoTask(String MovieName){
        GetMovieImagesTask getmovieTask = new GetMovieImagesTask();
        getmovieTask.execute(MovieName);
    }

    public void getMoviesInfoTask(ArrayList<String> MovieNames){
        for (int i=0 ; i < MovieNames.size() ; i++){
            String movName = MovieNames.get(i);
            GetMovieImagesTask getmovieTask = new GetMovieImagesTask();
            getmovieTask.execute(movName);
        }
    }

    private class GetMovieInfoTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            return networkConnection.getMovieInfo(name);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                if (jsonArray != null && jsonArray.length() > 0){
                    for(int i = 0; i < jsonArray.length() ; i++){
                        JSONObject movieInfo = jsonArray.getJSONObject(i);
                        //Log.d("sign_in", "json: " + movieInfo);
                        String name = movieInfo.getString("title");
                        String year = movieInfo.getString("release_date");
                        Integer movieId = movieInfo.getInt("id");
                        String movieID = movieId.toString();
                        String image = movieInfo.getString("poster_path");
                        String[] movieinfo = {name,year,movieID,image};
                        list.add(movieinfo);
                    }

                }

                MovieInfo.setValue(list);
            } catch (Exception e) {
                MovieInfo.setValue(list);
            }
        }
    }


    private class GetMovieImagesTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            return networkConnection.getMovieInfo(name);
        }

        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                if (jsonArray != null && jsonArray.length() > 0){

                        JSONObject movieInfo = jsonArray.getJSONObject(0);
                        //Log.d("sign_in", "json: " + movieInfo);
                        String name = movieInfo.getString("title");
                        Integer movieId = movieInfo.getInt("id");
                        String movieID = movieId.toString();
                        String image = movieInfo.getString("poster_path");
                        String[] movieinfo = {name,movieID,image};
                        list1.add(movieinfo);

                }

                MovieInfo.setValue(list1);
            } catch (Exception e) {
                MovieInfo.setValue(list1);
            }
        }
    }




}
