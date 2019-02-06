package com.example.progforce.network;

import com.example.progforce.network.net.ListWithCity;
import com.example.progforce.network.net.WeatherList;
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

        @GET("forecast")
        Call<ListWithCity> getForecast(
                @Query("lat") Double lat,
                @Query("lon") Double lon,
                @Query("units") String units,
                @Query("appid") String appid
        );

        @GET("find")
        Call<WeatherList> getTownWeather(
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