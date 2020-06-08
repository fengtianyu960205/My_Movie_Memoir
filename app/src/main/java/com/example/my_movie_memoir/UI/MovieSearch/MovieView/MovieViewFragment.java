package com.example.my_movie_memoir.UI.MovieSearch.MovieView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.my_movie_memoir.MainActivity;
import com.example.my_movie_memoir.MovieDB.MovieDB;
import com.example.my_movie_memoir.MovieDB.MovieDBViewModel;
import com.example.my_movie_memoir.MovieDB.MovieInfo;
import com.example.my_movie_memoir.R;
import com.example.my_movie_memoir.UI.WatchList.WatchListFragment;
import com.example.my_movie_memoir.sp.SharedPreferenceUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MovieViewFragment extends Fragment {

    private MovieVIewVIewModel movieVIewVIewModel;
    private TextView ActualMovieName;
    private TextView ActualReleaseDate;
    private TextView ActualMovieGenre;
    private TextView ViewMovieCast;
    private TextView ActualMovieCast;
    private TextView ActualMovieCountry;
    private TextView ViewMoviePlot,Director,show_directors;
    private TextView ActualMoviePlot;
    private Button addToMemoir_btn;
    private Button addWatchList_btn;
    private TextView Score;
    private ImageView ViewMovieImage;
    private Context mcontext;
    private String movieName;
    private String releaseDate;
    private Integer movId;
    private Date addDate;
    MovieDB db = null;
    private MovieDBViewModel movieDBVIewModel;
    private MovieViewFromOMDBViewModel movieViewFromOMDBViewModel;
    private MovieInfo movieInfo;
    private Boolean flag;
    private List<MovieInfo> movieList;
    private String[] movieAddToMemoir;
    private RequestOptions option;
    private int btn_enable;
    private RatingBar rateScoreStar;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setToolBarTitle("Movie View");
        View view = inflater.inflate(R.layout.movieview_fragment, container, false);
        mcontext = getActivity();
        movieVIewVIewModel = new ViewModelProvider(this).get(MovieVIewVIewModel.class);
        movieDBVIewModel = new ViewModelProvider(this).get(MovieDBViewModel.class);
        movieViewFromOMDBViewModel = new ViewModelProvider(this).get(MovieViewFromOMDBViewModel.class);
        movieDBVIewModel.initalizeVars(getActivity().getApplication());
        addWatchList_btn = view.findViewById(R.id.addWatchList_btn);
        addToMemoir_btn = view.findViewById(R.id.addToMemoir_btn);
        rateScoreStar = view.findViewById(R.id.rateScoreStar);
        ActualMovieName = view.findViewById(R.id.ActualMovieName);
        ActualReleaseDate = view.findViewById(R.id.ActualReleaseDate);
        ActualMovieGenre = view.findViewById(R.id.ActualMovieGenre);
        ViewMovieCast = view.findViewById(R.id.ViewMovieCast);
        ActualMovieCast = view.findViewById(R.id.ActualMovieCast);
        ActualMovieCountry = view.findViewById(R.id.ActualMovieCountry);
        ViewMoviePlot = view.findViewById(R.id.ViewMoviePlot);
        ActualMoviePlot = view.findViewById(R.id.ActualMoviePlot);
        Director = view.findViewById(R.id.Director);
        show_directors = view.findViewById(R.id.show_directors);
        Score = view.findViewById(R.id.Score);
        ViewMovieImage = view.findViewById(R.id.ViewMovieImage);
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading).error(R.drawable.loading);
        movieVIewVIewModel.getMovieDetaliTask();
        movieVIewVIewModel.getMovieInfo().observe(getViewLifecycleOwner(), new Observer<String[]>() {
            @Override
            public void onChanged(String[] list) {

                ActualMovieName.setText(list[0]);
                ActualReleaseDate.setText(list[1]);
                ActualMovieGenre.setText(list[2]);
                ActualMovieCountry.setText(list[3]);
                ActualMoviePlot.setText(list[4]);
                String imageURL = list[5];
                String url = "https://image.tmdb.org/t/p/w92" + imageURL;
                Glide.with(mcontext).load(url).apply(option).into(ViewMovieImage);

                movieName = list[0];
                releaseDate = list[1];
                String temp = list[6];
                movId =  Integer.parseInt(temp);

            }
        });

        movieViewFromOMDBViewModel.getMovieDetaliTask();
        movieViewFromOMDBViewModel.getMovieInfo().observe(getViewLifecycleOwner(), new Observer<String[]>() {
            @Override
            public void onChanged(String[] strings) {
                show_directors.setText(strings[0]);
                ActualMovieCast.setText(strings[1]);
                String rate = strings[2];
                Double rateScore = Double.parseDouble(rate);
                rateScore = rateScore / 2 ;
                float score = rateScore.floatValue();
                rateScoreStar.setRating(score);
            }
        });
        // if user press add memoir button the page will go to add to memoir fragment
        addToMemoir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddtoMemoirFragment nextFrag = new AddtoMemoirFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.content_frame, nextFrag, "findThisFragment")
                                        .addToBackStack(null)
                                        .commit();
            }
        });

        SharedPreferenceUtil sp = SharedPreferenceUtil.getInstance(mcontext);
        btn_enable = sp.getInt("buttonEnable");
        if (btn_enable == 1){
            addWatchList_btn.setEnabled(false);
        }

        // if the user press add to watchlist button, it will check whether the movie is in the database or not
        addWatchList_btn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                SharedPreferenceUtil sp = SharedPreferenceUtil.getInstance(mcontext);
                final Integer perId = sp.getInt("perId");
                addDate = Calendar.getInstance().getTime();
                movieInfo = new MovieInfo(perId, movieName, releaseDate, addDate,movId);
                flag = Boolean.TRUE;
                movieDBVIewModel.getAllMovieInfos().observe(getViewLifecycleOwner(), new Observer<List<MovieInfo>>() {
                    @Override
                    public void onChanged(List<MovieInfo> movieInfos) {
                        for (MovieInfo temp : movieInfos) {
                            if (movieName.equals(temp.movieName) && releaseDate.equals(temp.releaseDate) && perId == temp.perId  ) {
                                flag = Boolean.FALSE;
                                Toast toast = Toast.makeText(mcontext, "the movie has already in the watchlist ", Toast.LENGTH_SHORT);
                                toast.show();
                                break;
                            }

                        }
                        if(flag == Boolean.TRUE)
                        {
                            movieDBVIewModel.insert(movieInfo);
                            Toast toast = Toast.makeText(mcontext, "add successfully ", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });

            }

        });


        return view;

    }
}

