package com.example.progforce.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface WeatherDAO {

    @Query("SELECT * FROM weather")
    List<WeatherDB> allWeathersBlocking();

    @Query("SELECT * FROM weather")
    WeatherDB  getFirstElement();

    @Query("SELECT * FROM weather ")
    LiveData<List<WeatherDB>> allFavoriteWeathers();

    @Query("SELECT * FROM weather ")
    LiveData<WeatherDB> favoriteWeathers();

    @Insert
    void insert(List<WeatherDB> items);
    @Insert
    void insert(WeatherDB item);

    @Delete
    void delete(WeatherDB... weathers);

    @Update
    void update(WeatherDB... weathers);

    @Query("DELETE FROM weather")
    void clearWeathers();
}
