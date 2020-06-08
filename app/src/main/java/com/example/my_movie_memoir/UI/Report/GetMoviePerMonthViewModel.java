package com.example.my_movie_memoir.UI.Report;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.my_movie_memoir.networking.NetworkConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// this viewmodel is to get movie per month from server
public class GetMoviePerMonthViewModel extends ViewModel {

    NetworkConnection networkConnection = new NetworkConnection();
    private MutableLiveData<ArrayList<Integer[]>> getMoviePerMonth;

    public GetMoviePerMonthViewModel(){
        getMoviePerMonth = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Integer[]>> getMoviePerMonth(){
        return getMoviePerMonth;
    }

    public void setMoviePerMonth(ArrayList<Integer[]> cinemas) {
        getMoviePerMonth.setValue(cinemas);
    }


    public void setCinemaPerMonthTask(int perId, String year){
        GetMoviePerMonthViewModel.GetMovies movies= new GetMoviePerMonthViewModel.GetMovies();
        GetMoviePerMonthViewModel.MyTaskParams params = new GetMoviePerMonthViewModel.MyTaskParams(perId,year);
        movies.execute(params);
    }

    public static class MyTaskParams{
        int perId;
        String year;


        MyTaskParams (int perId,String year){
            this.perId = perId;
            this.year = year;

        }
    }

    private class GetMovies extends AsyncTask<GetMoviePerMonthViewModel.MyTaskParams,Void, String> {
        @Override
        protected String doInBackground(GetMoviePerMonthViewModel.MyTaskParams... params) {
            int perId = params[0].perId;
            String year = params[0].year;


            return networkConnection.getMoviePerMonth(perId,year);
        }

        @Override
        protected void onPostExecute(String result) {
            ArrayList<Integer[]> list = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i <jsonArray.length(); i++ ) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Log.d("sign_in", "json: " + object);

                    Long count = object.getLong("count");
                    String month = object.getString("Month");
                    int monthInt = Integer.parseInt(month);
                    Integer countInt = count.intValue();
                    Integer[] temp = {monthInt,countInt};
                    list.add(temp);
                }
                getMoviePerMonth.setValue(list);

            } catch (JSONException e) {
                e.printStackTrace();
                getMoviePerMonth.setValue(list);
            }
        }
    }
}
