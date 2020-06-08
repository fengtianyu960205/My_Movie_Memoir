package com.example.my_movie_memoir.UI.WatchList;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_movie_memoir.MainActivity;
import com.example.my_movie_memoir.MovieDB.MovieDB;
import com.example.my_movie_memoir.MovieDB.MovieDBViewModel;
import com.example.my_movie_memoir.MovieDB.MovieInfo;
import com.example.my_movie_memoir.R;
import com.example.my_movie_memoir.UI.MovieSearch.MovieView.MovieVIewVIewModel;
import com.example.my_movie_memoir.UI.MovieSearch.MovieView.MovieViewFragment;
import com.example.my_movie_memoir.UI.MovieSearch.MyAdapter;
import com.example.my_movie_memoir.sp.SharedPreferenceUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WatchListFragment extends Fragment  {

    private TextView movieDBView;
    MovieDB db = null;
    private MovieDBViewModel movieDBVIewModel;
    private Context context;
    private String movieName;
    private String releaseDate;
    private Date addDate;
    private MovieInfo movieInfo;
    private Integer perId;
    private ArrayList<MovieInfo> movieList = new ArrayList<>();
    private WatchListAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){


        View view = inflater.inflate(R.layout.watchlist_fragment, container, false);

        context = getActivity();
        movieDBVIewModel = new
                ViewModelProvider(this).get(MovieDBViewModel.class);

        movieDBVIewModel.initalizeVars(getActivity().getApplication());

        SharedPreferenceUtil sp = SharedPreferenceUtil.getInstance(context);
        perId = sp.getInt("perId");
        ((MainActivity) getActivity()).setToolBarTitle("Watch List");
        RecyclerView recyclerView = view.findViewById(R.id.recycleWatch);

         adapter = new WatchListAdapter(movieList, getActivity().getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);


        movieDBVIewModel.getAllMovieInfos().observe(getViewLifecycleOwner(), new Observer<List<MovieInfo>>() {
                    @Override
                    public void onChanged(List<MovieInfo> movieInfos) {
                        adapter.setMovieInfos(movieInfos);

                    }

                });


        return view;
    }

}

