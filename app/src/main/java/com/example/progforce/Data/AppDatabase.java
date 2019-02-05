package com.example.progforce.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {WeatherDB.class,WeatherDBforFirstDay.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WeatherDAO weatherDAO();
    public abstract WeatherDAOFirst weatherDAOFirst();
}
