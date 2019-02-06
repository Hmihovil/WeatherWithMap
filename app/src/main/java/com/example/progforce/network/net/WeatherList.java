package com.example.progforce.network.net;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherList {
    @SerializedName("list")
    private List<WeatherDay> items;

    public WeatherList(List<WeatherDay> items, City city) {
        this.items = items;
    }

    public List<WeatherDay> getItems() {
        return items;
    }

}
