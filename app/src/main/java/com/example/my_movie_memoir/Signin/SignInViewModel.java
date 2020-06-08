package com.example.my_movie_memoir.Signin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.my_movie_memoir.R;
import com.example.my_movie_memoir.Signin.entity.Credential;
import com.example.my_movie_memoir.Signin.entity.Person;
import com.example.my_movie_memoir.networking.NetworkConnection;
import com.example.my_movie_memoir.sp.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// the view model get person information from the server if user sign in successfully
public class SignInViewModel extends ViewModel {

    NetworkConnection networkConnection = new NetworkConnection();


    private MutableLiveData<Integer> signInResult;
    private Context context;

    public SignInViewModel() {
        signInResult = new MutableLiveData<>();
        signInResult.setValue(99);
    }

    public void initContext(Context context) {
        this.context = context;
    }

    public void singInProcess(String username, String password) {
        try {
            GetCredentialTask getCredentialTask = new GetCredentialTask();
            getCredentialTask.execute(username, password);
        } catch (Exception e) {
            signInResult.setValue(1);
        }
    }


    public LiveData<Integer> getSignInResult() {
        return signInResult;
    }


    // the async task get person and add person information to the sharedpreference
    private class GetCredentialTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return networkConnection.getCredential(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                JSONObject   jsonObject  =  jsonArray.getJSONObject(0) ;
                Person currentUser =
                        new Person(
                                jsonObject.getInt("perId"),
                                jsonObject.getString("perFn"),
                                jsonObject.getString("perLn"),
                                jsonObject.getString("gender"),
                                jsonObject.getString("dob"),
                                jsonObject.getString("address"),
                                jsonObject.getString("perState"),
                                jsonObject.getInt("perPostcode")
                        );
                SharedPreferenceUtil sp = SharedPreferenceUtil.getInstance(context);
                sp.putInt("perId", currentUser.getperId());
                sp.putString("perFn", currentUser.getperFn());
                sp.putString("perLn", currentUser.getperLn());
                sp.putString("gender", currentUser.getGender());
                sp.putString("dob", currentUser.getDob());
                sp.putString("address", currentUser.getAddress());
                sp.putString("perState", currentUser.getperState());
                sp.putInt("perPostcode", currentUser.getperPostcode());
                if (currentUser.getperFn() != null) {
                    signInResult.setValue(0);
                }
                if (jsonObject.getString("perFn").length() > 0){
                    signInResult.setValue(0);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                signInResult.setValue(1);
            }
        }
    }




}
