package com.example.my_movie_memoir.UI.MovieSearch.MovieView;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.my_movie_memoir.Signin.Signup.SignupViewModel;
import com.example.my_movie_memoir.Signin.entity.Credential;
import com.example.my_movie_memoir.Signin.entity.Person;
import com.example.my_movie_memoir.entity.Memoir;
import com.example.my_movie_memoir.networking.NetworkConnection;


// this view model is to post memoir information to the server
public class addtoMemoirViewModel extends ViewModel {

    NetworkConnection networkConnection = new NetworkConnection();

    private MutableLiveData<Integer> addMemoirResult;

    public addtoMemoirViewModel() {
        addMemoirResult = new MutableLiveData<>();
    }

    public LiveData<Integer> getAddMemoirResult() {
        return addMemoirResult;
    }


    public void addTask(Memoir memoir){
        try {
            addtoMemoirViewModel.AddMemoirTask credentialTask  = new addtoMemoirViewModel.AddMemoirTask();
            credentialTask.execute(memoir);

        } catch (Exception e) {
            addMemoirResult.setValue(1);
        }
    }

    private class AddMemoirTask extends AsyncTask<Memoir, Void, String> {
        @Override
        protected String doInBackground(Memoir... params) {
            return networkConnection.addMemoir(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("204"))
                addMemoirResult.setValue(0);
            else
                addMemoirResult.setValue(1);
            Log.d("sign_in",result);
        }
    }





}
