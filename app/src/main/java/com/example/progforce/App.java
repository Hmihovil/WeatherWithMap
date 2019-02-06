package com.example.progforce;

import android.app.Application;
import android.arch.persistence.room.Room;
import com.example.progforce.data.AppDatabase;

public class App extends Application {

    public static AppDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = Room.databaseBuilder(this, AppDatabase.class, "production")
                .allowMainThreadQueries()
                .build();
    }
}
