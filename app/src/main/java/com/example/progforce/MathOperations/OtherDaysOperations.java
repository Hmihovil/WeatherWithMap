package com.example.progforce.MathOperations;

import com.example.progforce.Data.WeatherDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class OtherDaysOperations {
    public static List<WeatherDB> getOtherDays(List<WeatherDB> resp, Integer day1) {

        List<WeatherDB> finaList = new ArrayList<>();
        List<Integer> maxTempsDayTwo = new ArrayList<>();
        List<Integer> maxTempsDayThree = new ArrayList<>();
        List<Integer> maxTempsDayFour = new ArrayList<>();
        List<Integer> maxTempsDayFive = new ArrayList<>();

        List<Integer> minTempsDayTwo = new ArrayList<>();
        List<Integer> minTempsDayThree = new ArrayList<>();
        List<Integer> minTempsDayFour = new ArrayList<>();
        List<Integer> minTempsDayFive = new ArrayList<>();

        List<WeatherDB> weatherForDayTwo = new ArrayList<>();
        List<WeatherDB> weatherForDayThree = new ArrayList<>();
        List<WeatherDB> weatherForDayFour = new ArrayList<>();
        List<WeatherDB> weatherForDayFive = new ArrayList<>();

        Integer day2 = day1 + 1;
        Integer day3 = day1 + 2;
        Integer day4 = day1 + 3;
        Integer day5 = day1 + 4;

        for (WeatherDB weatherDay : resp) {

            checkEveryDay(weatherDay, day2, weatherForDayTwo, maxTempsDayTwo, minTempsDayTwo);
            checkEveryDay(weatherDay, day3, weatherForDayThree, maxTempsDayThree, minTempsDayThree);
            checkEveryDay(weatherDay, day4, weatherForDayFour, maxTempsDayFour, minTempsDayFour);
            checkEveryDay(weatherDay, day5, weatherForDayFive, maxTempsDayFive, minTempsDayFive);

        }

        setEveryDay(maxTempsDayTwo, minTempsDayTwo, weatherForDayTwo,finaList );
        setEveryDay(maxTempsDayThree, minTempsDayThree, weatherForDayThree,finaList );
        setEveryDay(maxTempsDayFour, minTempsDayFour, weatherForDayFour,finaList );
        setEveryDay(maxTempsDayFive, minTempsDayFive, weatherForDayFive,finaList);

        return finaList;

    }

    public static void checkEveryDay(WeatherDB weatherDay, Integer day, List<WeatherDB> weatherForDayOne,
                              List<Integer> maxTempsDayOne, List<Integer> minTempsDayOne) {

        if (weatherDay.getDate().get(Calendar.DAY_OF_MONTH) == day) {
            weatherForDayOne.add(weatherDay);
            maxTempsDayOne.add(weatherDay.getMaxTemp().intValue());
            minTempsDayOne.add(weatherDay.getMinTemp().intValue());
        }
    }

    public static void setEveryDay(List<Integer> maxTempsDayOne, List<Integer> minTempsDayOne, List<WeatherDB> weatherForDayOne, List<WeatherDB> finaList) {
        Integer maxTemp = Collections.max(maxTempsDayOne);
        Integer minTemp = Collections.min(minTempsDayOne);
        WeatherDB weatherDB = weatherForDayOne.get(0);
        weatherDB.setMinTemp(minTemp.doubleValue());
        weatherDB.setMaxTemp(maxTemp.doubleValue());
        finaList.add(weatherDB);
    }
}
