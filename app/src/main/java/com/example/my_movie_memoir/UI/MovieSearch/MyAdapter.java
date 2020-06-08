package com.example.my_movie_memoir.UI.MovieSearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.my_movie_memoir.R;
import com.example.my_movie_memoir.UI.Home.HomeFragmentViewModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {
    private ArrayList<String[]> nameYear = new ArrayList<>();
    private OnItemListener monItemListener;
    private Context mcontext ;
    RequestOptions option;

    public MyAdapter( OnItemListener monItemListener ,Context mcontext){

        this.monItemListener = monItemListener;
        this.mcontext = mcontext;

        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading).error(R.drawable.loading);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listview,parent,false);
        return new Holder (itemView,monItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position)  {
        String[] currentNameYear = nameYear.get(position);
        holder.MovieName.setText(currentNameYear[0]);
        holder.ReleaseDate.setText(currentNameYear[1]);
        String url = "https://image.tmdb.org/t/p/w92" + currentNameYear[3];
        Glide.with(mcontext).load(url).apply(option).into(holder.Image1);


    }

    @Override
    public int getItemCount() {
        return nameYear.size();
    }

    public void setNameYear(ArrayList<String[]> nameYear){

        this.nameYear = nameYear;
        notifyDataSetChanged();
    }


    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView Image1;
        private TextView MovieName, ReleaseDate;
        OnItemListener onItemListener;


        public Holder(View itemView ,OnItemListener onItemListener ){
            super(itemView);
            Image1 = itemView.findViewById(R.id.Image1);
            MovieName = itemView.findViewById(R.id.MovieName);
            ReleaseDate = itemView .findViewById(R.id.ReleaseDate);
            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemListener{

        void onItemClick(int position);
    }

}
