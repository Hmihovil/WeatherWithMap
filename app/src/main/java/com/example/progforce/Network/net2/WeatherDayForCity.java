package com.example.progforce.Network.net2;

import com.example.progforce.Network.net.Main;
import com.example.progforce.Network.net.Weather;
import com.example.progforce.Network.net.Wind;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.List;

public class WeatherDayForCity {
    @SerializedName("dt")
    @Expose
    private long dt;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("main")
    @Expose
    private Main main;


    @SerializedName("wind")
    @Expose
    private Wind wind;

    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;

    public long getDt() {
        return dt;
    }
    public Calendar getDate() {
        // dt = dt*1000;
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(dt * 1000);
        return date;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
}
