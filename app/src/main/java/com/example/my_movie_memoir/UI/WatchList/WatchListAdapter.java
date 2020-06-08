package com.example.my_movie_memoir.UI.WatchList;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.my_movie_memoir.MovieDB.MovieDBViewModel;
import com.example.my_movie_memoir.MovieDB.MovieInfo;
import com.example.my_movie_memoir.R;
import com.example.my_movie_memoir.UI.MovieMemoir.MemoirAdapter;
import com.example.my_movie_memoir.UI.MovieSearch.MovieView.MovieViewFragment;
import com.example.my_movie_memoir.UI.MovieSearch.MyAdapter;
import com.example.my_movie_memoir.sp.SharedPreferenceUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.Holder> {

    private List<MovieInfo> movieinfos = new ArrayList<>();
    private Application application;
    private Context context;

    public WatchListAdapter(ArrayList<MovieInfo> movieinfos, Application application){
        this.movieinfos = movieinfos;
        this.application = application;

    }


    @NonNull
    @Override
    public WatchListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.watchlist_recycleview,parent,false);
        Holder viewHolder = new Holder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final MovieInfo currentMovieInfo = movieinfos.get(position);
        holder.watchList_movieName.setText(currentMovieInfo.movieName);
        holder.watchList_releaseDate.setText("Release Date : "+ currentMovieInfo.releaseDate);
        Date date = currentMovieInfo.addDate;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strDate = dateFormat.format(date);
        holder.watchList_addDate.setText("Add date: "+ strDate);

        Date nowDate = Calendar.getInstance().getTime();
        int differentTime = betweenSevenDaysOrNot(date,nowDate);
        if (differentTime < 7) {
            holder.watchList_image.setVisibility(View.INVISIBLE);
        }
        else {
            holder.watchList_image.setVisibility(View.VISIBLE);
        }

        // can go to movie view fragment
        holder.watchList_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenceUtil sp = SharedPreferenceUtil.getInstance(context);
                sp.putInt("movieId",currentMovieInfo.movieId);
                sp.putInt("buttonEnable",1);
                MovieViewFragment nextFrag= new MovieViewFragment();
                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        // to show the delete dialog
        holder.watchList_Delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(" delete confirmation").setMessage("Do you want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MovieDBViewModel movieDBVIewModel = new
                                       ViewModelProvider((FragmentActivity)context).get(MovieDBViewModel.class);
                                movieDBVIewModel.initalizeVars(application);
                                movieDBVIewModel.delete(currentMovieInfo);
                            }
                        }).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return movieinfos.size();
    }

    public void setMovieInfos(List<MovieInfo> movieinfos){

        this.movieinfos = movieinfos;
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {


        private TextView watchList_movieName, watchList_releaseDate, watchList_addDate;
        private Button watchList_view_btn,watchList_Delete_btn;
        private ImageView watchList_image;


        public Holder(View itemView) {
            super(itemView);
            watchList_addDate = itemView.findViewById(R.id.watchList_addDate);
            watchList_movieName = itemView.findViewById(R.id.watchList_movieName);
            watchList_releaseDate = itemView.findViewById(R.id.watchList_releaseDate);
            watchList_view_btn = itemView.findViewById(R.id.watchList_view_btn);
            watchList_Delete_btn = itemView.findViewById(R.id.watchList_Delete_btn);
            watchList_image = itemView.findViewById(R.id.watchList_image);

        }
    }


    public int betweenSevenDaysOrNot(Date one, Date two){

        long diff = two.getTime() - one.getTime();
        int diffmin = (int) (diff / (24* 60 *60 * 1000));

        return diffmin;
    }
}
