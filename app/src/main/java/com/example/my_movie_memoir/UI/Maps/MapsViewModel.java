package com.example.my_movie_memoir.UI.Maps;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.my_movie_memoir.networking.NetworkConnection;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


// the view model is to convert the address to latitude and long titude
public class MapsViewModel extends ViewModel {

    NetworkConnection networkConnection = new NetworkConnection();
    private MutableLiveData<List<LatLng>> liveDataAddList;
    private Context context;
    private List<LatLng> addList = new ArrayList<LatLng>();

    public MapsViewModel() {
        liveDataAddList = new MutableLiveData<>();
    }

    public void init(Context context){
        this.context = context;
        this.addList = new ArrayList<>();
    }

    public LiveData<List<LatLng>> getLiveDataAddList() {
        return liveDataAddList;
    }

    /*public void getAddProcessing(String address){
        try {
            GetAddTask getTask = new GetAddTask();
            getTask.execute(address);

        } catch (Exception e) {
            Log.e("MapView","getAddProcessing error");
        }
    }

    private class GetAddTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return networkConnection.getLatAndLng(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray resultArray = jsonObject.getJSONArray("results");
                if (resultArray.length()>0){
                    JSONObject object  =  resultArray.getJSONObject(0);
                    JSONObject firstResult = object.getJSONObject("geometry");
                    Log.d("sign_in", "json: " + firstResult);
                    double lat = firstResult.getDouble("lat");
                    double lng = firstResult.getDouble("lng");
                    LatLng latLng = new LatLng(lat,lng);
                    Log.i("mapModelView",latLng.latitude + " " + latLng.longitude);
                    addList.add(latLng);
                    liveDataAddList.setValue(addList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }*/

    public void getAddCinemaProcessing(ArrayList<String> cinemas){
        try {
            for (String temp : cinemas) {
                GetAddCinemaTask getTask = new GetAddCinemaTask();
                getTask.execute(temp);
            }

        } catch (Exception e) {
            Log.e("MapView","getAddProcessing error");
        }
    }

    private class GetAddCinemaTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return networkConnection.getLatAndLng(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray resultArray = jsonObject.getJSONArray("results");
                if (resultArray.length()>0){

                    JSONObject object  =  resultArray.getJSONObject(0);
                     Log.d("sign_up", "json: " + object);
                    JSONObject firstResult = object.getJSONObject("geometry");
                    Log.d("sign_in", "json: " + firstResult);
                    double lat = firstResult.getDouble("lat");
                    double lng = firstResult.getDouble("lng");
                    LatLng latLng = new LatLng(lat,lng);
                    addList.add(latLng);
                    liveDataAddList.setValue(addList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}
