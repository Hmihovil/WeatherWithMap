package com.example.progforce.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.progforce.network.net.City;
import com.example.progforce.network.net.WeatherDay;

import java.io.Serializable;
import java.util.Calendar;

@Entity(tableName = "weather")
public class WeatherDB implements Serializable {

   @PrimaryKey(autoGenerate = true)
   private int id;

   @ColumnInfo(name = "dataTime")
    private long dt;

   @ColumnInfo(name = "icon")
    private String icon;

   @ColumnInfo(name = "description")
   private String description;

   @ColumnInfo(name = "maxTemp")
   private Double maxTemp;

   @ColumnInfo(name = "minTemp")
   private Double minTemp;

   @ColumnInfo(name = "humidity")
   private Integer humidity;

   @ColumnInfo(name = "pressure")
   private Double pressure;

   @ColumnInfo(name = "wind")
   private Double wind;

    @ColumnInfo(name = "city")
   private String city;

   public static WeatherDB from(WeatherDay weatherDay, String city){
       return new WeatherDB(weatherDay.getDt(), weatherDay.getWeather().get(0).getIcon(), weatherDay.getWeather().get(0).getDescription(),
       weatherDay.getMain().getTempMax(),weatherDay.getMain().getTempMin(),weatherDay.getMain().getHumidity(),
               weatherDay.getMain().getPressure(),weatherDay.getWind().getSpeed(), city);

   }

    public WeatherDB(long dt, String icon, String description, Double maxTemp, Double minTemp, Integer humidity, Double pressure, Double wind, String city) {
        this.id = id;
        this.dt = dt;
        this.icon = icon;
        this.description = description;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public Double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(Double minTemp) {
        this.minTemp = minTemp;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getWind() {
        return wind;
    }

    public void setWind(Double wind) {
        this.wind = wind;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "WeatherDB{" +
                "id=" + id +
                ", dt=" + dt +
                ", icon='" + icon + '\'' +
                ", description='" + description + '\'' +
                ", maxTemp=" + maxTemp +
                ", minTemp=" + minTemp +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", wind=" + wind +
                ", city="+city+
                '}';
    }
    public Calendar getDate() {
        // dt = dt*1000;
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(dt * 1000);
        return date;
    }
}
