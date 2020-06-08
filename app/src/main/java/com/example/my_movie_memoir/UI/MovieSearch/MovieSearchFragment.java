package com.example.my_movie_memoir.UI.MovieSearch;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_movie_memoir.MainActivity;
import com.example.my_movie_memoir.R;
import com.example.my_movie_memoir.UI.MovieSearch.MovieView.MovieVIewVIewModel;
import com.example.my_movie_memoir.UI.MovieSearch.MovieView.MovieViewFragment;
import com.example.my_movie_memoir.sp.SharedPreferenceUtil;

import java.util.ArrayList;


public class MovieSearchFragment extends Fragment implements MyAdapter.OnItemListener {
    private MyAdapter adapter;
    private MovieSearchViewModel moviesearchViewModel;
    private MovieVIewVIewModel movieVIewVIewModel;
    private EditText MovieName;
    private Context mcontext ;
    private Button searchButton;
    private ArrayList<String[]> Info = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mcontext = getActivity();
        View view = inflater.inflate(R.layout.moviesearch_fragment, container, false);
        MovieName=view.findViewById(R.id.MovieName);
        searchButton=view.findViewById(R.id.searchButton);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview1);
        ((MainActivity) getActivity()).setToolBarTitle("Movie Search");

        adapter = new MyAdapter( this, mcontext);
        recyclerView.setAdapter(adapter);
         recyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
         recyclerView.setHasFixedSize(true);
        moviesearchViewModel = new ViewModelProvider(this).get(MovieSearchViewModel.class);
       // movieVIewVIewModel = new ViewModelProvider(this).get(MovieVIewVIewModel.class);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moviesearchViewModel.getMovieInfoTask(MovieName.getText().toString().trim());
                moviesearchViewModel.getMovieInfo().observe(getViewLifecycleOwner(), new Observer<ArrayList>() {
                    @Override
                    public void onChanged(ArrayList arrayList) {
                        Info = arrayList;
                        adapter.setNameYear(arrayList);

                    }
                });

            }
        });

        return view;
    }


    @Override
    public void onItemClick (int position) {

        String [] movieViewInfo = Info.get(position);
        SharedPreferenceUtil sp = SharedPreferenceUtil.getInstance(mcontext);
        String moiveId = movieViewInfo[2];
        String movieName = movieViewInfo[0];
        String movieDate = movieViewInfo[1];
        Integer movieid = Integer.parseInt(moiveId);
        sp.putString("movieName",movieName);
        sp.putInt("movieId",movieid);
        sp.putInt("buttonEnable",0);
        MovieViewFragment nextFrag= new MovieViewFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }



}






