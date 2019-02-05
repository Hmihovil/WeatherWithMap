package com.example.progforce.Network.net;
import com.example.progforce.Network.net.City;
import com.example.progforce.Network.net.Main;
import com.example.progforce.Network.net.Weather;
import com.example.progforce.Network.net.Wind;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeatherDay {

    @SerializedName("dt")
    @Expose
    private long dt;

    @SerializedName("main")
    @Expose
    private Main main;


    @SerializedName("wind")
    @Expose
    private Wind wind;

    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;



    public Calendar getDate() {
       // dt = dt*1000;
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(dt * 1000);
        return date;
    }


    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }


    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
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

    @Override
    public String toString() {
        return "WeatherDay{" +
                "dt=" + dt +
                ", main=" + main +
                ", wind=" + wind +
                ", weather=" + weather +
                '}';
    }
}
