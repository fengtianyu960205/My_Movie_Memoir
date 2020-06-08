package com.example.my_movie_memoir.UI.MovieMemoir;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.my_movie_memoir.MovieDB.MovieInfo;
import com.example.my_movie_memoir.UI.Home.HomeFragmentViewModel;
import com.example.my_movie_memoir.entity.Memoir;
import com.example.my_movie_memoir.networking.NetworkConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieMemoirViewModel extends ViewModel {

    NetworkConnection networkConnection = new NetworkConnection();
    private MutableLiveData<ArrayList> getmemoir;
    private ArrayList<String[]> list  = new ArrayList<>();
    private String movieTitle;


    public MovieMemoirViewModel(){
        getmemoir = new MutableLiveData<>();
    }

    public LiveData<ArrayList> getMemoir(){
        return getmemoir;
    }

    public void setMovieInfo(ArrayList<String[]> movieInfo) {
        getmemoir.setValue(movieInfo);
    }


    public void getMemoirTask(int perId){
        MovieMemoirViewModel.GetMemoir getMemoirTask = new MovieMemoirViewModel.GetMemoir();
        getMemoirTask.execute(perId);
    }

    private class GetMemoir extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... params) {
            return networkConnection.getMemoirInfo(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONArray jsonArray = new JSONArray(result);
                String data = "";
                int length = jsonArray.length();
                for (int i = 0; i<length; i++ ){
                    JSONObject memoir  =  jsonArray.getJSONObject(i) ;
                     String  name = memoir.getString("movName");
                     String releaseDate = memoir.getString("movRelease");
                     String watchDate = memoir.getString("movWdate");
                     String comment = memoir.getString("comment");
                     Integer rate = memoir.getInt("rate");
                     String rateStr = rate.toString();
                    JSONObject cinema = memoir.getJSONObject("cinId");
                    String cinemaName = cinema.getString("cinName");
                    String cinemaSuburb = cinema.getString("cinSuburb");
                    String imageURL = "";
                    String id = "";
                    String[] info = {name,releaseDate,watchDate,comment,cinemaName,cinemaSuburb,imageURL,id,rateStr};
                    list.add(info);

                }

                getmemoir.setValue(list);


                if (length == 0) {
                    getmemoir.setValue(list);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                getmemoir.setValue(list);
            }
        }
    }


}
