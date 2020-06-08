package com.example.my_movie_memoir.MovieDB;

import java.util.Date;

public class Converters {
    @androidx.room.TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @androidx.room.TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
