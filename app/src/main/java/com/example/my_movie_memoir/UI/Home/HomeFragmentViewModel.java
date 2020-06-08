package com.example.my_movie_memoir.UI.Home;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.my_movie_memoir.Signin.entity.Person;
import com.example.my_movie_memoir.networking.NetworkConnection;
import com.example.my_movie_memoir.sp.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// the view model is to get top five movies from server
public class HomeFragmentViewModel extends ViewModel {
    NetworkConnection networkConnection = new NetworkConnection();
    private MutableLiveData<String> addMovie;

    public HomeFragmentViewModel(){
        addMovie = new MutableLiveData<>();
    }

    public LiveData<String>getMovie(){
        return addMovie;
    }


    public void getMovieTask(int perId){
        GetMovie getmovieTask = new GetMovie();
        getmovieTask.execute(perId);


    }

    private class GetMovie extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... params) {
            return networkConnection.getTopFiveMovie(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                String data = "";
                 int length = jsonArray.length();
                 for (int i = 0; i<length; i++ ){
                     JSONObject jsonObject  =  jsonArray.getJSONObject(i) ;
                     data += "Movie name: "+ jsonObject.getString("movieName") + " \n" + "Movie release date: "+jsonObject.getString("movRelease") + "\n"+ "Rate: " + jsonObject.getString("rate") + "\n\n";
                 }
                addMovie.setValue(data);


                if (length == 0) {
                    addMovie.setValue("No movie");
                }

            } catch (JSONException e) {
                e.printStackTrace();
                addMovie.setValue("No movie");
            }
        }
    }

}
