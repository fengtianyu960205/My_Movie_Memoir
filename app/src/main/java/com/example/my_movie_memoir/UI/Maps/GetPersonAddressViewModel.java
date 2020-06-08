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

// the view model is to get all person address
public class GetPersonAddressViewModel extends ViewModel {

    NetworkConnection networkConnection = new NetworkConnection();
    private MutableLiveData<String> getAddress;
    private String personAddress ;



    public GetPersonAddressViewModel(){
        getAddress = new MutableLiveData<>();
    }

    public LiveData<String> getAddress(){
        return getAddress;
    }

    public void setAddressInfo(String address) {
        getAddress.setValue(address);
    }


    public void getPersonAddressTask(int perId){
        GetPersonAddressViewModel.GetPersonAddress getPersonAddressTask = new GetPersonAddressViewModel.GetPersonAddress();
        getPersonAddressTask.execute(perId);
    }

    private class GetPersonAddress extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... params) {
            return networkConnection.getPersonInfo(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONArray jsonArray = new JSONArray(result);
                JSONObject object  =  jsonArray.getJSONObject(0);

                String temp = "";
                String address = object.getString("address");
                String state = object.getString("perState");
                Integer postcode = object.getInt("perPostcode");
                String postCode = postcode.toString();
                temp = address + "" + postcode +" " + state;
                personAddress = temp;


                getAddress.setValue(temp);


            } catch (JSONException e) {
                e.printStackTrace();
                getAddress.setValue("wrong");
            }
        }
    }


}
