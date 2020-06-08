package com.example.my_movie_memoir.UI.MovieMemoir;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_movie_memoir.MainActivity;
import com.example.my_movie_memoir.R;
import com.example.my_movie_memoir.UI.MovieSearch.MovieSearchViewModel;
import com.example.my_movie_memoir.UI.MovieSearch.MovieView.MovieVIewVIewModel;
import com.example.my_movie_memoir.UI.MovieSearch.MyAdapter;
import com.example.my_movie_memoir.sp.SharedPreferenceUtil;

import java.util.ArrayList;

// this view model is to get all the memoirs from the server
public class MovieMemoirFragment extends Fragment {
    private MovieMemoirViewModel MovieMemoirViewModel;
    private Context context;
    private MemoirAdapter adapter;
    private Button memoir_btn;
    private ArrayList<String[]> list = new ArrayList<>();
    private MovieSearchViewModel movieSearchViewModel;
    private ArrayList<String[]> infos = new ArrayList<>();



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.moviememoir_fragment, container, false);



        MovieMemoirViewModel = new ViewModelProvider(this).get(MovieMemoirViewModel.class);
        movieSearchViewModel = new ViewModelProvider(this).get(MovieSearchViewModel.class);
        context = getActivity();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerMemoir);
        ((MainActivity) getActivity()).setToolBarTitle("Movie Memoir");
        adapter = new MemoirAdapter(list,getViewLifecycleOwner());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);

        SharedPreferenceUtil sp = SharedPreferenceUtil.getInstance(context);
        int perId = sp.getInt("perId");
        MovieMemoirViewModel.getMemoirTask(perId);
        MovieMemoirViewModel.getMemoir().observe(getViewLifecycleOwner(), new Observer<ArrayList>() {
            @Override
            public void onChanged(ArrayList arraylist) {
                infos = arraylist;
                ArrayList<String> movieNames = new ArrayList<>();
                for (int i = 0; i < infos.size(); i++) {
                    movieNames.add(infos.get(i)[0]);
                }


                movieSearchViewModel.getMoviesInfoTask(movieNames);
                movieSearchViewModel.getMovieInfo().observe(getViewLifecycleOwner(), new Observer<ArrayList>() {
                    @Override
                    public void onChanged(ArrayList arraylist) {
                        ArrayList<String[]> list1 = arraylist;

                        if (list1.size() == infos.size()){
                            for(int i = 0 ; i < list1.size() ; i++) {
                                infos.get(i)[6] = list1.get(i)[2];
                                infos.get(i)[7] = list1.get(i)[1];
                            }
                            // put all the memoir information to the recycler adapter
                            adapter.setMemoirs(infos);
                        }

                    }
                });

            }
        });

        return view;
    }


}
