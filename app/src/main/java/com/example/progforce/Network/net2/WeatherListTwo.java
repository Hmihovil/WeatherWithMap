package com.example.progforce.Network.net2;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherListTwo {


        @SerializedName("list")
        private List<WeatherDayForCity> items;

        public WeatherListTwo(List<WeatherDayForCity> items) {
            this.items = items;
        }

        public List<WeatherDayForCity> getItems() {
            return items;
        }

}
