package com.example.my_movie_memoir.UI.Home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.my_movie_memoir.R;
import com.example.my_movie_memoir.Signin.Signup.SignupViewModel;
import com.example.my_movie_memoir.sp.SharedPreferenceUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private TextView Home_textView;
    private TextView date_display;
    private TextView home_Movie_info,home_favourite;
    private Context context;
    private HomeFragmentViewModel homeFragmentViewModel;


    public HomeFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        homeFragmentViewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);
        Home_textView=view.findViewById(R.id.Home_textView);
        home_Movie_info=view.findViewById(R.id.home_Movie_info);
        home_favourite=view.findViewById(R.id.home_favourite);
        date_display = view.findViewById(R.id.date_display);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date());
        SharedPreferenceUtil sp = SharedPreferenceUtil.getInstance(context);
        String firstName = sp.getString("perFn");
        Home_textView.setText("Welcome " + firstName + "!");
        date_display.setText("Date: " +date);
        int perId = sp.getInt("perId");


        homeFragmentViewModel.getMovieTask(perId);

        homeFragmentViewModel.getMovie().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                home_Movie_info.setText(s);
            }
        });


        return view;
    }


}
