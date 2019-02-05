package com.example.progforce.Data;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface WeatherDAOFirst {


    @Query("SELECT * FROM weatherForFirstDay")
    List<WeatherDBforFirstDay> firstWeathersBlocking();

    @Query("SELECT * FROM weatherForFirstDay")
    WeatherDBforFirstDay  getFirstElement();

    @Query("SELECT * FROM weatherForFirstDay")
    LiveData<List<WeatherDBforFirstDay>> firstFavoriteWeathers();

//    @Query("SELECT * FROM human")
//    Flowable<List<HumanEntity>> allHumansRx();
//
//    @Query("UPDATE weather SET favorite = 0")
//    void removeAllFavorite();

    @Insert
    void insert(WeatherDBforFirstDay... items);



    @Delete
    void delete(WeatherDBforFirstDay... weathers);

    @Update
    void update(WeatherDBforFirstDay... weathers);

    @Query("DELETE FROM weatherForFirstDay")
    void clearFirstDay();
}
