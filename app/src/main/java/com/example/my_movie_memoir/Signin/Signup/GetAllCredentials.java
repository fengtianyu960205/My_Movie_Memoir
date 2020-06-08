package com.example.my_movie_memoir.Signin.Signup;

import android.os.AsyncTask;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.my_movie_memoir.UI.Maps.GetCinemaAddressViewModel;
import com.example.my_movie_memoir.networking.NetworkConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// the view model to get all credential information
public class GetAllCredentials extends ViewModel {

    NetworkConnection networkConnection = new NetworkConnection();
    private MutableLiveData<ArrayList<String>> getCre;
    private ArrayList<String> allCrenditals = new ArrayList<>() ;



    public GetAllCredentials(){
        getCre = new MutableLiveData<>();
    }

    public LiveData<ArrayList<String>> getAllCreInfo(){
        return getCre;
    }

    public void setAllCre(ArrayList<String> info) {
        getCre.setValue(info);
    }


    public void getCreTask(){
        GetAllCredentials.GetCre getCretask = new GetAllCredentials.GetCre();
        getCretask.execute();
    }

    // the Async task to call networkconnection to get all the credentials
    private class GetCre extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            return networkConnection.getAllCre();
        }

        @Override
        protected void onPostExecute(String result) {
            ArrayList<String> allcres = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0 ; i <jsonArray.length() ; i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    // Log.d("sign_in", "json: " + object);

                    String email = object.getString("username");
                    allcres.add(email);

                }
                getCre.setValue(allcres);


            } catch (JSONException e) {
                e.printStackTrace();
                getCre.setValue(allcres);
            }
        }
    }
}
