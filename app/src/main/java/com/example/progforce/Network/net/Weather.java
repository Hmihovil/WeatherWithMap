package com.example.progforce.Network.net;

import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.http.Url;

public class Weather {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("icon")
    @Expose
    private String icon;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return "http://openweathermap.org/img/w/" + icon + ".png";
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}


//    public String getIcon(){
//        return "http://openweathermap.org/img/w/" + icon + ".png";
//    }

//    public void setIcon(String icon) {
//        this.icon = icon;
//    }




