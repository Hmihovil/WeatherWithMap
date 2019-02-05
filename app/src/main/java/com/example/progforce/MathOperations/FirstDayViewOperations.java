package com.example.progforce.MathOperations;

import android.util.Log;

import com.example.progforce.Data.WeatherDB;
import com.example.progforce.Data.WeatherDBforFirstDay;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class FirstDayViewOperations {

    public static WeatherDBforFirstDay initFirstDay(List<WeatherDBforFirstDay> resp, Integer day1) {

        List<Integer> maxTempsDayOne = new ArrayList<>();
        List<Integer> minTempsDayOne = new ArrayList<>();
        List<WeatherDBforFirstDay> weatherForDayOne = new ArrayList<>();

        for (WeatherDBforFirstDay weatherDay : resp) {
            if (weatherDay.getDate().get(Calendar.DAY_OF_MONTH) == day1) {
                weatherForDayOne.add(weatherDay);
                maxTempsDayOne.add(weatherDay.getMaxTemp().intValue());
                minTempsDayOne.add(weatherDay.getMinTemp().intValue());

            }
        }
        Integer maxTemp = Collections.max(maxTempsDayOne);
        Integer minTemp = Collections.min(minTempsDayOne);
        WeatherDBforFirstDay weatherForOneDay = weatherForDayOne.get(0);
        weatherForOneDay.setMaxTemp(maxTemp.doubleValue());
        weatherForOneDay.setMinTemp(minTemp.doubleValue());
      //  finalListToDBFromFirst = (weatherForOneDay);
        //Log.e("ЖОПКЕН", maxTemp + "  " + minTemp);


        return weatherForOneDay;

    }
}
