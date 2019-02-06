package com.example.progforce.math_operation;

import com.example.progforce.data.WeatherDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class FirstDayViewOperations {

    public static WeatherDB initFirstDay(List<WeatherDB> resp, Integer day1) {

        List<Integer> maxTempsDayOne = new ArrayList<>();
        List<Integer> minTempsDayOne = new ArrayList<>();
        List<WeatherDB> weatherForDayOne = new ArrayList<>();

        for (WeatherDB weatherDay : resp) {
            if (weatherDay.getDate().get(Calendar.DAY_OF_MONTH) == day1) {
                weatherForDayOne.add(weatherDay);
                maxTempsDayOne.add(weatherDay.getMaxTemp().intValue());
                minTempsDayOne.add(weatherDay.getMinTemp().intValue());
            }
        }
        Integer maxTemp = Collections.max(maxTempsDayOne);
        Integer minTemp = Collections.min(minTempsDayOne);
        WeatherDB weatherForOneDay = weatherForDayOne.get(0);
        weatherForOneDay.setMaxTemp(maxTemp.doubleValue());
        weatherForOneDay.setMinTemp(minTemp.doubleValue());

        return weatherForOneDay;

    }
}
