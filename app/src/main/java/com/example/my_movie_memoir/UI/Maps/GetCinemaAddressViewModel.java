package com.example.my_movie_memoir.UI.Maps;

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

// the view model is to get all cinema address
public class GetCinemaAddressViewModel extends ViewModel {

    NetworkConnection networkConnection = new NetworkConnection();
    private MutableLiveData<ArrayList<String>> getAddress;
    private ArrayList<String> cinemaAddresses = new ArrayList<>() ;



    public GetCinemaAddressViewModel(){
        getAddress = new MutableLiveData<>();
    }

    public LiveData<ArrayList<String>> getAddress(){
        return getAddress;
    }

    public void setAddressInfo(ArrayList<String> address) {
        getAddress.setValue(address);
    }


    public void getCinemaAddressTask(){
        GetCinemaAddressViewModel.GetCinemaAddress getCinemaAddressTask = new GetCinemaAddressViewModel.GetCinemaAddress();
        getCinemaAddressTask.execute();
    }

    private class GetCinemaAddress extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            return networkConnection.getCinemaInfo();
        }

        @Override
        protected void onPostExecute(String result) {
            ArrayList<String> cinemaInfos = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0 ; i <jsonArray.length() ; i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String temp = "";
                    String cinemaName = object.getString("cinName");
                    String suburb = object.getString("cinSuburb");
                    Integer temps = object.getInt("cinPostcode");
                    String postcode = temps.toString();
                    temp = cinemaName + " " + suburb + " " + postcode ;
                    cinemaInfos.add(temp);

                }
                getAddress.setValue(cinemaInfos);


            } catch (JSONException e) {
                e.printStackTrace();
                getAddress.setValue(cinemaInfos);
            }
        }
    }
}
