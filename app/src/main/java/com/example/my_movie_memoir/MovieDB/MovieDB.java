package com.example.my_movie_memoir.MovieDB;

import android.content.Context;
import android.os.AsyncTask;


import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {MovieInfo.class}, version = 18, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MovieDB extends RoomDatabase {
    public abstract DAO DAO();

    private static MovieDB INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized MovieDB getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    MovieDB.class, "MovieDB")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

   /* private static RoomDatabase.Callback roomcallback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            new PopulatedDbAsyncTasks(INSTANCE).execute();
        }

    };

    private static class PopulatedDbAsyncTasks extends AsyncTask<Void,Void,Void>{
        private DAO dao;

        private PopulatedDbAsyncTasks(MovieDB db){
            dao = db.DAO();

        }


        @Override
        protected Void doInBackground(Void... voids) {
            Date addDate = Calendar.getInstance().getTime();
            dao.insert(new MovieInfo(100,"Forrest Gump","2019 7 24",addDate));

            return null;
        }
    }*/
}

