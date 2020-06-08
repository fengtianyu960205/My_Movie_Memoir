package com.example.my_movie_memoir.UI.MovieMemoir;

import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.my_movie_memoir.MovieDB.MovieDBViewModel;
import com.example.my_movie_memoir.R;
import com.example.my_movie_memoir.UI.MovieSearch.MovieSearchViewModel;
import com.example.my_movie_memoir.UI.MovieSearch.MovieView.MovieViewFragment;
import com.example.my_movie_memoir.UI.MovieSearch.MyAdapter;
import com.example.my_movie_memoir.sp.SharedPreferenceUtil;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MemoirAdapter extends  RecyclerView.Adapter<MemoirAdapter.Holder> {

    private ArrayList<String[]> memoirs = new ArrayList<>();
    private MyAdapter.OnItemListener monItemListener;
    private Context context;
    private LifecycleOwner lifecycleOwner;
    private Date release = Calendar.getInstance().getTime();
    private Date watchMovieDate = Calendar.getInstance().getTime();
    RequestOptions option;


    public MemoirAdapter(ArrayList<String[]> memoirs,LifecycleOwner lifecycleOwner) {

        this.memoirs = memoirs;
        this.lifecycleOwner = lifecycleOwner;
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading).error(R.drawable.loading);

    }

    @NonNull
    @Override
    public MemoirAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.moviememoir_recyclerview, parent, false);
        Holder viewHolder = new Holder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MemoirAdapter.Holder holder, int position) {
        // assign the vaule to all the ui in the recycler view
        String[] currentMovie = memoirs.get(position);
        String releaseDate = currentMovie[1];
        String watchDate = currentMovie[2];
        try{  release = new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate);}
        catch (Exception e){}
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        releaseDate = dateFormat.format(release);

        try{  watchMovieDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(watchDate);}
        catch (Exception e){}
        DateFormat datewatchFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        watchDate = datewatchFormat.format(watchMovieDate);

        holder.MovieMemoirName.setText(currentMovie[0]);
        holder.MemoirReleaseDate.setText("release date: " + releaseDate);
        holder.MemoirWatchDate.setText("watch date: " + watchDate);
        holder.MemoirCinemaName.setText(currentMovie[4]);
        holder.MemoirCinemaSuburb.setText(currentMovie[5]);
        holder.MemoirComment.setText(currentMovie[3]);
        String rateStr = currentMovie[8];
        Double rateScore = Double.parseDouble(rateStr);
        float score = rateScore.floatValue();
        holder.movieMemoir_rateScore.setRating(score);
        String url = "https://image.tmdb.org/t/p/w92" + currentMovie[6];
        Glide.with(context).load(url).apply(option).into(holder.MovieMemoirImage);


    }

    @Override
    public int getItemCount() {
        return memoirs.size();
    }

    public void setMemoirs(ArrayList<String[]> memoirs) {

        this.memoirs = memoirs;
        notifyDataSetChanged();
    }


    class Holder extends RecyclerView.ViewHolder  {

        private ImageView MovieMemoirImage;
        private TextView MovieMemoirName, MemoirReleaseDate,MemoirWatchDate,MemoirCinemaName,MemoirCinemaSuburb,MemoirComment;
        private RatingBar movieMemoir_rateScore;


        public Holder(View itemView) {
            super(itemView);
            MovieMemoirImage = itemView.findViewById(R.id.MovieMemoirImage);
            MovieMemoirName = itemView.findViewById(R.id.MovieMemoirName);
            MemoirReleaseDate = itemView.findViewById(R.id.MemoirReleaseDate);
            MemoirWatchDate = itemView.findViewById(R.id.MemoirWatchDate);
            MemoirCinemaName = itemView.findViewById(R.id.MemoirCinemaName);
            MemoirCinemaSuburb = itemView.findViewById(R.id.MemoirCinemaSuburb);
            MemoirComment = itemView.findViewById(R.id.MemoirComment);
            movieMemoir_rateScore = itemView.findViewById(R.id.movieMemoir_rateScore);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                            int movieId = Integer.parseInt( memoirs.get(getAdapterPosition())[7]);
                            String MovieName = memoirs.get(getAdapterPosition())[0];
                            SharedPreferenceUtil sp = SharedPreferenceUtil.getInstance(context);
                            sp.putInt("movieId",movieId );
                            sp.putString("movieName",MovieName);
                            MovieViewFragment nextFrag= new MovieViewFragment();
                            ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, nextFrag, "findThisFragment")
                                    .addToBackStack(null)
                                    .commit();

                }
            });

        }

    }
}
