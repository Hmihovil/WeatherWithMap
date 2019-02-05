package com.example.progforce.Network;

import com.example.progforce.Network.net.City;
import com.example.progforce.Network.net.WeatherDay;
import com.example.progforce.Network.net.WeatherList;
import com.example.progforce.Network.net2.WeatherListTwo;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WeatherAPI {
    public static String KEY = "088bf18e1e0dff6e36e16733f079d626";
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private static Retrofit retrofit = null;

    public interface ApiInterface {
        @GET("weather")
        Call<WeatherDay> getToday(
                @Query("lat") Double lat,
                @Query("lon") Double lon,
                @Query("units") String units,
                @Query("appid") String appid
        );

        @GET("forecast")
        Call<WeatherList> getForecast(
                @Query("lat") Double lat,
                @Query("lon") Double lon,
                @Query("units") String units,
                @Query("appid") String appid
        );

        @GET("find")
        Call<WeatherListTwo> getTownWeather(
                @Query("q" ) String city,
                @Query("units") String units,
                @Query("appid") String appid

        );

    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}