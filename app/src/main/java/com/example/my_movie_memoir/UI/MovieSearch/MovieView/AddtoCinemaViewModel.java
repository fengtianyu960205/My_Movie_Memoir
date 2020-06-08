package com.example.my_movie_memoir.UI.MovieSearch.MovieView;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.my_movie_memoir.entity.Cinema;
import com.example.my_movie_memoir.entity.Memoir;
import com.example.my_movie_memoir.networking.NetworkConnection;

// this view model is to post cinema information to the server
public class AddtoCinemaViewModel extends ViewModel {

    NetworkConnection networkConnection = new NetworkConnection();

    private MutableLiveData<Integer> addCinemaResult;

    public AddtoCinemaViewModel() {
        addCinemaResult = new MutableLiveData<>();
    }

    public LiveData<Integer> getAddCinemaResult() {
        return addCinemaResult;
    }


    public void addTask(Cinema cinema){
        try {
            AddtoCinemaViewModel.AddCinemaTask credentialTask  = new AddtoCinemaViewModel.AddCinemaTask();
            credentialTask.execute(cinema);

        } catch (Exception e) {
            addCinemaResult.setValue(1);
        }
    }

    private class AddCinemaTask extends AsyncTask<Cinema, Void, String> {
        @Override
        protected String doInBackground(Cinema... params) {
            return networkConnection.addCinema(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("204"))
                addCinemaResult.setValue(0);
            else
                addCinemaResult.setValue(1);
            Log.d("sign_in",result);
        }
    }
}
