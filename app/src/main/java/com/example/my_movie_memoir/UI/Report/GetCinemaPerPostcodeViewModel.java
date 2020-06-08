package com.example.my_movie_memoir.UI.Report;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.my_movie_memoir.UI.Maps.GetPersonAddressViewModel;
import com.example.my_movie_memoir.networking.NetworkConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

// this view model is to get cinema per postcode from the server
public class GetCinemaPerPostcodeViewModel extends ViewModel {

    NetworkConnection networkConnection = new NetworkConnection();
    private MutableLiveData<ArrayList<Integer[]>> getCinemaPerPostcode;




    public GetCinemaPerPostcodeViewModel(){
        getCinemaPerPostcode = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Integer[]>> getCinemaPerPostcode(){
        return getCinemaPerPostcode;
    }

    public void setCinemaInfo(ArrayList<Integer[]> cinemas) {
        getCinemaPerPostcode.setValue(cinemas);
    }


    public void setGetCinemaPerPostcodeTask(int perId, String startDate, String endDate){
        GetCinemaPerPostcodeViewModel.GetCinemaPerPostcode getCinemaPerPostcode= new GetCinemaPerPostcodeViewModel.GetCinemaPerPostcode();
        MyTaskParams params = new MyTaskParams(perId,startDate,endDate);
        getCinemaPerPostcode.execute(params);
    }

    public static class MyTaskParams{
        int perId;
        String startDate;
        String endDate;

        MyTaskParams (int perId,String startDate,String endDate){
            this.perId = perId;
            this.endDate = endDate;
            this.startDate = startDate;
        }
    }

    private class GetCinemaPerPostcode extends AsyncTask<MyTaskParams,Void, String> {
        @Override
        protected String doInBackground(MyTaskParams... params) {
            int perId = params[0].perId;
            String endDate = params[0].endDate;
            String startDate = params[0].startDate;

            return networkConnection.getCinemaPerPostcode(perId,startDate,endDate);
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
                    Integer postcode = object.getInt("cinPostcode");
                    Integer countInt = count.intValue();
                    Integer[] temp = {postcode,countInt};
                    list.add(temp);
                }
                getCinemaPerPostcode.setValue(list);




            } catch (JSONException e) {
                e.printStackTrace();
                getCinemaPerPostcode.setValue(list);
            }
        }
    }

}