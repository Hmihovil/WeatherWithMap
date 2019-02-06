package com.example.progforce.network.net;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.List;

public class WeatherDay {

    @SerializedName("name")
    @Expose
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
