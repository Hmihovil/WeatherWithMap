package com.example.progforce.network.net;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListWithCity {

    @SerializedName("list")
    private List<WeatherDay> items;

    @SerializedName("city")
    @Expose
    private City city;

    public ListWithCity (List<WeatherDay> items, City city) {
        this.items = items;
        this.city = city;
    }

    public void setItems(List<WeatherDay> items) {
        this.items = items;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<WeatherDay> getItems() {
        return items;
    }

    public City getCity(){
        return city;
    }

    public City getName() {
        return city;
    }

    public void setName(City name) {
        this.city = name;
    }
}
