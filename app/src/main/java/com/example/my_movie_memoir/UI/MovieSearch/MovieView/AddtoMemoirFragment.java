package com.example.my_movie_memoir.UI.MovieSearch.MovieView;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.my_movie_memoir.R;
import com.example.my_movie_memoir.Signin.Signin;
import com.example.my_movie_memoir.Signin.entity.Credential;
import com.example.my_movie_memoir.Signin.entity.Person;
import com.example.my_movie_memoir.UI.Maps.GetCinemaAddressViewModel;
import com.example.my_movie_memoir.UI.Report.GetCinemaPerPostcodeViewModel;
import com.example.my_movie_memoir.entity.Cinema;
import com.example.my_movie_memoir.entity.Memoir;
import com.example.my_movie_memoir.sp.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddtoMemoirFragment extends Fragment {
    private MovieVIewVIewModel movieVIewVIewModel;
    private GetCinemaAddressViewModel getCinemaAddressViewModel;
    private addtoMemoirViewModel addtoMemoirViewModel;
    private TextView add_memoir_movieName;
    private TextView add_memoir_showName;
    private TextView add_memoir_movieName_releaseDate;
    private TextView add_memoir_showDate;
    private ImageView add_memoir_image;
    private TextView add_memoir_cinemaName_comment;
    private EditText show_memoir_cinemaName_comment;
    private TextView add_memoir_WatchTime;
    private EditText show_memoir_WatchTime;
    private TextView add_memoir_cinemaName;
    private Button add_memoir_btn,add_memoir_cinema_btn;
    private DatePicker date_picker_watchDate;
    private TimePicker time_picker_watchTime;
    private Spinner add_memoir_spinner;
    private Context context;
    private String watchDate;
    private String movieName;
    private String releaseDate;
    private Date release;
    private Date watchMovieDate;
    private int perId ;
    RequestOptions option;
    private ArrayList<String> cinemaAddress = new ArrayList<>();
    private EditText sp_cinemaName,sp_subrub,sp_postcode;
    private AddtoCinemaViewModel addtoCinemaViewModel;
    private RatingBar addtoMemoir_rateScore;
    private int myRating;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addtomemoir_fragment, container, false);

        add_memoir_showName = view.findViewById(R.id.add_memoir_showName);
        add_memoir_showDate = view.findViewById(R.id.add_memoir_showDate);
        add_memoir_image = view.findViewById(R.id.add_memoir_image);
        add_memoir_cinema_btn = view.findViewById(R.id.add_memoir_cinema_btn);
        add_memoir_cinemaName_comment = view.findViewById(R.id.add_memoir_cinemaName_comment);
        show_memoir_cinemaName_comment = view.findViewById(R.id.show_memoir_cinemaName_comment);
        add_memoir_cinemaName = view.findViewById(R.id.add_memoir_cinemaName);
        add_memoir_btn = view.findViewById(R.id.add_memoir_btn);
        add_memoir_WatchTime = view.findViewById(R.id.add_memoir_WatchTime);
        show_memoir_WatchTime = view.findViewById(R.id.show_memoir_WatchTime);
        date_picker_watchDate = view.findViewById(R.id.date_picker_watchDate);
        addtoMemoir_rateScore = view.findViewById(R.id.addtoMemoir_rateScore);
        sp_cinemaName = view.findViewById(R.id.sp_cinemaName);
        sp_subrub = view.findViewById(R.id.sp_subrub);
        sp_postcode = view.findViewById(R.id.sp_postcode);
        final Spinner add_memoir_spinner = view.findViewById(R.id.add_memoir_spinner);
        movieVIewVIewModel = new ViewModelProvider(this).get(MovieVIewVIewModel.class);
        addtoMemoirViewModel = new ViewModelProvider(this).get(addtoMemoirViewModel.class);
        getCinemaAddressViewModel = new ViewModelProvider(this).get(GetCinemaAddressViewModel.class);
        addtoCinemaViewModel = new ViewModelProvider(this).get(AddtoCinemaViewModel.class);
        context = getActivity();
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading).error(R.drawable.loading);
        SharedPreferenceUtil sp = SharedPreferenceUtil.getInstance(context);

        perId = sp.getInt("perId");
        movieVIewVIewModel.getMovieDetaliTask();
        movieVIewVIewModel.getMovieInfo().observe(getViewLifecycleOwner(), new Observer<String[]>() {
            @Override
            public void onChanged(String[] movie) {

                movieName = movie[0];
                releaseDate = movie[1];
                add_memoir_showName.setText(movie[0]);
                add_memoir_showDate.setText(movie[1]);
                String url = "https://image.tmdb.org/t/p/w92" + movie[5];
                Glide.with(context).load(url).apply(option).into(add_memoir_image);

            }
        });

        date_picker_watchDate.init(1990, 8, 8, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                watchDate = sdf.format(c);
                Toast.makeText(getContext(),
                        "Date: " + watchDate,
                        Toast.LENGTH_SHORT).show();
            }
        });


        addtoMemoir_rateScore.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int ratInt = (int) rating;
                switch(ratInt){
                    case 1:
                        myRating = 1;
                        break;
                    case 2:
                        myRating = 2;
                        break;
                    case 3:
                        myRating = 3;
                        break;
                    case 4:
                        myRating = 4;
                        break;
                    case 5:
                        myRating = 5;
                        break;


                }
            }
        });

        add_memoir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Memoir memoir = initMemoir(10000, perId);
                addtoMemoirViewModel.addTask(memoir);
            }
        });

        addtoMemoirViewModel.getAddMemoirResult().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                if (integer == 0) {
                    Toast.makeText(getContext(),
                            "Add memoir succeed",
                            Toast.LENGTH_SHORT).show();
                } else if (integer == 1) {
                    Toast.makeText(getContext(),
                            "Add memoir failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });



        getCinemaAddressViewModel.getCinemaAddressTask();
        getCinemaAddressViewModel.getAddress().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
                    @Override
                    public void onChanged(ArrayList<String> strings) {
                        final ArrayAdapter<String> spinnerAdapter = new
                                ArrayAdapter<String>(context ,android.R.layout.simple_spinner_item, strings);
                        add_memoir_spinner.setAdapter(spinnerAdapter);
                    }
                });

        add_memoir_cinema_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cinema cinema = initCinema();
                addtoCinemaViewModel.addTask(cinema);
            }
        });

        addtoCinemaViewModel.getAddCinemaResult().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                if (integer == 0) {
                    Toast.makeText(getContext(),
                            "Add memoir succeed",
                            Toast.LENGTH_SHORT).show();
                    getCinemaAddressViewModel.getCinemaAddressTask();
                } else if (integer == 1) {
                    Toast.makeText(getContext(),
                            "Add memoir failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
    public Memoir initMemoir ( int cinId, int perId )
    {
        int id = getuId();

        watchDate = watchDate + " " + show_memoir_WatchTime.getText().toString().trim();

        try{ release = new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate);}
        catch (Exception e){}

        try{ watchMovieDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(watchDate);}
        catch (Exception e){}

        Memoir memoir = new Memoir(id,movieName ,release , watchMovieDate, show_memoir_cinemaName_comment.getText().toString().trim(), myRating);
        memoir.setCinId(cinId);
        memoir.setPerID(perId);
        return memoir;
    }

    public Cinema initCinema ()
    {
        int id = getuId();

        String cinemaname = sp_cinemaName.getText().toString().trim();
        String suburb = sp_subrub.getText().toString().trim();
        String postcode = sp_postcode.getText().toString().trim();
        Integer postcodeInt = Integer.parseInt(postcode);

       Cinema cinema = new Cinema(id,cinemaname,suburb,postcodeInt);
        return cinema;
    }

    public int getuId(){
        int min = 10000;
        int max = 100000;
        int id = (int)(Math.random() * (max - min + 1) + min);
        return id;
    }
}

