package com.example.progforce.Network.net;

import com.example.progforce.Network.net.WeatherDay;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherList {
    @SerializedName("list")
    private List<WeatherDay> items;

    public WeatherList(List<WeatherDay> items) {
        this.items = items;
    }

    public List<WeatherDay> getItems() {
        return items;
    }
}
