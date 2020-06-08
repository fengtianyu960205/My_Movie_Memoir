package com.example.my_movie_memoir.Signin.Signup;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.my_movie_memoir.Signin.entity.Credential;
import com.example.my_movie_memoir.Signin.entity.Person;
import com.example.my_movie_memoir.networking.NetworkConnection;


// the view model to post credential and person to the server
public class SignupViewModel extends ViewModel {
    NetworkConnection networkConnection = new NetworkConnection();
    Person person = new Person(0);
    private MutableLiveData<Integer> addPersonResult;

    public SignupViewModel() {
        addPersonResult = new MutableLiveData<>();
    }

    public LiveData<Integer> getAddPersonResult() {
        return addPersonResult;
    }


    public void singUpProcessing(Person person,Credential credential){
        try {
            AddCredentialTask credentialTask  = new AddCredentialTask ();
            credentialTask.execute(credential);
            AddPersonTask addPersonTask = new AddPersonTask();
            addPersonTask.execute(person);
        } catch (Exception e) {
            addPersonResult.setValue(1);
        }
    }

    // the Async task to call networkconnection to post person to the server
    private class AddPersonTask extends AsyncTask<Person, Void, String> {
        @Override
        protected String doInBackground(Person... params) {
            return networkConnection.addPerson(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            // if success return 0 not success return 1
            if (result.equals("204"))
                addPersonResult.setValue(0);
            else
                addPersonResult.setValue(1);
            Log.d("sign_in",result);
        }
    }

    // the Async task to call networkconnection to post credential to the server
    private class AddCredentialTask extends AsyncTask<Credential, Void, String> {
        @Override
        protected String doInBackground(Credential... params) {
            return networkConnection.addCredential(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("204"))
                addPersonResult.setValue(0);
            else
                addPersonResult.setValue(1);
            Log.d("sign_in",result);
        }
    }


}
