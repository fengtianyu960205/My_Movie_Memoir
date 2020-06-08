package com.example.my_movie_memoir.networking;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.my_movie_memoir.Signin.entity.Credential;
import com.example.my_movie_memoir.Signin.entity.Person;
import com.example.my_movie_memoir.entity.Cinema;
import com.example.my_movie_memoir.entity.Memoir;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkConnection {
    private OkHttpClient client = null;
    public static final MediaType JSON =
            MediaType.parse("application/json; charset=utf-8");

    public NetworkConnection() {
        client = new OkHttpClient();
    }

    private static final String BASE_URL =
            "http://192.168.0.76:45279/MyMovieMemoir/webresources/";

    private static final String GetMovieInfoBASE_URL =
            "https://api.themoviedb.org/3/search/movie?api_key=f28d7f8bf30f1496bf9e82d740f1b3d0&language=en-US&query=";
    private static final String GetMovieInfobackName = "&page=1&include_adult=false";

    private static final String GetMovieInfoDetailBASE_URL =
            "https://api.themoviedb.org/3/movie/";
    private static final String GetMovieInfoDetailbackName = "?api_key=f28d7f8bf30f1496bf9e82d740f1b3d0&language=en-US";

    // use password and username to get person object
    public String getCredential(String username, String password) {
        final String methodPath = "mymoviememoir.person/findByUsernameAndPasswordHash/" + username + "/" + getSHA256StrJava(password);
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        String results = "";
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    // post person to the server
    public String addPerson(Person person) {
        Gson gson = new Gson();
        String personJson = gson.toJson(person);
        String results = "";
        final String methodPath = "mymoviememoir.person";
        RequestBody body = RequestBody.create(personJson, JSON);
        Log.d("sign_in", "json: " + personJson);

        Request request = new Request.Builder()
                .url(BASE_URL + methodPath)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 204) {
                return "204";
            }
            results = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    //post credential to the server
    public String addCredential(Credential credential) {
        Gson gson = new Gson();
        String personJson = gson.toJson(credential);
        String results = "";
        final String methodPath = "mymoviememoir.credential";
        RequestBody body = RequestBody.create(personJson, JSON);
        Log.d("sign_in", "json: " + personJson);

        Request request = new Request.Builder()
                .url(BASE_URL + methodPath)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 204) {
                return "204";
            }
            results = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    // post memoir to the server
    public String addMemoir(Memoir memoir) {
        Gson gson = new Gson();
        String personJson = gson.toJson(memoir);
        String results = "";
        final String methodPath = "mymoviememoir.memoir";
        RequestBody body = RequestBody.create(personJson, JSON);
        Log.d("sign_in", "json: " + personJson);

        Request request = new Request.Builder()
                .url(BASE_URL + methodPath)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 204) {
                return "204";
            }
            results = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    // post cinema to the server
    public String addCinema(Cinema cinema) {
        Gson gson = new Gson();
        String personJson = gson.toJson(cinema);
        String results = "";
        final String methodPath = "mymoviememoir.cinema";
        RequestBody body = RequestBody.create(personJson, JSON);
        Log.d("sign_in", "json: " + personJson);

        Request request = new Request.Builder()
                .url(BASE_URL + methodPath)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 204) {
                return "204";
            }
            results = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    // get movie information from tmdb api using movie name
    public String getMovieInfo(String MovieName) {
        final String methodPath = GetMovieInfoBASE_URL + MovieName + GetMovieInfobackName;
        Request.Builder builder = new Request.Builder();
        builder.url(methodPath);
        Request request = builder.build();
        String results = "";
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    // get movie detail from tmdb using movie id
    public String getMovieInfoDetail(Integer MovieID) {
        final String methodPath = GetMovieInfoDetailBASE_URL + MovieID + GetMovieInfoDetailbackName;
        Request.Builder builder = new Request.Builder();
        builder.url(methodPath);
        Request request = builder.build();
        String results = "";
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    // get moive
    public Bitmap getMoviePoster(String image) {
        final String methodPath = "http://image.tmdb.org/t/p/w92/" + image;
        Request.Builder builder = new Request.Builder();
        builder.url(methodPath);
        Request request = builder.build();
//        InputStream results = null;
        Bitmap bitmap = null;
        try {
            Response response = client.newCall(request).execute();
            InputStream results = Objects.requireNonNull(response.body()).byteStream();
            bitmap = BitmapFactory.decodeStream(results);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    // use SHA256 technology to hash password
    private String getSHA256StrJava(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }


    private String byte2Hex(byte[] bytes) {
        StringBuilder stringBuffer = new StringBuilder();
        String temp = null;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    private Date convertToDate(String str) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // get top five movie form server
    public String getTopFiveMovie(int perId) {
        final String methodPath = "mymoviememoir.memoir/findMovie/" + perId ;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        String results = "";
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    // get memoir information from the server
    public String getMemoirInfo(int perId) {
        final String methodPath = "mymoviememoir.memoir/findByperId/" + perId ;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        String results = "";
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    // get longtitude and latitude from opencagedata api
    public String getLatAndLng(String address) {
        final String methodPath = "http://api.opencagedata.com/geocode/v1/json?q=" + address + "&key=01f22e6351ba44169c1a4ca92a4830b2";
        Request.Builder builder = new Request.Builder();
        builder.url(methodPath);
        Request request = builder.build();
        String results = "";
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    // get all person information from the server
    public String getPersonInfo(int perId) {
        final String methodPath = "mymoviememoir.person/findByPerIds/" + perId ;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        String results = "";
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    // get all cinemas information from the server
    public String getCinemaInfo() {
        final String methodPath = "mymoviememoir.cinema/findByAll";
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        String results = "";
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    // get cinema per postcode using start date and endd ate
    public String getCinemaPerPostcode(int perId, String startDate , String endDate ) {
        final String methodPath = "mymoviememoir.memoir/taska/" + perId +"/"+ startDate +"/"+ endDate ;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        String results = "";
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    // get movie per month for a specific year
    public String getMoviePerMonth(int perId, String year ) {
        final String methodPath = "mymoviememoir.memoir/taskb/" + perId +"/"+ year ;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        String results = "";
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    // get movie information from omdb
    public String getMovieInfoFromomdb(String moviename) {
        final String methodPath = "http://www.omdbapi.com/?t=" + moviename + "&apikey=99a6556c";
        Request.Builder builder = new Request.Builder();
        builder.url(methodPath);
        Request request = builder.build();
        String results = "";
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    // get all credential from the server
    public String getAllCre() {
        final String methodPath = "mymoviememoir.credential/findAllCre";
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        String results = "";
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}
