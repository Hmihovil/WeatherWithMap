package com.example.progforce.Data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.progforce.Network.net.Weather;

import java.util.List;

import static android.icu.text.MessagePattern.ArgType.SELECT;

@Dao
public interface WeatherDAO {

    @Query("SELECT * FROM weather")
    List<WeatherDB> allWeathersBlocking();

    @Query("SELECT * FROM weather")
    WeatherDB  getFirstElement();

    @Query("SELECT * FROM weather")
    LiveData<List<WeatherDB>> allFavoriteWeathers();

//    @Query("SELECT * FROM human")
//    Flowable<List<HumanEntity>> allHumansRx();
//
//    @Query("UPDATE weather SET favorite = 0")
//    void removeAllFavorite();


    @Insert
    void insert(List<WeatherDB> items);

    @Delete
    void delete(WeatherDB... weathers);

    @Update
    void update(WeatherDB... weathers);

    @Query("DELETE FROM weather")
    void clearWeathers();


}
